package com.tvd12.ezyfox.naming;

import lombok.Getter;

public enum EzyNamingCase {

    NATURE(""),
    UPPER(""),
    LOWER(""),
    CAMEL(""),
    DASH("-"),
    DOT("."),
    UNDERSCORE("_");

    @Getter
    private final String sign;

    private EzyNamingCase(String sign) {
        this.sign = sign;
    }

    public static EzyNamingCase of(String value) {
        if(value == null)
            return NATURE;
        return valueOf(value);
    }
}
