package com.tvd12.ezyfox.bean;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.tvd12.ezyfox.bean.impl.EzyBeanKey;

@SuppressWarnings("rawtypes")
public interface EzySingletonFetcher {

    <T> T getSingleton(Class<T> type);
    
    <T> T getSingleton(String name, Class<T> type);
    
    <T> T getSingleton(Map properties);
    
    List getSingletons();
    
    List getSingletons(Map properties);
    
    List getSingletons(Class annotationClass);
    
    List getSingletons(Predicate filter);
    
    List getSingletonsOf(Class parentClass);
    
    <T> T getAnnotatedSingleton(Class annotationClass);
    
    Map<EzyBeanKey, Object> getSingletonMapByKey();
}
