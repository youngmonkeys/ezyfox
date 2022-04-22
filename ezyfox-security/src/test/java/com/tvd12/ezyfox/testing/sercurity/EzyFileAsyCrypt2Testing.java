package com.tvd12.ezyfox.testing.sercurity;

import java.io.File;
import java.security.KeyPair;
import java.util.Arrays;

import org.testng.annotations.Test;

import com.tvd12.ezyfox.file.EzyFileReader;
import com.tvd12.ezyfox.file.EzyFileWriter;
import com.tvd12.ezyfox.file.EzySimpleFileReader;
import com.tvd12.ezyfox.file.EzySimpleFileWriter;
import com.tvd12.ezyfox.sercurity.EzyBase64;
import com.tvd12.ezyfox.sercurity.EzyFileAsyCrypt;
import com.tvd12.ezyfox.sercurity.EzyFileKeysGenerator;
import com.tvd12.test.base.BaseTest;
import static org.testng.Assert.*;

public class EzyFileAsyCrypt2Testing extends BaseTest {

    private EzyFileReader fileReader = EzySimpleFileReader.builder().build();
    private EzyFileWriter fileWriter = EzySimpleFileWriter.builder().build();
    
    @Test
    public void test() throws Exception {
        EzyFileKeysGenerator keysGenerator = EzyFileKeysGenerator.builder()
                .algorithm("RSA")
                .keysize(512)
                .publicKeyFile(new File("output/publickey.txt"))
                .privateKeyFile(new File("output/privatekey.txt"))
                .fileWriter(fileWriter)
                .build();
        keysGenerator.generate();
        KeyPair keyPair = keysGenerator.generate();
        
        System.err.println(Arrays.toString(keyPair.getPublic().getEncoded()));
        
        EzyFileAsyCrypt asyCrypt = EzyFileAsyCrypt.builder()
                .algorithm("RSA")
                .privateKeyFile(new File("output/privatekey.txt"))
                .publicKeyFile(new File("output/publickey.txt"))
                .fileReader(fileReader)
                .fileWriter(fileWriter)
                .build();
        
        assertEquals(keyPair.getPublic().getEncoded(), 
                fileReader.readBytes(new File("output/publickey.txt")));
        assertEquals(keyPair.getPrivate().getEncoded(), 
                fileReader.readBytes(new File("output/privatekey.txt")));
        
        String text = "i'm dzung";
        String encryptedText = asyCrypt.encrypt(text, String.class);
        
        System.out.println("encryptedText = " + encryptedText);
        
        String decryptedText = asyCrypt.decrypt(EzyBase64.decode(encryptedText), String.class);
        
        System.out.println("decryptedText = " + decryptedText);
        
    }
}
