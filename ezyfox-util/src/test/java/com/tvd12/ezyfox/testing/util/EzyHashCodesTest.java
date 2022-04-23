package com.tvd12.ezyfox.testing.util;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.util.EzyHashCodes;
import com.tvd12.test.assertion.Asserts;

public class EzyHashCodesTest {

    @Test
    public void test() {
        new EzyHashCodes(1, 3);

        EzyHashCodes hashCodes = new EzyHashCodes();
        Asserts.assertEquals(1, hashCodes.toHashCode());

        hashCodes.append(1);
        Asserts.assertEquals(1 * 31 + 1, hashCodes.toHashCode());

        hashCodes.append(2);
        Asserts.assertEquals((1 * 31 + 1) * 31 + 2, hashCodes.toHashCode());

        hashCodes.append(null);
        Asserts.assertEquals(((1 * 31 + 1) * 31 + 2) * 31 + 43, hashCodes.toHashCode());
    }
}
