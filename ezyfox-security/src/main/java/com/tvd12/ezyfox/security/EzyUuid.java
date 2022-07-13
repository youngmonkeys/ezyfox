package com.tvd12.ezyfox.security;

import java.util.UUID;

public final class EzyUuid {

    private EzyUuid() {}

    public static UUID random() {
        return UUID.randomUUID();
    }
}
