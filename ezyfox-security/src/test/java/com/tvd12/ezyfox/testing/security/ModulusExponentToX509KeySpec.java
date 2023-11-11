package com.tvd12.ezyfox.testing.security;

import com.tvd12.ezyfox.security.EzyBase64;

import java.security.spec.RSAPublicKeySpec;
import java.security.interfaces.RSAPublicKey;
import java.security.KeyFactory;
import java.math.BigInteger;
import java.util.Base64;

public class ModulusExponentToX509KeySpec {

    public static void main(String[] args) throws Exception {
        // Replace these with your actual modulus and exponent values
        String modulusBase64 = "pGxIROVFa/fHqhMyrX4L53BCb72XTIK2+Hw2htdqF5wigSbDuHPDFetmQCdGUcTE2pCO/a6c3VrBU3vt45bXN4wqpZDM60XCsPOFZ1L3PIKmFnmuRwFdz+QIaoEo8sgkicPSG3ZHiHBr7t7+aIEnSpHr4jGInVUHSHnLt8m9MUr743XtgZynarfugUKz8utnRyhnoW/4Q8w+XivijDWf3VWWFpjfc78NxWJw+I3h7bdAO61N4O2JYXQMFgh26nPTAxrdk7r7spHJCBi6QHKLnRm8EXHC2pH4xFvKdxJamDC0evL/5Samy7vjIN8jibFtsWe/tKe/blbVF7cas4r0Iw==";
        String exponentBase64 = "AQAB";

        // Decode base64 strings to get byte arrays
        byte[] modulusBytes = Base64.getDecoder().decode(modulusBase64);
        byte[] exponentBytes = Base64.getDecoder().decode(exponentBase64);

        // Convert byte arrays to BigInteger
        BigInteger modulus = new BigInteger(1, modulusBytes);
        BigInteger exponent = new BigInteger(1, exponentBytes);

        // Create RSAPublicKeySpec
        RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(modulus, exponent);

        // Create an RSAPublicKey from RSAPublicKeySpec
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(rsaPublicKeySpec);
        publicKey.getEncoded();
        // Now you have an RSAPublicKey that can be used for various cryptographic operations
        System.out.println("RSAPublicKey: " + publicKey);
        System.out.println("RSAPublicKey: " + EzyBase64.encode2utf(publicKey.getEncoded()));
    }
}

