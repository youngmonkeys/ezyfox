package com.tvd12.ezyfox.sercurity;

import java.util.UUID;

public final class EzyUuid {

    private EzyUuid() {}

    public static UUID random() {
        return UUID.randomUUID();
    }
}
