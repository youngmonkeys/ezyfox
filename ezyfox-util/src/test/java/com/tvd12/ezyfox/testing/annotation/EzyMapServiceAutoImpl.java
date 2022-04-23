package com.tvd12.ezyfox.testing.annotation;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.annotation.EzyKeyValue;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({TYPE})
@EzyAutoImpl(properties = @EzyKeyValue(key = "map-name", value = ""))
public @interface EzyMapServiceAutoImpl {
}
