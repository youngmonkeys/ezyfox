package com.tvd12.ezyfox.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Import classes to the context to manage or process
 * 
 * @author tavandung12
 *
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface EzyImport {
    
    /**
     * the array of classes
     * 
     * @return array of classes
     */
    public Class<?>[] value();
}
