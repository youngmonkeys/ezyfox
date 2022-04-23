package com.tvd12.ezyfox.testing.sercurity;

import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.ezyfox.sercurity.EzyAsyCrypt;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.ezyfox.sercurity.EzyFileKeysGenerator;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.security.KeyPair;

public class EzyAsyCrypt3Testing extends BaseTest {

    @Test
    public void test() throws Exception {
        EzyFileKeysGenerator keysGenerator = EzyFileKeysGenerator.builder()
            .algorithm("RSA")
            .keySize(512)
            .fileWriter(EzySimpleFileWriter.builder().build())
            .build();
        KeyPair keyPair = keysGenerator.generate();

        EzyAsyCrypt asyCrypt = EzyAsyCrypt.builder()
            .algorithm("RSA")
            .privateKey(keyPair.getPrivate().getEncoded())
            .publicKey(keyPair.getPublic().getEncoded())
            .build();

        String text = "i'm dzung";
        String encryptedText = asyCrypt.encrypt(text, String.class);

        System.out.println("encryptedText = " + encryptedText);

        String decryptedText = asyCrypt.decrypt(EzyBase64.decode(encryptedText), String.class);

        System.out.println("decryptedText = " + decryptedText);
    }
}
