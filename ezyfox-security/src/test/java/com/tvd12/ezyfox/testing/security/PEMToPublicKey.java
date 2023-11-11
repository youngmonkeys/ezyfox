package com.tvd12.ezyfox.testing.security;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;

public class PEMToPublicKey {
    public static void main(String[] args) {
        try {
            // Replace this with your PEM-encoded public key
            String base64PublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApGxIROVFa/fHqhMyrX4L53BCb72XTIK2+Hw2htdqF5wigSbDuHPDFetmQCdGUcTE2pCO/a6c3VrBU3vt45bXN4wqpZDM60XCsPOFZ1L3PIKmFnmuRwFdz+QIaoEo8sgkicPSG3ZHiHBr7t7+aIEnSpHr4jGInVUHSHnLt8m9MUr743XtgZynarfugUKz8utnRyhnoW/4Q8w+XivijDWf3VWWFpjfc78NxWJw+I3h7bdAO61N4O2JYXQMFgh26nPTAxrdk7r7spHJCBi6QHKLnRm8EXHC2pH4xFvKdxJamDC0evL/5Samy7vjIN8jibFtsWe/tKe/blbVF7cas4r0IwIDAQAB";

            // Decode the base64 string into a byte array
            byte[] keyBytes = Base64.getDecoder().decode(base64PublicKey);
            System.out.println(keyBytes.length);
            System.out.println(Arrays.toString(keyBytes));

            // Create a KeyFactory and a public key specification
            KeyFactory keyFactory = KeyFactory.getInstance("RSA"); // or your desired algorithm
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

            // Generate the PublicKey object
            PublicKey publicKey = keyFactory.generatePublic(keySpec);

            // Now you have the PublicKey object
            System.out.println("Public Key: " + publicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

