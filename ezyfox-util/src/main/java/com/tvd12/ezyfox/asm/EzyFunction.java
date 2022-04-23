package com.tvd12.ezyfox.asm;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EzyFunction {

    protected final EzyMethod method;
    protected final EzyBody body;
    protected String modifier = "public";
    protected Class<?> exceptionClass = null;

    public EzyFunction(Method method) {
        this(new EzyMethod(method));
    }

    public EzyFunction(EzyMethod method) {
        this.method = method;
        this.body = new EzyBody(this);
    }

    public EzyFunction modifier(String modifier) {
        this.modifier = modifier;
        return this;
    }

    public EzyFunction throwsException() {
        return throwsException(Exception.class);
    }

    public EzyFunction throwsException(Class<?> exceptionClass) {
        this.exceptionClass = exceptionClass;
        return this;
    }

    public EzyBody body() {
        return body;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
            .append(method.getDeclaration(modifier));
        if (exceptionClass != null) {
            builder.append(" throws ")
                .append(Exception.class.getTypeName());
        }
        builder.append(" {\n")
            .append(body)
            .append("}");
        return builder.toString();
    }

    public static class EzyBody {
        protected EzyFunction function;
        protected List<EzyInstruction> instructions = new ArrayList<>();

        public EzyBody(EzyFunction function) {
            this.function = function;
        }

        public EzyBody append(EzyInstruction instruction) {
            this.instructions.add(instruction);
            return this;
        }

        public EzyFunction function() {
            return function;
        }

        @Override
        public String toString() {
            return EzyStrings.join(instructions, "");
        }
    }
}
