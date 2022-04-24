package com.tvd12.ezyfox.bean.annotation;

import com.tvd12.ezyfox.annotation.EzyKeyValue;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author tavandung12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface EzySingleton {

    String value() default "";

    EzyKeyValue[] properties() default {};
}
