package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.binding.EzyMarshaller;
import com.tvd12.ezyfox.binding.EzyWriter;
import com.tvd12.ezyfox.binding.exception.EzyWriteValueException;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.reflect.*;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;

@SuppressWarnings("rawtypes")
public abstract class EzyAbstractWriterBuilder
    extends EzyAbstractBuilder<EzyGetterMethod>
    implements EzyBuilder<EzyWriter> {

    protected EzyAbstractWriterBuilder(EzyClass clazz) {
        super(clazz);
    }

    @Override
    public EzyWriter build() {
        try {
            return make();
        } catch (Exception e) {
            throw new IllegalStateException("can not build writer for class " + clazz, e);
        }
    }

    protected EzyWriter make() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        String implClassName = getImplClassName();
        CtClass implClass = pool.makeClass(implClassName);
        EzyMethod writeMethod = getWriteMethod();
        writeMethod.setDisplayName("write$impl");
        String implMethodContent = makeImplMethodContent(writeMethod);
        writeMethod.setDisplayName("write");
        String methodContent = makeMethodContent(writeMethod);
        printMethodContent(methodContent);
        printMethodContent(implMethodContent);
        implClass.setInterfaces(new CtClass[]{pool.makeClass(EzyWriter.class.getName())});
        implClass.addMethod(CtNewMethod.make(implMethodContent, implClass));
        implClass.addMethod(CtNewMethod.make(methodContent, implClass));
        Class answerClass = implClass.toClass();
        implClass.detach();
        logger.debug("class {} has generated", implClassName);
        return (EzyWriter) answerClass.newInstance();
    }

    protected String makeMethodContent(EzyMethod writeMethod) {
        return new EzyFunction(writeMethod)
            .body()
            .append(new EzyInstruction("\t", "\n", false)
                .append("try {"))
            .append(new EzyInstruction("\t\t", "\n")
                .append("return this.write$impl(arg0, arg1)"))
            .append(new EzyInstruction("\t", "\n", false)
                .append("} catch(")
                .clazz(Exception.class)
                .append(" e) {"))
            .append(new EzyInstruction("\t\t", "\n\t}\n")
                .append("throw new ")
                .clazz(EzyWriteValueException.class)
                .bracketopen()
                .clazz(getOutType(), true)
                .append(", arg1, e")
                .bracketclose())
            .function()
            .toString();
    }

    protected abstract Class<?> getOutType();

    protected abstract String getImplClassName();

    protected abstract String makeImplMethodContent(EzyMethod writeMethod);

    protected com.tvd12.ezyfox.binding.annotation.EzyWriter getWriterClass(
        EzyReflectElement element
    ) {
        com.tvd12.ezyfox.binding.annotation.EzyWriter wrt =
            getWriterAnnotation(element);
        if (wrt != null) {
            return wrt;
        }
        if (element instanceof EzyField) {
            return null;
        }
        EzyField field = clazz.getField(getFieldName(element));
        return field == null ? null : getWriterAnnotation(field);
    }

    protected com.tvd12.ezyfox.binding.annotation.EzyWriter getWriterAnnotation(
        EzyReflectElement element
    ) {
        return element.getAnnotation(
            com.tvd12.ezyfox.binding.annotation.EzyWriter.class);
    }

    protected EzyMethod getWriteMethod() {
        return EzyMethod.builder()
            .clazz(EzyWriter.class)
            .methodName("write")
            .parameterTypes(EzyMarshaller.class)
            .parameterTypes(Object.class)
            .build();
    }

    protected void printMethodContent(String methodContent) {
        if (isDebug()) {
            logger.debug("writer: method content \n{}", methodContent);
        }
    }

    protected abstract boolean isDebug();
}
