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
import java.util.function.Function;

@SuppressWarnings("rawtypes")
public class EzyGetterBuilder extends EzyLoggable implements EzyBuilder<Function> {

    protected static final AtomicInteger COUNT = new AtomicInteger(0);
    @Setter
    protected static boolean debug;
    protected EzyField field;
    protected EzyMethod method;
    protected Class<?> declaringClass;

    public EzyGetterBuilder field(EzyField field) {
        this.field = field;
        this.declaringClass = field.getField().getDeclaringClass();
        return this;
    }

    public EzyGetterBuilder method(EzyMethod method) {
        this.method = method;
        this.declaringClass = method.getMethod().getDeclaringClass();
        return this;
    }

    @Override
    public Function build() {
        try {
            return doBuild();
        } catch (Exception e) {
            throw new IllegalArgumentException("build getter: " + field + " error", e);
        }
    }

    protected Function doBuild() throws Exception {
        String implClassName = getImplClassName();
        ClassPool pool = ClassPool.getDefault();
        CtClass implClass = pool.makeClass(implClassName);
        String acceptMethodContent = makeApplyMethodContent();
        printMethodContent(acceptMethodContent);
        implClass.addMethod(CtNewMethod.make(acceptMethodContent, implClass));
        implClass.setInterfaces(new CtClass[]{pool.get(Function.class.getName())});
        Class answerClass = implClass.toClass();
        implClass.detach();
        return (Function) answerClass.newInstance();
    }

    protected String makeApplyMethodContent() {
        Class<?> type = null;
        String methodName = null;
        if (field != null) {
            type = field.getType();
            methodName = field.getGetterMethod();
        } else {
            type = method.getReturnType();
            methodName = method.getName();
        }
        return new EzyFunction(getEntityTypeMethod())
            .body()
            .append(new EzyInstruction("\t", "\n")
                .append(type.getTypeName())
                .append(" answer = ")
                .cast(declaringClass, "arg0")
                .dot()
                .append(methodName)
                .bracketopen()
                .bracketclose())
            .append(new EzyInstruction("\t", "\n")
                .answer()
                .valueOf(type, "answer"))
            .function()
            .toString();
    }

    protected EzyMethod getEntityTypeMethod() {
        Method method = EzyMethods.getMethod(Function.class, "apply", Object.class);
        return new EzyMethod(method);
    }

    protected String getImplClassName() {
        String name = field != null ? field.getName() : method.getFieldName();
        return declaringClass.getName() + "$" + name + "$EzyObjectProxy$GetterImpl$" + COUNT.incrementAndGet();
    }

    protected void printMethodContent(String methodContent) {
        if (debug) {
            logger.info("method content \n{}", methodContent);
        }
    }
}
