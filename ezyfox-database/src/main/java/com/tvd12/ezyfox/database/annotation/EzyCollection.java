package com.tvd12.ezyfox.database.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyCollection {

    String name() default "";

    String value() default "";
}
