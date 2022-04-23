package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyFunction.EzyBody;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.binding.annotation.EzyPostRead;
import com.tvd12.ezyfox.binding.exception.EzyReadValueException;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.*;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("rawtypes")
public abstract class EzyAbstractReaderBuilder
    extends EzyAbstractBuilder<EzySetterMethod> {

    protected final List<EzyMethod> postReadMethods;

    public EzyAbstractReaderBuilder(EzyClass clazz) {
        super(clazz);
        this.postReadMethods = getPostReadMethods();
    }

    private List<EzyMethod> getPostReadMethods() {
        return clazz.getPublicMethods(m -> m.isAnnotated(EzyPostRead.class));
    }

    @SuppressWarnings("unchecked")
    public <T> T build() {
        try {
            return (T) make();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected Object make() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        String implClassName = getImplClassName();
        CtClass implClass = pool.makeClass(implClassName);
        EzyMethod readMethod = getReadMethod();
        readMethod.setDisplayName(getReadMethodName() + "$impl");
        String implMethodContent = makeImplMethodContent(readMethod);
        readMethod.setDisplayName(getReadMethodName());
        String methodContent = makeMethodContent(readMethod);
        printMethodContent(methodContent);
        printMethodContent(implMethodContent);
        implClass.setInterfaces(new CtClass[]{pool.makeClass(getReaderInterface().getName())});
        implClass.addMethod(CtNewMethod.make(implMethodContent, implClass));
        implClass.addMethod(CtNewMethod.make(methodContent, implClass));
        Class answerClass = implClass.toClass();
        implClass.detach();
        logger.debug("class {} has generated", implClassName);
        return answerClass.getDeclaredConstructor().newInstance();
    }

    protected String getReadMethodName() {
        return "read";
    }

    protected Class<?> getReaderInterface() {
        return EzyReader.class;
    }

    protected String makeMethodContent(EzyMethod readMethod) {
        String[] paramNames = new String[readMethod.getParameterCount()];
        for (int i = 0; i < paramNames.length; ++i) {
            paramNames[i] = "arg" + i;
        }
        String paramNamesChain = EzyStrings.join(paramNames, ", ");
        return new EzyFunction(readMethod)
            .body()
            .append(new EzyInstruction("\t", "\n", false)
                .append("try {"))
            .append(new EzyInstruction("\t\t", "\n")
                .append("return this." + getReadMethodName() + "$impl(" + paramNamesChain + ")"))
            .append(new EzyInstruction("\t", "\n", false)
                .append("} catch(")
                .clazz(Exception.class)
                .append(" e) {"))
            .append(new EzyInstruction("\t\t", "\n\t}\n")
                .append("throw new ")
                .clazz(EzyReadValueException.class)
                .bracketopen()
                .clazz(clazz.getClazz(), true)
                .append(", arg1, e")
                .bracketclose())
            .function()
            .toString();
    }

    protected void appendOutputObjectConstructor(EzyFunction.EzyBody methodBody) {
        Constructor constructor = clazz.getNoArgsDeclaredConstructor();
        List<String> paramNames = Collections.emptyList();
        if (constructor == null) {
            paramNames = appendOutputObjectConstructorParams(methodBody);
        }
        EzyInstruction newOutputObjectInstruction = new EzyInstruction("", "", false)
            .append("new ")
            .clazz(clazz.getClazz(), false)
            .bracketopen()
            .append(String.join(", ", paramNames))
            .bracketclose();
        appendOutputObjectInstruction(methodBody, newOutputObjectInstruction);
    }

    protected List<String> appendOutputObjectConstructorParams(
        EzyFunction.EzyBody methodBody) {
        Constructor constructor = clazz.getMaxArgsDeclaredConstructor();
        List<String> keyList = new ArrayList<>();
        List<EzyField> declaredFields = clazz.getDeclaredFields();
        for (EzyField field : declaredFields) {
            keyList.add(getKey(field));
        }
        List<String> paramNames = new ArrayList<>();
        Parameter[] constructorParameters = constructor.getParameters();
        for (int i = 0; i < constructor.getParameterCount(); ++i) {
            String paramName = "cparam" + i;
            Parameter parameter = constructorParameters[i];
            EzyInstruction constructorInstruction = new EzyInstruction("\t", "\n")
                .variable(parameter.getType(), paramName)
                .equal();
            EzyInstruction constructorValueInstruction =
                new EzyInstruction("", "", false);
            if (i >= keyList.size()
                || !parameter.getType().isAssignableFrom(declaredFields.get(i).getType())
            ) {
                constructorValueInstruction.defaultValue(parameter.getType());
            } else {
                appendConstructorParamValue(
                    constructorValueInstruction,
                    parameter,
                    i,
                    declaredFields.get(i),
                    keyList.get(i));
            }
            constructorInstruction.append(constructorValueInstruction.toString(false));
            methodBody.append(constructorInstruction);
            paramNames.add(paramName);
        }
        return paramNames;
    }

    protected void appendConstructorParamValue(
        EzyInstruction instruction,
        Parameter parameter,
        int parameterIndex,
        EzyField field,
        String key) {}

    protected void appendOutputObjectInstruction(
        EzyFunction.EzyBody methodBody,
        EzyInstruction newOutputObjectInstruction) {
        methodBody.append(new EzyInstruction("\t", "\n")
            .variable(clazz.getClazz(), "object")
            .equal()
            .append(newOutputObjectInstruction.toString(false)
            )
        );
    }

    protected abstract String getImplClassName();

    protected abstract String makeImplMethodContent(EzyMethod readMethod);

    protected void addPostReadMethods(EzyBody methodBody) {
        for (EzyMethod method : postReadMethods) {
            EzyInstruction instruction = new EzyInstruction("\t", "\n")
                .append("object")
                .dot()
                .append(method.getName())
                .brackets("");
            methodBody.append(instruction);
        }
    }

    protected EzyMethod getReadMethod() {
        return EzyMethod.builder()
            .clazz(getReaderInterface())
            .methodName(getReadMethodName())
            .parameterTypes(getReaderMethodParameterTypes())
            .build();
    }

    protected Class[] getReaderMethodParameterTypes() {
        return new Class[]{EzyUnmarshaller.class, Object.class};
    }

    protected Class getReaderImplClass(EzyReflectElement element) {
        com.tvd12.ezyfox.binding.annotation.EzyReader rd =
            getReaderAnnotation(element);
        if (rd != null) {
            return rd.value();
        }
        if (element instanceof EzyField) {
            return null;
        }
        EzyField field = clazz.getField(getFieldName(element));
        return field == null ? null : getReaderImplClass(field);
    }

    protected com.tvd12.ezyfox.binding.annotation.EzyReader getReaderAnnotation(
        EzyReflectElement element
    ) {
        return element.getAnnotation(
            com.tvd12.ezyfox.binding.annotation.EzyReader.class
        );
    }

    protected EzyInstruction wrapUnmarshalInstruction(
        EzyInstruction instruction, Class outType) {
        String value = instruction.toString();
        EzyInstruction answer = new EzyInstruction("", "", false);
        return answer.cast(outType, value);
    }

    protected Set<Class> getCommonGenericTypes() {
        return EzyTypes.COMMON_GENERIC_TYPES;
    }

    protected void printMethodContent(String methodContent) {
        if (isDebug()) {
            logger.debug("reader: method content \n{}", methodContent);
        }
    }

    protected abstract boolean isDebug();
}
