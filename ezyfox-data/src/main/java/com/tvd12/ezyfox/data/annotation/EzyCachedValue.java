package com.tvd12.ezyfox.data.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyCachedValue {

    String mapName() default "";

    String value() default "";

    Class<?> keyType() default Object.class;
}
