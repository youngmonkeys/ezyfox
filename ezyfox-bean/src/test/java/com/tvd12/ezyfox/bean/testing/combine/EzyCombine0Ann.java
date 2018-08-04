package com.tvd12.ezyfox.bean.testing.combine;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.tvd12.ezyfox.annotation.EzyKeyValue;

/**
 * 
 * @author tavandung12
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD, ElementType.METHOD })
public @interface EzyCombine0Ann {

	String value() default "";
	
	EzyKeyValue[] properties() default {};
	
}
