package com.harshalbenake.apiencryptionaes;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Encrypt and decrypt messages using AES 256 bit encryption that are compatible with AESCrypt-ObjC and AESCrypt Ruby.
 */
public final class EncryptionUtility {
    //AESCrypt-ObjC uses CBC and PKCS7Padding
    private static final String AES_MODE = "AES/CBC/ISO10126Padding";
    private static final String CHARSET = "UTF-8";

    /**
     * Encrypt and encode message using 256-bit AES with key generated from password.
     *
     * @param key     : secrete key
     * @param message the thing you want to encrypt assumed String UTF-8
     * @return Base64 encoded CipherText
     * @throws GeneralSecurityException if problems occur during encryption
     */
    public static String encrypt(final String key, String message, String IV)
            throws GeneralSecurityException {
        try {
            byte[] ivBytes = IV.getBytes();
            byte[] keyBytes = key.getBytes("UTF-8");

            final SecretKeySpec secreteKey = new SecretKeySpec(keyBytes, "AES");
            byte[] cipherText = encrypt(secreteKey, ivBytes, message.getBytes(CHARSET));
            String encoded = Base64.encodeToString(cipherText, Base64.NO_WRAP);

            return encoded;
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        }
    }


    /**
     * More flexible AES encrypt that doesn't encode
     *
     * @param key     AES key typically 128, 192 or 256 bit
     * @param iv      Initiation Vector
     * @param message in bytes (assumed it's already been decoded)
     * @return Encrypted cipher text (not encoded)
     * @throws GeneralSecurityException if something goes wrong during encryption
     */
    public static byte[] encrypt(final SecretKeySpec key, final byte[] iv, final byte[] message)
            throws GeneralSecurityException {
        byte[] cipherText = new byte[0];
        try {
            final Cipher cipher = Cipher.getInstance(AES_MODE);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            cipherText = cipher.doFinal(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }

    /**
     * Decrypt and decode ciphertext using 256-bit AES with key generated from password
     *
     * @param key
     * @param base64EncodedCipherText the encrpyted message encoded with base64
     * @return message in Plain text (String UTF-8)
     * @throws GeneralSecurityException if there's an issue decrypting
     */
    public static String decrypt(final String key, String base64EncodedCipherText, String IV)
            throws GeneralSecurityException {

        try {
            byte[] ivBytes = IV.getBytes();
            byte[] keyBytes = key.getBytes("UTF-8");

            final SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "AES");
            byte[] decodedCipherText = Base64.decode(base64EncodedCipherText, Base64.NO_WRAP);
            byte[] decryptedBytes = decrypt(secretKey, ivBytes, decodedCipherText);
            String message = new String(decryptedBytes, CHARSET);

            return message;
        } catch (UnsupportedEncodingException e) {
            throw new GeneralSecurityException(e);
        } catch (Exception e) {
            System.out.println(" Sys : " + e);
            throw new RuntimeException(e);
        }
    }


    /**
     * More flexible AES decrypt that doesn't encode
     *
     * @param key               AES key typically 128, 192 or 256 bit
     * @param iv                Initiation Vector
     * @param decodedCipherText in bytes (assumed it's already been decoded)
     * @return Decrypted message cipher text (not encoded)
     * @throws GeneralSecurityException if something goes wrong during encryption
     */
    public static byte[] decrypt(final SecretKeySpec key, final byte[] iv, final byte[] decodedCipherText)
            throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(AES_MODE);
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(decodedCipherText);
        return decryptedBytes;
    }
}


