package com.tvd12.ezyfox.testing.security;

import com.tvd12.ezyfox.security.EzyAesCrypt;
import com.tvd12.test.assertion.Asserts;
import org.testng.annotations.Test;

public class EzyAesCryptTest {

    @Test
    public void test() throws Exception {
        // given
        byte[] key = EzyAesCrypt.randomKey();
        byte[] clean = "Quisque eget odio ac lectus vestibulum faucibus eget.".getBytes();

        // when
        EzyAesCrypt sut = EzyAesCrypt.getDefault();
        byte[] encrypted = sut.encrypt(clean, key);
        byte[] decrypted = sut.decrypt(encrypted, key);

        // then
        Asserts.assertEquals(clean, decrypted);
    }

    @Test
    public void testMinSize() throws Exception {
        // given
        byte[] key = EzyAesCrypt.randomKey(16);
        byte[] clean = "Quisque eget odio ac lectus vestibulum faucibus eget.".getBytes();

        // when
        EzyAesCrypt sut = EzyAesCrypt.builder()
            .initVectorSize(16)
            .keySpecAlgorithm("AES")
            .transformation("AES/CBC/PKCS5Padding")
            .build();
        byte[] encrypted = sut.encrypt(clean, key);
        byte[] decrypted = sut.decrypt(encrypted, key);

        // then
        Asserts.assertEquals(clean, decrypted);
    }
}
