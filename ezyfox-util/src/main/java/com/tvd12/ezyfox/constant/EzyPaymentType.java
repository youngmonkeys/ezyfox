package com.tvd12.ezyfox.constant;

import com.tvd12.ezyfox.util.EzyEnums;
import lombok.Getter;

import java.util.Map;

public enum EzyPaymentType implements EzyConstant {

    FREE(1, "free"),
    MONTHLY(1, "monthly"),
    QUARTERLY(2, "quarterly"),
    ANNUALLY(3, "annually"),
    PERMANENTLY(4, "permanently");

    private static final Map<String, EzyPaymentType> MAP_BY_NAME =
        EzyEnums.enumMap(EzyPaymentType.class, it -> it.name);
    @Getter
    private final int id;
    @Getter
    private final String name;

    private EzyPaymentType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static EzyPaymentType ofName(String name) {
        return MAP_BY_NAME.get(name);
    }
}
