package com.tvd12.ezyfox.testing.sercurity;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.sercurity.EzyKeysGenerator;
import com.tvd12.test.base.BaseTest;

public class EzyKeysGeneratorTest extends BaseTest {

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test() {
        EzyKeysGenerator.builder()
            .algorithm("fasdfasdf")
            .keysize(512)
            .build()
            .generate();
    }
}
