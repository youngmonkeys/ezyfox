package com.tvd12.ezyfox.testing.io;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.io.EzyDoubleArrays;
import com.tvd12.test.base.BaseTest;

public class EzyDoubleArraysTest extends BaseTest {

    @Test
    public void test() {
        byte[] byteArray = EzyDoubleArrays.toByteArray(new double[] {1});
        System.out.println(Arrays.toString(byteArray));
        double[] doubleArray = EzyDoubleArrays.toDoubleArray(byteArray);
        assertEquals(doubleArray, new double[] {1});
        double[] arr2 = EzyDoubleArrays.toDoubleArray(new byte[] {1, 2, 3, 4, 5}, 2);
        System.out.println(Arrays.toString(arr2));
    }

    @Override
    public Class<?> getTestClass() {
        return EzyDoubleArrays.class;
    }
}