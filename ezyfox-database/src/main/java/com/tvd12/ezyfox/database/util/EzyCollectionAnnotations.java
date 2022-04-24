package com.tvd12.ezyfox.database.util;

import com.tvd12.ezyfox.database.annotation.EzyCollection;
import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyCollectionAnnotations {

    private EzyCollectionAnnotations() {}

    public static String getCollectionName(EzyCollection anno) {
        String answer = anno.value();
        if (EzyStrings.isNoContent(answer)) {
            answer = anno.name();
        }
        return answer;
    }

    public static String getCollectionName(Class<?> entityClass) {
        EzyCollection anno = entityClass.getAnnotation(EzyCollection.class);
        String name = null;
        if (anno != null) {
            name = getCollectionName(anno);
        }
        if (EzyStrings.isNoContent(name)) {
            name = entityClass.getSimpleName();
        }
        return name;
    }
}
