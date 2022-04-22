package com.tvd12.ezyfox.database.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyNamedQuery {

    String name() default "";
    String value() default "";
    String type() default "";
    boolean nativeQuery() default false;
}
