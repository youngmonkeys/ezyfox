package com.tvd12.ezyfox.database.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyQuery {

    String name() default "";

    String value() default "";

    String type() default "";

    boolean nativeQuery() default false;

    Class<?> resultType() default Object.class;
}
