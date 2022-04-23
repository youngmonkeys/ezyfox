package com.tvd12.ezyfox.annotation;

import java.lang.annotation.*;

@Documented
@Target({
    ElementType.FIELD,
    ElementType.METHOD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface EzyProperty {

    String value() default "";

    String prefix() default "";
}
