package com.tvd12.ezyfox.bean.impl;

import static com.tvd12.ezyfox.bean.impl.EzyBeanKey.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.tvd12.ezyfox.bean.EzyBeanNameTranslator;

public class EzySimpleBeanNameTranslator implements EzyBeanNameTranslator {

    protected final Map<EzyBeanKey, String> map = new ConcurrentHashMap<>();

    @Override
    public String translate(String name, Class<?> type) {
        EzyBeanKey key = of(name, type);
        return map.containsKey(key) ? map.get(key) : name;
    }

    @Override
    public void map(String freename, Class<?> type, String realname) {
        map.put(of(freename, type), realname);
    }}