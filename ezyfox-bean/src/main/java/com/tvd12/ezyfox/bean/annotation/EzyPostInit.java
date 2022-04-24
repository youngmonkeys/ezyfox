package com.tvd12.ezyfox.bean.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
    ElementType.METHOD
})
public @interface EzyPostInit {

    String[] value() default "";
}
