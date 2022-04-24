package com.tvd12.ezyfox.bean.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EzyPropertiesSources {

    /**
     * properties source files.
     *
     * @return array of properties source files
     */
    String[] value();
}
