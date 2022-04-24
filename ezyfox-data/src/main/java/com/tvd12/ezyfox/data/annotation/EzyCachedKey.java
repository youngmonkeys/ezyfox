package com.tvd12.ezyfox.data.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyCachedKey {

    boolean composite() default false;
}
