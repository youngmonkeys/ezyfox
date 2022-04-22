package com.tvd12.ezyfox.binding.reader;

import java.util.HashMap;
import java.util.Map;

import com.tvd12.ezyfox.binding.EzyReader;
import com.tvd12.ezyfox.binding.EzyUnmarshaller;
import com.tvd12.ezyfox.entity.EzyObject;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class EzyMapReader implements EzyReader<EzyObject, Map> {

    private static final EzyMapReader INSTANCE = new EzyMapReader();

    private EzyMapReader() {
    }

    public static EzyMapReader getInstance() {
        return INSTANCE;
    }

    @Override
    public Map read(EzyUnmarshaller unmarshaller, EzyObject object) {
        Map answer = new HashMap<>();
        for(Object key : object.keySet())
            answer.put(key, object.get(key));
        return answer;
    }
}