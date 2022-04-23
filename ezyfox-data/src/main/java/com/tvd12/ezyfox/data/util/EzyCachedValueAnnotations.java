package com.tvd12.ezyfox.data.util;

import com.tvd12.ezyfox.data.annotation.EzyCachedValue;
import com.tvd12.ezyfox.io.EzyStrings;

public final class EzyCachedValueAnnotations {

    private EzyCachedValueAnnotations() {}

    public static String getMapName(Class<?> cachedValueClass) {
        String mapName = getMapName(cachedValueClass.getAnnotation(EzyCachedValue.class));
        if(EzyStrings.isNoContent(mapName))
            mapName = cachedValueClass.getSimpleName();
        return mapName;
    }

    public static String getMapName(EzyCachedValue anno) {
        String mapName = anno.value();
        if(EzyStrings.isNoContent(mapName))
            mapName = anno.mapName();
        return mapName;
    }
}
