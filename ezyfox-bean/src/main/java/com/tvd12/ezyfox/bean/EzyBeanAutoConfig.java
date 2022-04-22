package com.tvd12.ezyfox.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface EzyBeanAutoConfig extends EzyBeanConfig {
    
    Logger LOGGER = LoggerFactory.getLogger(EzyBeanContext.class);
    
    void autoConfig();
    
    @Override
    default void config() {
        try {
            autoConfig();
        }
        catch (Throwable e) {
            LOGGER.debug("{} auto config failed due to: {} ({})", getClass().getName(), e.getClass().getName(), e.getMessage());
        }
    }
    }