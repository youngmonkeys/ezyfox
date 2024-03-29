package com.tvd12.ezyfox.testing;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;

public class GenerateKeysTest {

    private final KeyPairGenerator keyGen;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public GenerateKeysTest(int keylength)
        throws NoSuchAlgorithmException, NoSuchProviderException {
        this.keyGen = KeyPairGenerator.getInstance("RSA");
        this.keyGen.initialize(keylength);
    }

    public static void main(String[] args) {
        GenerateKeysTest gk;
        try {
            gk = new GenerateKeysTest(1024);
            gk.createKeys();
            gk.writeToFile("KeyPair/publicKey", gk.getPublicKey().getEncoded());
            gk.writeToFile("KeyPair/privateKey", gk.getPrivateKey().getEncoded());
        } catch (NoSuchAlgorithmException | NoSuchProviderException | IOException e) {
            System.err.println(e.getMessage());
        }

    }

    public void createKeys() {
        KeyPair pair = this.keyGen.generateKeyPair();
        this.privateKey = pair.getPrivate();
        this.publicKey = pair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void writeToFile(String path, byte[] key) throws IOException {
        File f = new File(path);
        f.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream(f);
        fos.write(key);
        fos.flush();
        fos.close();
    }

}
