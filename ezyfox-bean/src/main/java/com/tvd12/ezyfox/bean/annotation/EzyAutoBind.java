package com.tvd12.ezyfox.bean.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.CONSTRUCTOR
})
public @interface EzyAutoBind {

    String[] value() default {};
}
