package com.tvd12.ezyfox.testing.util;

import com.tvd12.ezyfox.util.EzyRoundRobin;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

public class EzyRoundRobinTest {

    @Test
    public void test() {
        EzyRoundRobin<Integer> rr = new EzyRoundRobin<>(() -> 1, 1);
        rr.add(2);
        rr.add(3);
        rr.forEach(i -> System.out.println(i));
        assert rr.get().equals(1);
        assert rr.get().equals(2);
        assert rr.get().equals(3);
        assert rr.get().equals(1);
        assert rr.get().equals(2);
        assert rr.get().equals(3);
        assert rr.get().equals(1);
        assert rr.get().equals(2);
        assert rr.get().equals(3);

        long time = Performance.create()
            .test(() -> {
                rr.get();
            }).getTime();
        System.out.println("time: " + time);
    }
}
