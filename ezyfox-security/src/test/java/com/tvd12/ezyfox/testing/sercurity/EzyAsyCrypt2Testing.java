package com.tvd12.ezyfox.testing.sercurity;

import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.ezyfox.io.EzyStrings;
import com.tvd12.ezyfox.sercurity.EzyAsyCrypt;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.ezyfox.sercurity.EzyFileKeysGenerator;
import com.tvd12.test.base.BaseTest;
import org.testng.annotations.Test;

import java.io.File;
import java.security.KeyPair;

import static org.testng.Assert.assertEquals;

public class EzyAsyCrypt2Testing extends BaseTest {

    @Test
    public void test() throws Exception {
        EzyFileKeysGenerator keysGenerator = EzyFileKeysGenerator.builder()
            .algorithm("RSA")
            .keySize(2048)
            .publicKeyFile(new File("output/publickey.txt"))
            .privateKeyFile(new File("output/privatekey.txt"))
            .fileWriter(EzySimpleFileWriter.builder().build())
            .build();
        KeyPair keyPair = keysGenerator.generate();

        EzyAsyCrypt asyCrypt = EzyAsyCrypt.builder()
            .algorithm("RSA")
            .privateKey(keyPair.getPrivate().getEncoded())
            .publicKey(keyPair.getPublic().getEncoded())
            .build();

        String text = "i'm dzung";
        byte[] encrypt = asyCrypt.encrypt(text);
        assertEquals(asyCrypt.decrypt(encrypt), EzyStrings.getUtfBytes("i'm dzung"));

        String encrypt2 = asyCrypt.encrypt(text, String.class);
        assertEquals(asyCrypt.decrypt(encrypt2, String.class),
            EzyBase64.encodeUtf("i'm dzung"));
        assertEquals(asyCrypt.decrypt(encrypt2), EzyStrings.getUtfBytes(text));
    }

    @Test(expectedExceptions = {IllegalStateException.class})
    public void test2() {

        EzyFileKeysGenerator keysGenerator = EzyFileKeysGenerator.builder()
            .algorithm("RSA")
            .keySize(512)
            .publicKeyFile(new File("output/publickey.txt"))
            .privateKeyFile(new File("output/privatekey.txt"))
            .fileWriter(EzySimpleFileWriter.builder().build())
            .build();

        EzyFileKeysGenerator.builder()
            .algorithm("RSA")
            .keySize(512)
            .publicKeyFile(new File("output/publickey.txt"))
            .privateKeyFile(new File("output/privatekey.txt"))
            .fileWriter(EzySimpleFileWriter.builder().build())
            .build();

        KeyPair keyPair = keysGenerator.generate();

        EzyAsyCrypt.builder()
            .algorithm("fafafsdfsa")
            .privateKey(keyPair.getPrivate().getEncoded())
            .publicKey(keyPair.getPublic().getEncoded())
            .build();
    }
}
