package com.tvd12.ezyfox.data.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.data.annotation.EzyCachedValue;
import com.tvd12.ezyfox.data.util.EzyCachedValueAnnotations;
import com.tvd12.test.assertion.Asserts;

public class EzyCachedValueAnnotationsTest {

    @Test
    public void test() {
        Asserts.assertEquals(EzyCachedValueAnnotations.getMapName(A.class), "aha");
        Asserts.assertEquals(EzyCachedValueAnnotations.getMapName(B.class), "bhb");
        Asserts.assertEquals(EzyCachedValueAnnotations.getMapName(C.class), "C");
    }

    @EzyCachedValue("aha")
    private static class A {}

    @EzyCachedValue(mapName = "bhb")
    private static class B {}

    @EzyCachedValue
    private static class C {}

}
