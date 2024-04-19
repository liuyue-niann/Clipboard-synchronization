package org.sync.clipboard.test.rsa;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class Test01 {
    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
         KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
         keyPairGenerator.initialize(1024); // 设置密钥大小
         KeyPair keyPair = keyPairGenerator.generateKeyPair();
         PublicKey publicKey = keyPair.getPublic();
         PrivateKey privateKey = keyPair.getPrivate();
         byte[] encrypt = RSAEncryption.encrypt("Hello".getBytes(StandardCharsets.UTF_8), publicKey);
         byte[] decrypt = RSAEncryption.decrypt(encrypt, privateKey);
         System.out.println(new String(decrypt));
    }
}
