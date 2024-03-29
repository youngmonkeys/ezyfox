/*
 * This file was automatically generated by EvoSuite
 * Wed May 03 09:49:34 ICT 2017
 */

package com.tvd12.ezyfox.testing.helper;

import com.tvd12.ezyfox.helper.EzyLazyInitHelper;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class EzyLazyInitHelper_ESTest {

    @Test
    public void test3() throws Throwable {
        int integer0 = 1203;
        // Undeclared exception!
        try {
            EzyLazyInitHelper.init(integer0, null);
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test4() throws Throwable {
        // Undeclared exception!
        try {
            EzyLazyInitHelper.init(null, null);
            fail("Expecting exception: NullPointerException");

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}
