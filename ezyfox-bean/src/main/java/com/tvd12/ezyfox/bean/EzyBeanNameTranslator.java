package com.tvd12.ezyfox.bean;

public interface EzyBeanNameTranslator {

    String translate(String name, Class<?> type);

    void map(String freeName, Class<?> type, String realName);
}
