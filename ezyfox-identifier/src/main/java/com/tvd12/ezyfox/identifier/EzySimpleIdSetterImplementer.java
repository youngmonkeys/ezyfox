package com.tvd12.ezyfox.identifier;

import com.tvd12.ezyfox.annotation.EzyId;
import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.reflect.*;
import com.tvd12.ezyfox.util.EzyHasIdEntity;
import com.tvd12.ezyfox.util.EzyLoggable;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import lombok.Setter;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class EzySimpleIdSetterImplementer
    extends EzyLoggable
    implements EzyIdSetterImplementer {

    @Setter
    protected static boolean debug = false;
    protected final EzyClass clazz;
    protected final EzyReflectElement idElement;

    protected static final AtomicInteger COUNT = new AtomicInteger(0);

    public EzySimpleIdSetterImplementer(Class<?> clazz) {
        this(new EzyClass(clazz));
    }

    public EzySimpleIdSetterImplementer(EzyClass clazz) {
        this.clazz = clazz;
        this.idElement = getIdElement0();
    }

    @Override
    public EzyIdSetter implement() {
        try {
            return doImplement();
        } catch (Exception e) {
            throw new IllegalStateException("implement setter of " + clazz + " error", e);
        }
    }

    private EzyIdSetter doImplement() throws Exception {
        ClassPool pool = ClassPool.getDefault();
        String implClassName = getImplClassName();
        CtClass implClass = pool.makeClass(implClassName);
        EzyMethod setIdMethod = getSetIdMethod();
        String implMethodContent = makeSetIdMethodContent(setIdMethod);
        setIdMethod.setDisplayName("setId");
        printMethodContent(implMethodContent);
        implClass.setInterfaces(new CtClass[]{pool.get(EzyIdSetter.class.getName())});
        implClass.addMethod(CtNewMethod.make(implMethodContent, implClass));
        Class<?> answerClass = implClass.toClass();
        implClass.detach();
        return EzyClasses.newInstance(answerClass);
    }

    protected String makeSetIdMethodContent(EzyMethod setIdMethod) {
        EzyInstruction instruction = new EzyInstruction("\t", "\n")
            .append("object.")
            .append(idElement.getName());
        Class<?> valueType;
        if (idElement instanceof EzyField) {
            valueType = ((EzyField) idElement).getType();
            instruction
                .equal()
                .append("id");
        } else {
            valueType = ((EzyMethod) idElement).getParameterTypes()[0];
            instruction
                .brackets("id");
        }

        return new EzyFunction(setIdMethod)
            .body()
            .append(new EzyInstruction("\t", "\n")
                .variable(clazz.getClazz(), "object")
                .equal()
                .cast(clazz.getClazz(), "arg0"))
            .append(new EzyInstruction("\t", "\n")
                .variable(valueType, "id")
                .equal()
                .cast(valueType, "arg1"))
            .append(instruction)
            .function()
            .toString();
    }

    protected EzyMethod getSetIdMethod() {
        return EzyMethod.builder()
            .clazz(EzyIdSetter.class)
            .methodName("setId")
            .parameterTypes(Object.class)
            .parameterTypes(Object.class)
            .build();
    }

    private EzyReflectElement getIdElement0() {
        EzyReflectElement element = getIdElement();
        if (element != null) {
            return element;
        }
        if (EzyHasIdEntity.class.isAssignableFrom(clazz.getClazz())) {
            return clazz.getSetterMethod("setId");
        }
        Optional<EzyField> foundField =
            clazz.getField(f -> f.isAnnotated(EzyId.class));
        if (foundField.isPresent()) {
            EzyField field = foundField.get();
            if (field.isPublic()) {
                return field;
            }
            EzyMethod method = clazz.getMethod(field.getSetterMethod());
            if (method != null && method.isPublic()) {
                return method;
            }
        }
        Optional<EzyMethod> foundMethod =
            clazz.getSetterMethod(m -> m.isAnnotated(EzyId.class));
        if (foundMethod.isPresent()) {
            return foundMethod.get();
        }
        throw new IllegalStateException("use @EzyId to specific 'id' element on " + clazz);
    }

    protected EzyReflectElement getIdElement() {
        return null;
    }

    protected String getImplClassName() {
        return clazz.getName() + "$EzyIdSetter$EzyAutoImpl$" + COUNT.incrementAndGet();
    }

    protected void printMethodContent(String methodContent) {
        if (debug) {
            logger.debug("getId: method content \n{}", methodContent);
        }
    }
}
