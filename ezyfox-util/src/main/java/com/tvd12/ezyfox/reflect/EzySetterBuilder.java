package com.tvd12.ezyfox.reflect;

import com.tvd12.ezyfox.asm.EzyFunction;
import com.tvd12.ezyfox.asm.EzyInstruction;
import com.tvd12.ezyfox.builder.EzyBuilder;
import com.tvd12.ezyfox.util.EzyLoggable;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtNewMethod;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

@SuppressWarnings("rawtypes")
public class EzySetterBuilder extends EzyLoggable implements EzyBuilder<BiConsumer> {

    protected static final AtomicInteger COUNT = new AtomicInteger(0);
    @Setter
    protected static boolean debug;
    protected EzyField field;
    protected EzyMethod method;
    protected Class<?> declaringClass;

    public EzySetterBuilder field(EzyField field) {
        this.field = field;
        this.declaringClass = field.getField().getDeclaringClass();
        return this;
    }

    public EzySetterBuilder method(EzyMethod method) {
        this.method = method;
        this.declaringClass = method.getMethod().getDeclaringClass();
        return this;
    }

    @Override
    public BiConsumer build() {
        try {
            return doBuild();
        } catch (Exception e) {
            throw new IllegalArgumentException("build setter: " + field + " error", e);
        }
    }

    protected BiConsumer doBuild() throws Exception {
        String implClassName = getImplClassName();
        ClassPool pool = ClassPool.getDefault();
        CtClass implClass = pool.makeClass(implClassName);
        String acceptMethodContent = makeAcceptMethodContent();
        printMethodContent(acceptMethodContent);
        implClass.addMethod(CtNewMethod.make(acceptMethodContent, implClass));
        implClass.setInterfaces(new CtClass[]{pool.get(BiConsumer.class.getName())});
        Class answerClass = implClass.toClass();
        implClass.detach();
        return EzyClasses.newInstance(answerClass);
    }

    protected String makeAcceptMethodContent() {
        Class<?> type;
        String methodName;
        if (field != null) {
            type = field.getType();
            methodName = field.getSetterMethod();
        } else {
            type = method.getParameterTypes()[0];
            methodName = method.getName();
        }
        return new EzyFunction(getEntityTypeMethod())
            .body()
            .append(new EzyInstruction("\t", "\n")
                .cast(declaringClass, "arg0")
                .dot()
                .append(methodName)
                .bracketopen()
                .cast(type, "arg1")
                .bracketclose())
            .function()
            .toString();
    }

    protected EzyMethod getEntityTypeMethod() {
        Method method = EzyMethods.getMethod(BiConsumer.class, "accept", Object.class, Object.class);
        return new EzyMethod(method);
    }

    protected String getImplClassName() {
        String name = field != null ? field.getName() : method.getFieldName();
        return declaringClass.getName() + "$" + name + "$EzyObjectProxy$SetterImpl$" + COUNT.incrementAndGet();
    }

    protected void printMethodContent(String methodContent) {
        if (debug) {
            logger.info("method content \n{}", methodContent);
        }
    }
}
