package com.tvd12.ezyfox.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated field or index will be serialized/deserialized with index
 * or native index by default.
 *
 * @author tavandung12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface EzyIndex {

    /**
     * the key mapped to value, start from 0.
     *
     * @return the key mapped to value
     */
    int value();
}
