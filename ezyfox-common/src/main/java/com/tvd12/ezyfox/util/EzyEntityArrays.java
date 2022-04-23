package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.entity.EzyArray;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import java.util.List;

@SuppressWarnings("rawtypes")
public final class EzyEntityArrays {

    private EzyEntityArrays() {}

    public static EzyArray newArray() {
        return EzyEntityFactory.newArray();
    }

    public static EzyArray newArray(List list) {
        EzyArray array = EzyEntityFactory.newArray();
        array.add(list);
        return array;
    }

    public static EzyArray newArray(Object... args) {
        EzyArray array = EzyEntityFactory.newArray();
        array.add(args);
        return array;
    }

    public static boolean isEmpty(EzyArray array) {
        return array == null || array.isEmpty();
    }
}
