package com.tvd12.ezyfox.reflect;

public final class EzyPackages {

    private EzyPackages() {
    }

    public static EzyReflection scanPackage(String packet) {
        return new EzyReflectionProxy(packet);
    }

    public static EzyReflection scanPackages(Iterable<String> packages) {
        return new EzyReflectionProxy(packages);
    }

}
