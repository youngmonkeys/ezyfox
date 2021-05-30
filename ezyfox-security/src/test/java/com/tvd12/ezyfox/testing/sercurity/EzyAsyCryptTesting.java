package com.tvd12.ezyfox.testing.sercurity;

import java.io.File;
import java.security.KeyPair;

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
				.keysize(512)
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
	
}
