package com.tvd12.ezyfox.testing.security;

import com.tvd12.ezyfox.security.EzyKeysGenerator;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.security.KeyPair;

public class EzyKeysGeneratorTest extends BaseTest {

    public static void main(String[] args) {
        KeyPair keyPair = EzyKeysGenerator
            .builder()
            .build()
            .generate();
        System.out.println(keyPair.getPublic());
    }

    @Test(expectedExceptions = {IllegalArgumentException.class})
    public void test() {
        EzyKeysGenerator.builder()
            .algorithm("fasdfasdf")
            .keySize(512)
            .build()
            .generate();
    }
}
