package xyz.girudo.jetset.controllers;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class JetsetEncoder {

    public static String encrypt(String string, String key) {
        byte[] aesData;
        String base64 = "";

        try {
            aesData = encryptByte(key, string.getBytes("UTF8"));
            base64 = Base64.encodeToString(aesData, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return base64;
    }

    public static String decrypt(String string, String key) {
        byte[] debase64;
        String result = "";

        try {
            debase64 = Base64.decode(string, Base64.DEFAULT);
            byte[] aesDecrypted = decryptByte(key, debase64);
            if (aesDecrypted != null) result = new String(aesDecrypted, "UTF8");
        } catch (Exception e) {
        }

        return result;
    }

    private static byte[] decryptByte(String k, byte[] plainBytes) throws Exception {
        // convert key to bytes
        byte[] keyBytes = k.getBytes("UTF-8");
        // Use the first 16 bytes (or even less if key is shorter)
        byte[] keyBytes16 = new byte[16];
        System.arraycopy(keyBytes, 0, keyBytes16, 0, Math.min(keyBytes.length, 16));
        // setup cipher
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes16, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[16]; // initialization vector with all 0
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, new IvParameterSpec(iv));
        // encrypt
        byte[] encrypted = cipher.doFinal(plainBytes);
        return encrypted;
    }

    private static byte[] encryptByte(String k, byte[] plainBytes) throws Exception {
        // convert key to bytes
        byte[] keyBytes = k.getBytes("UTF-8");
        // Use the first 16 bytes (or even less if key is shorter)
        byte[] keyBytes16 = new byte[16];
        System.arraycopy(keyBytes, 0, keyBytes16, 0, Math.min(keyBytes.length, 16));
        // setup cipher
        SecretKeySpec skeySpec = new SecretKeySpec(keyBytes16, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] iv = new byte[16]; // initialization vector with all 0
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(iv));
        // encrypt
        byte[] encrypted = cipher.doFinal(plainBytes);
        return encrypted;
    }
}