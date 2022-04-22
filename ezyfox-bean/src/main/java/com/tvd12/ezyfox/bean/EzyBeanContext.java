package com.tvd12.ezyfox.bean;

import java.util.Properties;
import java.util.Set;

import com.tvd12.ezyfox.bean.impl.EzySimpleBeanContext;

public interface EzyBeanContext extends 
        EzyBeanFetcher, 
        EzySingletonFetcher, 
        EzyPrototypeFetcher,
        EzyPropertyFetcher {
    
    String ACTIVE_PROFILES_KEY = "active_profiles";
    String EZYFOX_ACTIVE_PROFILES_KEY = "ezyfox.active_profiles";
    
    Properties getProperties();
    
    Set<String> getPackagesToScan();
    
    EzySingletonFactory getSingletonFactory();
    
    EzyPrototypeFactory getPrototypeFactory();
    
    EzyBeanNameTranslator getBeanNameTranslator();
    
    static EzyBeanContextBuilder builder() {
        return EzySimpleBeanContext.builder();
    }
    }