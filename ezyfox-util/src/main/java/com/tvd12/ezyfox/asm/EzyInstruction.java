package com.tvd12.ezyfox.asm;

import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.reflect.EzyTypes;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class EzyInstruction {

    protected final String end;
    protected final boolean semicolon;
    protected final StringBuilder builder = new StringBuilder();

    protected static final Map<Class, Class> PRIMITIVE_WRAPPER_TYPES =
        EzyTypes.PRIMITIVE_WRAPPER_TYPES_MAP;
    private static final Map<Class, String> FETCH_PRIMITIVE_METHODS =
        fetchPrimitiveMethods();

    public EzyInstruction() {
        this("");
    }

    public EzyInstruction(String begin) {
        this(begin, "");
    }

    public EzyInstruction(String begin, String end) {
        this(begin, end, true);
    }

    public EzyInstruction(String begin, String end, boolean semicolon) {
        this.end = end;
        this.builder.append(begin);
        this.semicolon = semicolon;
    }

    private static Map<Class, String> fetchPrimitiveMethods() {
        Map<Class, String> map = new HashMap<>();
        map.put(Boolean.class, "booleanValue()");
        map.put(Byte.class, "byteValue()");
        map.put(Character.class, "charValue()");
        map.put(Double.class, "doubleValue()");
        map.put(Float.class, "floatValue()");
        map.put(Integer.class, "intValue()");
        map.put(Long.class, "longValue()");
        map.put(Short.class, "shortValue()");
        return map;
    }

    public EzyInstruction equal() {
        builder.append(" = ");
        return this;
    }

    public EzyInstruction finish() {
        builder.append(";");
        return this;
    }

    public EzyInstruction constructor(Class clazz) {
        return clazz(clazz).append("()");
    }

    public EzyInstruction append(Object value) {
        builder.append(value.toString());
        return this;
    }

    public EzyInstruction append(String str) {
        builder.append(str);
        return this;
    }

    public EzyInstruction append(EzyInstruction instruction) {
        return append(instruction.toString());
    }

    public EzyInstruction string(String str) {
        builder.append("\"").append(str).append("\"");
        return this;
    }

    public EzyInstruction clazz(Class clazz) {
        return clazz(clazz, false);
    }

    public EzyInstruction clazz(Class clazz, boolean extension) {
        append(clazz.getTypeName());
        return extension ? append(".class") : this;
    }

    public EzyInstruction dot() {
        return append(".");
    }

    public EzyInstruction comma() {
        return append(", ");
    }

    public EzyInstruction bracketopen() {
        return append("(");
    }

    public EzyInstruction bracketclose() {
        return append(")");
    }

    public EzyInstruction brackets(Class clazz) {
        builder.append("(").append(clazz.getTypeName()).append(")");
        return this;
    }

    public EzyInstruction brackets(String expression) {
        builder.append("(").append(expression).append(")");
        return this;
    }

    public EzyInstruction answer() {
        return append("return ");
    }

    public EzyInstruction variable(Class type) {
        return variable(type, EzyClasses.getVariableName(type));
    }

    public EzyInstruction variable(Class type, String name) {
        builder.append(type.getTypeName()).append(" ").append(name);
        return this;
    }

    public EzyInstruction cast(Class type, String expression) {
        if (PRIMITIVE_WRAPPER_TYPES.containsKey(type)) {
            return castPrimitive(type, expression);
        }
        return castNormal(type, expression);
    }

    protected EzyInstruction castNormal(Class type, String expression) {
        builder
            .append("(")
            .append("(").append(type.getTypeName()).append(")")
            .append("(").append(expression).append(")")
            .append(")");
        return this;
    }

    protected EzyInstruction castPrimitive(Class type, String expression) {
        Class wrapperType = PRIMITIVE_WRAPPER_TYPES.get(type);
        String primitiveValueMethod = FETCH_PRIMITIVE_METHODS.get(wrapperType);
        castNormal(wrapperType, expression);
        dot();
        append(primitiveValueMethod);
        return this;
    }

    public EzyInstruction function(String method, String... args) {
        return append(method)
            .brackets(EzyStrings.join(args, ", "));
    }

    public EzyInstruction invoke(String object, String method, String... args) {
        return append(object)
            .dot()
            .function(method, args);
    }

    public EzyInstruction valueOf(Class type, String expression) {
        return valueOf(type, expression, false);
    }

    public EzyInstruction valueOf(Class type, String expression, boolean forceCast) {
        Class wrapperType = PRIMITIVE_WRAPPER_TYPES.get(type);
        if (wrapperType != null) {
            return clazz(wrapperType).append(".valueOf").brackets(expression);
        }
        if (forceCast) {
            return castNormal(type, expression);
        }
        return append(expression);
    }

    public EzyInstruction defaultValue(Class type) {
        if (type == boolean.class) {
            return append("false");
        }
        if (type == byte.class) {
            return append("(byte)0");
        }
        if (type == char.class) {
            return append("(char)0");
        }
        if (type == double.class) {
            return append("0D");
        }
        if (type == float.class) {
            return append("0F");
        }
        if (type == int.class) {
            return append("0");
        }
        if (type == long.class) {
            return append("0L");
        }
        if (type == short.class) {
            return append("(short)0");
        }
        return append("null");
    }

    @Override
    public String toString() {
        return toString(true);
    }

    public String toString(boolean autoFinish) {
        String string = builder.toString();
        if (autoFinish && semicolon) {
            string = string.endsWith(";") ? string : string + ";";
        }
        return string + end;
    }
}
