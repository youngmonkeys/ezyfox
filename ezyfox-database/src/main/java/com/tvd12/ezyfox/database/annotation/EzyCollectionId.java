package com.tvd12.ezyfox.database.annotation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyCollectionId {

    boolean composite() default false;
}
