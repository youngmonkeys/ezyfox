package com.tvd12.ezyfox.binding.impl;

import com.tvd12.ezyfox.binding.EzyBindingConfig;
import com.tvd12.ezyfox.binding.EzyBindingContext;
import com.tvd12.ezyfox.binding.EzyBindingContextAware;
import com.tvd12.ezyfox.reflect.EzyClass;

public class EzySimpleConfigurationLoader implements EzyConfigurationLoader {

    private final EzyClass clazz;

    public EzySimpleConfigurationLoader(EzyClass clazz) {
        this.clazz = clazz;
    }

    @Override
    public void load(EzyBindingContext context) {
        Object configurator = clazz.newInstance();
        if (configurator instanceof EzyBindingContextAware) {
            ((EzyBindingContextAware) configurator).setContext(context);
        }
        if (configurator instanceof EzyBindingConfig) {
            ((EzyBindingConfig) configurator).config();
        }
    }
}
