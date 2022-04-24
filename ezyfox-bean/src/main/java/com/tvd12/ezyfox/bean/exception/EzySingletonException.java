package com.tvd12.ezyfox.bean.exception;

import com.tvd12.ezyfox.bean.impl.EzyBeanKey;

import java.util.Set;

public class EzySingletonException extends IllegalStateException {
    private static final long serialVersionUID = 814337130118800149L;

    public EzySingletonException(String msg) {
        super(msg);
    }

    public static EzySingletonException implementationNotFound(
        EzyBeanKey key,
        Set<Class<?>> uncompleted
    ) {
        return new EzySingletonException(
            "bean " +
                key +
                " implementation not found, uncompleted classes: " +
                uncompleted
        );
    }
}
