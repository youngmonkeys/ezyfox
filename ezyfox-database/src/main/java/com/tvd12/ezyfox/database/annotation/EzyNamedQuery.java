package com.tvd12.ezyfox.database.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyNamedQuery {

    String name() default "";

    String value() default "";

    String type() default "";

    boolean nativeQuery() default false;
}
