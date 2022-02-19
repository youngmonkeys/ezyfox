package com.tvd12.ezyfox.constant;

import lombok.Getter;

public enum EzyPaymentType implements EzyConstant {
    
    MONTHLY(1, "monthly"),
    QUARTERLY(2, "quarterly"),
    ANNUALLY(3, "annually"),
    PERMANENTLY(4, "permanently");

    @Getter
    private final int id;
    
    @Getter
    private final String name;
    
    private EzyPaymentType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
