package com.tvd12.ezyfox.codec.testing;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressWarnings("rawtypes")
public class GetParameterTypeTest {

    public static void main(String[] args) {

        Type type = StringList.class.getGenericSuperclass();
        System.out.println(type);
        ParameterizedType pt = (ParameterizedType) type;
        System.out.println(pt.getActualTypeArguments()[0]);

    }

    @SuppressWarnings("unused")
    public static Class<?> getGenericType(Class clazz) {
        Type type = clazz.getGenericSuperclass();
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("The field "
                + clazz.getName()
                + " on class " + clazz.getDeclaringClass()
                + " is not generic type");
        }

        Type[] types = ((ParameterizedType) type).getActualTypeArguments();

        System.out.println(Arrays.toString((((TypeVariable) types[0]).getGenericDeclaration()).getTypeParameters()));

        if (types.length != 1) {
            throw new IllegalArgumentException(types.length
                + " template parameter(s) is not allowed at the field "
                + clazz.getName()
                + " on class " + clazz);
        }

        if (types[0] instanceof ParameterizedType) {
            throw new IllegalArgumentException("Unsupported Collection<Collection> "
                + "data type for parameter on field "
                + clazz.getName()
                + " on class " + clazz);
        }

        if (!(types[0] instanceof Class<?>)) {
            throw new IllegalArgumentException("You must specific "
                + "a type for collection in field "
                + clazz.getName()
                + " on class " + clazz);
        }
        return (Class<?>) types[0];
    }
}

class StringList extends ArrayList<String> {
    private static final long serialVersionUID = 3251751102634031407L;
}
