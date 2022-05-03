package com.tvd12.ezyfox.constant;

import com.tvd12.ezyfox.util.EzyEnums;
import lombok.Getter;

import java.util.Map;

public enum EzyLogLevel implements EzyConstant {

    TRACE(1, "trace"),
    DEBUG(2, "debug"),
    INFO(3, "info"),
    WARN(4, "warn"),
    ERROR(5, "error"),
    FATAL(6, "fatal");

    @Getter
    private final int id;
    @Getter
    private final String name;


    private static final Map<String, EzyLogLevel> MAP_BY_NAME =
        EzyEnums.enumMap(EzyLogLevel.class, it -> it.name);

    EzyLogLevel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EzyLogLevel ofName(String name) {
        return MAP_BY_NAME.get(name);
    }
}
