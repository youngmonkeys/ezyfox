package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanNameTranslator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.tvd12.ezyfox.bean.impl.EzyBeanKey.of;

public class EzySimpleBeanNameTranslator implements EzyBeanNameTranslator {

    protected final Map<EzyBeanKey, String> map = new ConcurrentHashMap<>();

    @Override
    public String translate(String name, Class<?> type) {
        EzyBeanKey key = of(name, type);
        return map.getOrDefault(key, name);
    }

    @Override
    public void map(String freeName, Class<?> type, String realName) {
        map.put(of(freeName, type), realName);
    }
}
