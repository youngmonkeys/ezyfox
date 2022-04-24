package com.tvd12.ezyfox.testing.performance;

import com.tvd12.ezyfox.io.EzyBytes;
import com.tvd12.test.performance.Performance;
import org.testng.annotations.Test;

public class EzyBytesCopyTest {

    @Test
    public void test1() {
        byte[][] byteArrays = new byte[][]{
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}
        };
        long time = Performance.create()
            .loop(1000000)
            .test(() -> EzyBytes.merge(byteArrays))
            .getTime();
        System.out.println("test1 elapsed time = " + time);
    }

    @SuppressWarnings("ALL")
    @Test
    public void test2() {
        byte[][] byteArrays = new byte[][]{
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4},
            {1, 2}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}, {3, 4}
        };
        long time = Performance.create()
            .loop(1000000)
            .test(() -> {
                int passed = 0;
                byte[] bytes = new byte[EzyBytes.totalBytes(byteArrays)];
                for (byte[] value : byteArrays) {
                    System.arraycopy(value, 0, bytes, passed, value.length);
                    passed += value.length;
                }
            })
            .getTime();
        System.out.println("test2 elapsed time = " + time);
    }
}
