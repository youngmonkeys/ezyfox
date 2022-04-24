package com.tvd12.ezyfox.data.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyCachedValue {

    String mapName() default "";

    String value() default "";

    Class<?> keyType() default Object.class;
}
