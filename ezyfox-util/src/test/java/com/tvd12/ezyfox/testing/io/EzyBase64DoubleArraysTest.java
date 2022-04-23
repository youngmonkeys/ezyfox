package com.tvd12.ezyfox.testing.io;

import com.tvd12.ezyfox.io.EzyBase64DoubleArrays;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class EzyBase64DoubleArraysTest extends BaseTest {

    @Override
    public Class<?> getTestClass() {
        return EzyBase64DoubleArrays.class;
    }

    @Test
    public void test() {
        String str = EzyBase64DoubleArrays.encode(new double[]{1, 2, 3});
        double[] array = EzyBase64DoubleArrays.decode(str);
        assertEquals(array, new double[]{1, 2, 3});
    }
}
