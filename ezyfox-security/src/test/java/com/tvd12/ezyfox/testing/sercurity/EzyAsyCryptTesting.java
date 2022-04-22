package com.tvd12.ezyfox.testing.sercurity;

import java.io.File;
import java.security.KeyPair;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.ezyfox.sercurity.EzyAesCrypt;
import com.tvd12.ezyfox.sercurity.EzyAsyCrypt;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.ezyfox.sercurity.EzyFileKeysGenerator;
import com.tvd12.test.assertion.Asserts;
import com.tvd12.test.base.BaseTest;

public class EzyAsyCryptTesting extends BaseTest {

    @Test
    public void test() throws Exception {
        EzyFileKeysGenerator keysGenerator = EzyFileKeysGenerator.builder()
                .algorithm("RSA")
                .keysize(2048)
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

        System.out.println("public: " + new String(keyPair.getPublic().getEncoded()));
        System.out.println("public: " + EzyBase64.encode2utf(keyPair.getPublic().getEncoded()));
        System.out.println("private: " + EzyBase64.encode2utf(keyPair.getPrivate().getEncoded()));

        String text = "i'm dzung";
        String encryptedText = asyCrypt.encrypt(text, String.class);

        System.out.println("encryptedText = " + encryptedText);

        String decryptedText = asyCrypt.decrypt(EzyBase64.decode(encryptedText), String.class);

        System.out.println("decryptedText = " + decryptedText);
    }

    @Test
    public void clearTest() throws Exception {
        EzyFileKeysGenerator keysGenerator = EzyFileKeysGenerator.builder()
                .algorithm("RSA")
                .keysize(2048)
                .publicKeyFile(new File("output/publickey.txt"))
                .privateKeyFile(new File("output/privatekey.txt"))
                .fileWriter(EzySimpleFileWriter.builder().build())
                .build();
        KeyPair keyPair = keysGenerator.generate();

        System.out.println("public 2048: " + EzyBase64.encode2utf(keyPair.getPublic().getEncoded()));
        System.out.println("private 2048: " + EzyBase64.encode2utf(keyPair.getPrivate().getEncoded()));


        EzyAsyCrypt asyCrypt = EzyAsyCrypt.builder()
                .algorithm("RSA")
                .publicKey(keyPair.getPublic().getEncoded())
                .build();

        byte[] text = EzyAesCrypt.randomKey();
        byte[] encryptedText = asyCrypt.encrypt(text);

        System.out.println("text = " + text.length);
        System.out.println("encryptedText = " + encryptedText.length);

        asyCrypt = EzyAsyCrypt.builder()
                .privateKey(keyPair.getPrivate().getEncoded())
                .build();
        byte[] decryptedText = asyCrypt.decrypt(encryptedText);

        byte[] data = "Hello".getBytes();

        byte[] cdata = EzyAesCrypt.builder()
                .build()
                .encrypt(data, text);
        byte[] ddata = EzyAesCrypt.builder()
                .build()
                .decrypt(cdata, text);
        Asserts.assertEquals(data, ddata);

        System.out.println("decryptedText = " + new String(decryptedText));
    }

    @Test
    public void encryptTest() throws Exception {
        String publicKeyBase64 = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1Gokfgb1T5/1/NC4VP7E"
                + "t9XP1Ye4ScAMi8wWWEZPExG4qdJYebDmYVtIaa4Ydh9LgXzAfkkzcXxmXm/O/An7"
                + "szxXTirw/DfL+JEaymkaMZyB+fSVHhG+/pCG3qBd7/L8Z8k8KM/5VpJqR76W4sbd"
                + "nHop0/FLtnPmgQQzcZ4nfi6tKab/tKn8mLEf3WTxkmi/aa+u5kkaTHhVPq8QILUU"
                + "XhLEY1EnqXD9bRmxCbXmpOkk6WVAcpECBVQdBP41xmj5iOKWLf0Y5fE7fEkWPs4w"
                + "yCZ8Muia0kbOyxOu+uPSC73W8ZPWf8tOmcrPPXp3bMV1l/dN5wNn4g3ReiZirXNW"
                + "WwIDAQAB";

        System.out.println("key length: " + EzyBase64.decode(publicKeyBase64).length);

        EzyAsyCrypt asyCrypt = EzyAsyCrypt.builder()
                .algorithm("RSA")
                .transformation("RSA")
                .publicKey(EzyBase64.decode(publicKeyBase64))
                .build();


        byte[] text = "abcd1234".getBytes();//EzyAesCrypt.randomKey();
        long start = System.currentTimeMillis();
        byte[] encryptedText = asyCrypt.encrypt(text);
        long elapsedTime = System.currentTimeMillis() - start;
        System.out.println("elapsed time: " + elapsedTime);
        System.out.println("normal text: " + EzyBase64.encode2utf(text));
        System.out.println(Arrays.toString(encryptedText));
        System.out.println("encrypted base64 text: " + EzyBase64.encode2utf(encryptedText));
        System.out.println("normal length: " + encryptedText.length + ", encryption length = " + EzyBase64.encode2utf(encryptedText).length());
    }

}
