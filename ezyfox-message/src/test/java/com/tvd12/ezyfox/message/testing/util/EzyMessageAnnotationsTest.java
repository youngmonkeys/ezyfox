package com.tvd12.ezyfox.message.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.message.annotation.EzyMessage;
import com.tvd12.ezyfox.message.util.EzyMessageAnnotations;
import com.tvd12.test.assertion.Asserts;

public class EzyMessageAnnotationsTest {

    @Test
    public void test() {
        Asserts.assertEquals(EzyMessageAnnotations.getChannelName(A.class), "aha");
        Asserts.assertEquals(EzyMessageAnnotations.getChannelName(B.class), "bhb");
        Asserts.assertEquals(EzyMessageAnnotations.getChannelName(C.class), "C");
    }

    @EzyMessage("aha")
    public static class A {}

    @EzyMessage(channel = "bhb")
    public static class B {}

    @EzyMessage
    public static class C {}
}