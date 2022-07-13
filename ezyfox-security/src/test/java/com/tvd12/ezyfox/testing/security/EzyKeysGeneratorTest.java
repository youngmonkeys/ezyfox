package com.tvd12.ezyfox.testing.security;

import com.tvd12.ezyfox.security.EzyKeysGenerator;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

public class EzyKeysGeneratorTest extends BaseTest {

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test() {
        EzyKeysGenerator.builder()
            .algorithm("fasdfasdf")
            .keySize(512)
            .build()
            .generate();
    }
}
