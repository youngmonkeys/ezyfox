package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.reflect.*;
import lombok.Setter;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("rawtypes")
public class EzyObjectReaderBuilder extends EzyAbstractReaderBuilder {

    protected static final AtomicInteger COUNT = new AtomicInteger(0);
    @Setter
    protected static boolean debug;

    public EzyObjectReaderBuilder(EzyClass clazz) {
        super(clazz);
    }

    @Override
    protected int getAccessType(EzyClass clazz) {
        EzyObjectBinding ann = clazz.getAnnotation(EzyObjectBinding.class);
        return ann == null ? EzyAccessType.ALL : ann.accessType();
    }

    @Override
    protected EzyElementsFetcher newElementsFetcher() {
        return new EzyObjectReaderElementsFetcher();
    }

    @Override
    protected String makeImplMethodContent(EzyMethod readMethod) {
        EzyFunction.EzyBody methodBody = new EzyFunction(readMethod)
            .modifier("protected")
            .body()
            .append(new EzyInstruction("\t", "\n")
                .variable(EzyObject.class, "value")
                .equal()
                .cast(EzyObject.class, "arg1")
            );
        appendOutputObjectConstructor(methodBody);

        for (Object element : getElements()) {
            if (element instanceof EzyField) {
                if (!((EzyField) element).isWritable()) {
                    continue;
                }
            }
            methodBody.append(checkNotNullInstruction((EzyReflectElement) element));
            methodBody.append(newInstructionByElement(element));
        }

        addPostReadMethods(methodBody);

        methodBody.append(new EzyInstruction("\t", "\n")
            .answer()
            .append("object"));

        EzyFunction method = methodBody.function();

        return method.toString();
    }

    protected EzyInstruction newInstructionByElement(Object element) {
        if (element instanceof EzyField) {
            return newInstructionByField((EzyField) element);
        }
        return newInstructionByMethod((EzyMethod) element);
    }

    protected EzyInstruction newInstructionByField(EzyField field) {
        EzyInstruction instruction = new EzyInstruction("\t\t", "\n")
            .append("object")
            .dot()
            .append(field.getName())
            .equal();
        EzyInstruction unmarshalInstruction = newUnmarshalInstruction(field);
        instruction.append(unmarshalInstruction.toString());
        return instruction;
    }

    protected EzyInstruction newInstructionByMethod(EzyMethod method) {
        EzyInstruction instruction = new EzyInstruction("\t\t", "\n")
            .append("object")
            .dot()
            .append(method.getName())
            .bracketopen();
        EzyInstruction unmarshalInstruction = newUnmarshalInstruction(method);
        instruction
            .append(unmarshalInstruction.toString())
            .bracketclose();
        return instruction;
    }

    protected EzyInstruction checkNotNullInstruction(EzyReflectElement element) {
        return new EzyInstruction("\t", "\n", false)
            .append("if")
            .bracketopen()
            .append("value")
            .dot()
            .append("isNotNullValue")
            .bracketopen()
            .string(getKey(element))
            .bracketclose()
            .bracketclose();
    }

    protected EzyInstruction newUnmarshalInstruction(EzyReflectElement element) {
        Set<Class> commonGenericTypes = getCommonGenericTypes();
        Class type = getElementType(element);
        Class readerImpl = getReaderImplClass(element);
        EzyInstruction instruction;
        if (readerImpl != null || !commonGenericTypes.contains(type)) {
            instruction = newUnmarshalNormalInstruction(element, readerImpl);
        } else {
            instruction = newUnmarshalGenericInstruction(element);
        }

        return wrapUnmarshalInstruction(instruction, type);
    }

    protected EzyInstruction newUnmarshalGenericInstruction(EzyReflectElement element) {
        Class genericType = getElementType(element);
        if (Map.class.isAssignableFrom(genericType)) {
            return newUnmarshalMapInstruction(element);
        }
        return newUnmarshalCollectionInstruction(element);
    }

    protected EzyInstruction newUnmarshalMapInstruction(EzyReflectElement element) {
        Class mapType = getElementType(element);
        Type genericType = getElementGenericType(element);
        Class[] keyValueTypes = EzyGenerics.getTwoGenericClassArguments(genericType);
        return new EzyInstruction("", "", false)
            .append("arg0.unmarshalMap")
            .bracketopen()
            .bracketopen()
            .clazz(Object.class)
            .bracketclose()
            .append("value.getValue(")
            .string(getKey(element))
            .comma()
            .clazz(mapType, true)
            .bracketclose()
            .comma()
            .clazz(mapType, true)
            .comma()
            .clazz(keyValueTypes[0], true)
            .comma()
            .clazz(keyValueTypes[1], true)
            .bracketclose();
    }

    protected EzyInstruction newUnmarshalCollectionInstruction(EzyReflectElement element) {
        Class collectionType = getElementType(element);
        Type genericType = getElementGenericType(element);
        Class itemType = EzyGenerics.getOneGenericClassArgument(genericType);
        return new EzyInstruction("", "", false)
            .append("arg0.unmarshalCollection")
            .bracketopen()
            .bracketopen()
            .clazz(Object.class)
            .bracketclose()
            .append("value.getValue(")
            .string(getKey(element))
            .comma()
            .clazz(collectionType, true)
            .bracketclose()
            .comma()
            .clazz(collectionType, true)
            .comma()
            .clazz(itemType, true)
            .bracketclose();
    }

    protected EzyInstruction newUnmarshalNormalInstruction(EzyReflectElement element, Class readerImpl) {
        EzyInstruction instruction = new EzyInstruction("", "", false)
            .append("arg0.unmarshal")
            .bracketopen();
        if (readerImpl != null) {
            instruction
                .clazz(readerImpl, true)
                .comma();
        }
        Class type = getElementType(element);
        instruction
            .bracketopen()
            .clazz(Object.class)
            .bracketclose()
            .append("value.getValue(")
            .string(getKey(element))
            .comma()
            .clazz(type, true)
            .bracketclose();
        if (readerImpl == null) {
            instruction
                .comma()
                .clazz(type, true);
        }
        instruction.bracketclose();
        return instruction;
    }

    @Override
    protected void appendConstructorParamValue(
        EzyInstruction instruction,
        Parameter parameter,
        int parameterIndex,
        EzyField field,
        String key) {
        instruction
            .append("value")
            .dot()
            .append("isNotNullValue")
            .bracketopen()
            .string(key)
            .bracketclose()
            .append(" ? ")
            .append(newUnmarshalInstruction(field))
            .append(" : ")
            .defaultValue(parameter.getType());
    }

    @Override
    protected String getImplClassName() {
        return clazz.getName() + "$EzyObjectReader$EzyAutoImpl$" + COUNT.incrementAndGet();
    }

    @Override
    protected boolean isDebug() {
        return debug;
    }
}

class EzyObjectReaderElementsFetcher extends EzyObjectElementsFetcher {

    private final EzyGenericSetterValidator setterValidator = new EzyGenericSetterValidator();

    @Override
    protected boolean isValidGenericField(EzyField field) {
        return setterValidator.validate(field.getGenericType());
    }

    @Override
    protected boolean isValidGenericMethod(EzyMethod method) {
        return setterValidator.validate(((EzyByFieldMethod) method).getGenericType());
    }

    @Override
    protected List<? extends EzyMethod> getMethodList(EzyClass clazz) {
        return clazz.getSetterMethods();
    }

    @Override
    protected List<? extends EzyMethod> getDeclaredMethods(EzyClass clazz) {
        return clazz.getDeclaredSetterMethods();
    }

    @Override
    protected EzyMethod newByFieldMethod(EzyMethod method) {
        return new EzySetterMethod(method);
    }

    @Override
    protected boolean isValidAnnotatedMethod(EzyMethod method) {
        return method.getParameterCount() == 1;
    }
}
