package com.tvd12.ezyfox.binding.reader;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.entity.EzyObject;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EzyConcurrentHashMapReader implements EzyReader<EzyObject, Map> {

    private static final EzyConcurrentHashMapReader INSTANCE = new EzyConcurrentHashMapReader();

    private EzyConcurrentHashMapReader() {
    }

    public static EzyConcurrentHashMapReader getInstance() {
        return INSTANCE;
    }

    @Override
    public Map read(EzyUnmarshaller unmarshaller, EzyObject object) {
        Map answer = new ConcurrentHashMap<>();
        for(Object key : object.keySet())
            answer.put(key, object.get(key));
        return answer;
    }
}
