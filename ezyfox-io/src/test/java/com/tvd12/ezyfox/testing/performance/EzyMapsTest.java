package com.tvd12.ezyfox.testing.performance;

import com.tvd12.ezyfox.io.EzyMaps;
import com.tvd12.test.performance.Performance;

import java.util.HashMap;
import java.util.Map;

public class EzyMapsTest {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args) {
        Map<Class, Object> map = new HashMap<>();
        map.put(A.class, new E());
        long time = Performance.create()
            .loop(1000000)
            .test(() -> EzyMaps.getValue(map, A.class))
            .getTime();
        System.out.println(time);
    }

    public interface A {}

    public interface B extends A {}

    public interface C extends A {}

    public static class D implements C {}

    public static class E extends D {}
}
