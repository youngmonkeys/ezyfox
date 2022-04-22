package com.tvd12.ezyfox.testing.sercurity;

import static org.testng.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
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

public class EzyFileAsyCrypt4Testing extends BaseTest {

    private EzyFileReader fileReader = EzySimpleFileReader.builder().build();
    private EzyFileWriter fileWriter = EzySimpleFileWriter.builder().build();

    private File fileInput 
            = new File("output/EzyFileAsyCrypt3Testing_fileInput.txt");
    
    private File outEncryptedFile 
            = new File("output/EzyFileAsyCrypt3Testing_outEncryptedFile.txt");
    private File outDecryptedFile 
            = new File("output/EzyFileAsyCrypt3Testing_outDecryptedFile.txt");
    
    public EzyFileAsyCrypt4Testing() throws IOException {
        super();
        fileInput.getParentFile().mkdirs();
        fileWriter.write(fileInput, new String("dungtv").getBytes("UTF-8"));
    }
    
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
                .outDecryptedFile(outDecryptedFile)
                .outEncryptedFile(outEncryptedFile)
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
        
        byte[] encrypt = asyCrypt.encrypt(fileInput);
        assertEquals(encrypt, fileReader.readBytes(outEncryptedFile));
        System.err.println("before: " + Arrays.toString(fileReader.readBytes(outDecryptedFile)));
        byte[] decrypt = asyCrypt.decrypt(outEncryptedFile, byte[].class);
        System.err.println("decrypt: " + Arrays.toString(decrypt));
        System.err.println("after: " + Arrays.toString(fileReader.readBytes(outDecryptedFile)));
        assertEquals(decrypt, fileReader.readBytes(outDecryptedFile));
        String string = asyCrypt.decrypt(outEncryptedFile, String.class);
        assert string.equals(EzyBase64.encodeUtf("dungtv"));
    }
}
