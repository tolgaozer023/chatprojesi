/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chats;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.crypto.Cipher;

/**
 *
 * @author tlg
 */
public class RSA_Encryption {

    static String plainText = "şifrelenen metin";

    public static void main(String[] args) throws Exception {
        // Get an instance of the RSA key generator
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);

        // Generate the KeyPair
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Get the public and private key
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        String publicK = Base64.getEncoder().encodeToString(publicKey.getEncoded());

        String privateK = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        System.out.println("publicKey " + publicK);
        System.out.println("privateKey " + privateK);

        System.out.println("Original Text  : " + plainText);
        
        String encryptedText = encrypt(plainText, publicK);
        System.out.println("Encrypted Text : " + encryptedText);
        
        
        String decryptedText = decrypt(encryptedText, privateK);
        System.out.println("DeCrypted Text : " + decryptedText);
        
  
        // Encryption
        /*byte[] cipherTextArray = encrypt(plainText, publicKey);
        String encryptedText = Base64.getEncoder().encodeToString(cipherTextArray);
        System.out.println("Encrypted Text : " + encryptedText);

        // Decryption
        String decryptedText = decrypt(cipherTextArray, privateKey);
        System.out.println("DeCrypted Text : " + decryptedText);*/

    }

    public static String encrypt(String plainText, String publicKey) throws Exception {

        byte[] publicBytes = Base64.getDecoder().decode(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pubKey = keyFactory.generatePublic(keySpec);
        
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");

        //Initialize Cipher for ENCRYPT_MODE
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        //Perform Encryption
        byte[] cipherText = cipher.doFinal(plainText.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(cipherText);
        return encryptedText;
    }

    public static String decrypt(String cipherTextArray, String privateKey) throws Exception {
        
        byte[] privateBytes = Base64.getDecoder().decode(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privKey = keyFactory.generatePrivate(keySpec);
        
        
        //Get Cipher Instance RSA With ECB Mode and OAEPWITHSHA-512ANDMGF1PADDING Padding
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-512ANDMGF1PADDING");

        //Initialize Cipher for DECRYPT_MODE
        cipher.init(Cipher.DECRYPT_MODE, privKey);

        byte[] byteArrray = cipherTextArray.getBytes();
        
        //Perform Decryption
        byte[] decryptedTextArray = cipher.doFinal(byteArrray);

        return new String(decryptedTextArray);
    }

    public String[] getkeys() {

        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");

            keyPairGenerator.initialize(2048);

            // Generate the KeyPair
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Get the public and private key
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();
            String publicK = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            String privateK = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            //System.out.println("publicKey " + publicK);
            //System.out.println("privateKey " + privateK);
            String[] keys = new String[2];

            keys[0] = publicK;
            keys[1] = privateK;

            return keys;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(RSA_Encryption.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}
