package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanContext;
import com.tvd12.ezyfox.bean.EzyBeanContextBuilder;

public interface EzyConfigurationLoader {

    EzyConfigurationLoader clazz(Class<?> clazz);

    EzyConfigurationLoader context(EzyBeanContext context);

    EzyConfigurationLoader contextBuilder(
        EzyBeanContextBuilder contextBuilder
    );

    void load();
}
