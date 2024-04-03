package com.example.login;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;

import java.util.Base64;

public class User {
    public String hashedPassword, salt;
    public byte[] encryptedUsername, encryptedEmail;
    public String key;
    private static final String UNICODE_FORMAT = "UTF-8";

    // Used by json mapper to convert json to an object
    public User() {
    }

    // Used to make new users in json list
    public User(String username, String password, String email) {
        try {
            key = generateKey("AES");
            Cipher cipher = Cipher.getInstance("AES");
            this.encryptedUsername = encryptString(username, key, cipher);
            this.encryptedEmail = encryptString(email, key, cipher);
        } catch (Exception e) {
            e.printStackTrace();
            this.encryptedUsername = null;
            this.encryptedEmail = null;
        }
        this.salt = BCrypt.gensalt(10);
        this.hashedPassword = hash(password);
    }

    // Used by json mapper to convert json to an object
    public User(String key, String username, String password, String email, String salt) {
        try {
            this.key = key;
            this.encryptedUsername = username.getBytes(UNICODE_FORMAT);
            this.salt = salt;
            this.hashedPassword = hash(password);
            this.encryptedEmail = email.getBytes(UNICODE_FORMAT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Simple password hashing using salt made in constructor
    private String hash(String password) {
        return BCrypt.hashpw(password, salt);
    }

    //https://www.youtube.com/watch?v=nzUealgF0hs
    private String generateKey(String encryptionType) {
            try {
                SecretKey myKey = KeyGenerator.getInstance(encryptionType).generateKey();
                return convertKeytostring(myKey);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
    }

    //encrypts with key made in constructor
    //https://www.youtube.com/watch?v=nzUealgF0hs
    private byte[] encryptString(String dataToEncrypt, String myKey, Cipher cipher) {
        try {
            SecretKey key = convertStringToKey(myKey);

            byte[] text = dataToEncrypt.getBytes(UNICODE_FORMAT);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] textEncrypted = cipher.doFinal(text);
            return textEncrypted;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //decrypts with key made in constructor
    //https://www.youtube.com/watch?v=nzUealgF0hs
    public static String decryptString(byte[] dataToDecrypt, String myKey, Cipher cipher) {
        try {
            SecretKey key = convertStringToKey(myKey);

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] textDecrypted = cipher.doFinal(dataToDecrypt);
            String result = new String(textDecrypted);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] getEncryptedUsername() {
        return encryptedUsername;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public byte[] getEncryptedEmail() {
        return encryptedEmail;
    }

    public String getSalt() {
        return salt;
    }

    public String getKey() {
        return key;
    }

    //Utility method to convert string key to its SecretKey form
    //https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa#comment78278108_5355466
    public static SecretKey convertStringToKey(String encodedKey) {
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
        return originalKey;
    }

    //Utility method to convert SecretKey to a String
    //https://stackoverflow.com/questions/5355466/converting-secret-key-into-a-string-and-vice-versa#comment78278108_5355466
    public static String convertKeytostring(SecretKey key) {
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        return encodedKey;
    }

    @Override
    public String toString() {
        return "User{" +
                "encryptedUsername='" + encryptedUsername + '\'' +
                ", hashedPassword='" + hashedPassword + '\'' +
                ", encryptedEmail='" + encryptedEmail + '\'' +
                ", salt='" + salt + '\'' +
                '}';
    }
}
