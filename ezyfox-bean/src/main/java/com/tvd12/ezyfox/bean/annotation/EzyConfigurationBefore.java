package com.tvd12.ezyfox.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated class will be run before the annotated @EzyConfiguration class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EzyConfigurationBefore {

    int priority() default 0;
}
