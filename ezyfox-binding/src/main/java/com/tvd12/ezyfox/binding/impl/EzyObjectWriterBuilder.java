package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.binding.EzyAccessType;
import com.tvd12.ezyfox.binding.annotation.EzyObjectBinding;
import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;
import com.tvd12.ezyfox.reflect.*;
import lombok.Setter;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("rawtypes")
public class EzyObjectWriterBuilder
    extends EzyAbstractWriterBuilder {

    protected static final AtomicInteger COUNT = new AtomicInteger(0);
    @Setter
    protected static boolean debug;

    public EzyObjectWriterBuilder(EzyClass clazz) {
        super(clazz);
    }

    @Override
    protected Class<?> getOutType() {
        return EzyObject.class;
    }

    @Override
    protected int getAccessType(EzyClass clazz) {
        EzyObjectBinding ann = clazz.getAnnotation(EzyObjectBinding.class);
        return ann == null ? EzyAccessType.ALL : ann.accessType();
    }

    @Override
    protected EzyElementsFetcher newElementsFetcher() {
        return new EzyObjectWriterElementsFetcher();
    }

    @Override
    protected String makeImplMethodContent(EzyMethod writeMethod) {
        EzyFunction.EzyBody methodBody = new EzyFunction(writeMethod)
            .modifier("protected")
            .body()
            .append(new EzyInstruction("\t", "\n")
                .variable(clazz.getClazz(), "value")
                .equal()
                .cast(clazz.getClazz(), "arg1")
            )
            .append(new EzyInstruction("\t", "\n")
                .variable(EzyObject.class, "object")
                .equal()
                .clazz(EzyEntityFactory.class)
                .dot()
                .append("newObject()")
            );
        for (Object element : getElements()) {
            methodBody.append(newInstructionByElement(element));
        }
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
        return newInstruction(field, "");
    }

    protected EzyInstruction newInstructionByMethod(EzyMethod method) {
        return newInstruction(method, "()");
    }

    protected EzyInstruction newInstruction(
        EzyReflectElement element, String valueExpSuffix) {
        EzyInstruction instruction = new EzyInstruction("\t", "\n")
            .append("object")
            .dot()
            .append("put")
            .bracketopen()
            .string(getKey(element))
            .comma()
            .brackets(Object.class)
            .append("arg0.marshal(");
        com.tvd12.ezyfox.binding.annotation.EzyWriter wrt = getWriterClass(element);
        if (wrt != null) {
            instruction
                .clazz(wrt.value(), true)
                .comma();
        }
        Class type = getElementType(element);
        instruction
            .valueOf(type, "value." + element.getName() + valueExpSuffix)
            .append(")")
            .bracketclose();
        return instruction;
    }

    @Override
    protected String getImplClassName() {
        return clazz.getName() + "$EzyObjectWriter$EzyAutoImpl$" + COUNT.incrementAndGet();
    }

    @Override
    protected boolean isDebug() {
        return debug;
    }

    public static class EzyObjectWriterElementsFetcher extends EzyObjectElementsFetcher {

        @Override
        protected List<? extends EzyMethod> getMethodList(EzyClass clazz) {
            return clazz.getGetterMethods();
        }

        @Override
        protected List<? extends EzyMethod> getDeclaredMethods(EzyClass clazz) {
            return clazz.getDeclaredGetterMethods();
        }

        @Override
        protected EzyMethod newByFieldMethod(EzyMethod method) {
            return new EzyGetterMethod(method);
        }

        @Override
        protected boolean isValidAnnotatedMethod(EzyMethod method) {
            return method.getReturnType() != void.class;
        }
    }
}
