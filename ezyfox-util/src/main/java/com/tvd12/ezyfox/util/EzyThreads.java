package com.tvd12.ezyfox.util;

public final class EzyThreads {

    private EzyThreads() {}

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }
}
