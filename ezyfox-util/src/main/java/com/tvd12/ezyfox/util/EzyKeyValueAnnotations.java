package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.annotation.EzyKeyValue;

import java.util.HashMap;
import java.util.Map;

public final class EzyKeyValueAnnotations {

    private EzyKeyValueAnnotations() {}

    public static String getProperty(String key, EzyKeyValue[] kvs) {
        for (EzyKeyValue kv : kvs) {
            if (key.equals(kv.key())) {
                return kv.value();
            }
        }
        return null;
    }

    public static Map<String, String> getProperties(EzyKeyValue[] kvs) {
        Map<String, String> answer = new HashMap<>();
        for (EzyKeyValue kv : kvs) {
            answer.put(kv.key(), kv.value());
        }
        return answer;
    }
}
