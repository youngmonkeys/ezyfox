package com.tvd12.ezyfox.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifics reader for a field or a method.
 *
 * @author tavandung12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface EzyReader {

    /**
     * the key mapped to value.
     *
     * @return the key mapped to value
     */
    @SuppressWarnings("rawtypes")
    Class<? extends com.tvd12.ezyfox.binding.EzyReader> value();
}
