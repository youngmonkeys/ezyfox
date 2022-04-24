package com.tvd12.ezyfox.database.util;

import com.tvd12.ezyfox.database.annotation.EzyRepository;
import com.tvd12.ezyfox.io.EzyStrings;

import static com.tvd12.ezyfox.reflect.EzyClasses.getVariableName;

public final class EzyRepositoryAnnotations {

    private EzyRepositoryAnnotations() {}

    public static String getRepoName(Object repo) {
        return getRepoName(repo.getClass());
    }

    public static String getRepoName(Class<?> clazz) {
        EzyRepository anno = clazz.getAnnotation(EzyRepository.class);
        String name = anno.value();
        return EzyStrings.isNoContent(name) ? getVariableName(clazz) : name;
    }
}
