package com.tvd12.ezyfox.binding.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated field or method will be serialized with a key or field name by default.
 *
 * @author tavandung12
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface EzyValue {

    /**
     * the key mapped to value.
     *
     * @return the key mapped to value
     */
    String value() default "";
}
