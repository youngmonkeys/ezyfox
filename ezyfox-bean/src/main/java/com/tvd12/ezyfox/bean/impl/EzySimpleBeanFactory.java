package com.tvd12.ezyfox.bean.impl;

import com.tvd12.ezyfox.bean.EzyBeanNameTranslator;
import com.tvd12.ezyfox.reflect.EzyClasses;
import com.tvd12.ezyfox.util.EzyLoggable;
import lombok.Setter;

public class EzySimpleBeanFactory extends EzyLoggable {

    @Setter
    protected EzyBeanNameTranslator beanNameTranslator;

    protected final String translateBeanName(String name, Class<?> type) {
        if (beanNameTranslator == null) {
            return name;
        }
        return beanNameTranslator.translate(name, type);
    }

    protected final void mapBeanName(String freeName, Class<?> type, String realName) {
        if (beanNameTranslator != null) {
            beanNameTranslator.map(freeName, type, realName);
        }
    }

    protected final String getDefaultBeanName(Class<?> type) {
        return EzyClasses.getVariableName(type);
    }
}
