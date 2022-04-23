package com.tvd12.ezyfox.util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.tvd12.ezyfox.constant.EzyConstant;
import com.tvd12.ezyfox.constant.EzyHasIntId;

public final class EzyEnums {

    private EzyEnums() {}

    public static <T extends EzyHasIntId> T valueOf(T[] values, int id) {
        return valueOf(values, id, v -> v.getId());
    }

    public static <T> T valueOf(
            T[] values, Object id, Function<T, Object> idFetcher) {
        for(T v : values) {
            Object vid = idFetcher.apply(v);
            if(vid.equals(id)) {
                return v;
            }
        }
        throw new IllegalArgumentException("has no enum value with id = " + id);
    }

    @SuppressWarnings("unchecked")
    public static <I,E> Map<I, E>
            enumMap(Class<E> enumClass, Function<E, I> idFetcher) {
        Object[] values = enumClass.getEnumConstants();
        Map<I, E> answer = new HashMap<>();
        for(Object value : values) {
            I id = idFetcher.apply((E)value);
            answer.put(id, (E)value);
        }
        return answer;
    }

    public static <E extends EzyConstant> Map<Integer, E> enumMapInt(
            Class<E> enumClass) {
        return enumMap(enumClass, e -> ((EzyConstant)e).getId());
    }
}
