package com.tvd12.ezyfox.testing.constant;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.constant.EzyPaymentType;
import com.tvd12.test.assertion.Asserts;

public class EzyPaymentTypeTest {

    @Test
    public void test() {
        Asserts.assertEquals(EzyPaymentType.ANNUALLY.getName(), "annually");
        Asserts.assertEquals(EzyPaymentType.ANNUALLY.getId(), 3);
        Asserts.assertEquals(EzyPaymentType.ofName("monthly"), EzyPaymentType.MONTHLY);
        Asserts.assertNull(EzyPaymentType.ofName("don't know"));
    }
}
