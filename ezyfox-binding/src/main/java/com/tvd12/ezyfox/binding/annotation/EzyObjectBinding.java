package com.tvd12.ezyfox.binding.annotation;

import com.tvd12.ezyfox.binding.EzyAccessType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The annotated class will be bind with object.
 *
 * @author tavandung12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface EzyObjectBinding {

    /**
     * Generate reader or not.
     *
     * @return make reader implementation nor not
     */
    boolean read() default true;

    /**
     * Generate writer or not.
     *
     * @return make writer implementation nor not
     */
    boolean write() default true;

    /**
     * Include sub types or not.
     *
     * @return binding all sub types
     */
    boolean subTypes() default false;

    /**
     * Get access type.
     *
     * @return the access type
     */
    int accessType() default EzyAccessType.ALL;

    /**
     * Get sub type classes to binding.
     *
     * @return sub type classes to include
     */
    Class<?>[] subTypeClasses() default {};
}
