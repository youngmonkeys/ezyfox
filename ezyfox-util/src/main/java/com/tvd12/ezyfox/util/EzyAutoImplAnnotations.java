package com.tvd12.ezyfox.util;

import com.tvd12.ezyfox.annotation.EzyAutoImpl;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.reflect.EzyClasses;

public final class EzyAutoImplAnnotations {

    private EzyAutoImplAnnotations() {
    }

    public static String getBeanName(Class<?> annotatedClass) {
        EzyAutoImpl anno = annotatedClass.getAnnotation(EzyAutoImpl.class);
        String beanName = anno.value().trim();
        if (!EzyStrings.isNoContent(beanName)) {
            return beanName;
        }
        beanName = EzyKeyValueAnnotations.getProperty("name", anno.properties());
        if (!EzyStrings.isNoContent(beanName)) {
            return beanName;
        }
        return EzyClasses.getVariableName(annotatedClass);
    }
}
