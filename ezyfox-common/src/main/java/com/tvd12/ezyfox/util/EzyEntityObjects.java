package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.entity.EzyObject;
import com.tvd12.ezyfox.factory.EzyEntityFactory;

import java.util.Map;

@SuppressWarnings("rawtypes")
public final class EzyEntityObjects {

    private EzyEntityObjects() {}

    public static EzyObject newObject(Object key, Object value) {
        EzyObject obj = EzyEntityFactory.newObject();
        obj.put(key, value);
        return obj;
    }

    public static EzyObject newObject(Map map) {
        EzyObject obj = EzyEntityFactory.newObject();
        obj.putAll(map);
        return obj;
    }

    public static boolean isEmpty(EzyObject object) {
        return object == null || object.isEmpty();
    }
}
