package com.example.czp.utils.helper.security;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecurityHelper {

    private final static String KEY = "czp123456";
    private final static String ALGORITHM = "AES";
    private final static String AESCBC = "AES/CBC/PKCS5Padding";

    public String encryptAES(String text){
        try {
            byte[] saltBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
            byte[] keySHA256 = toSHA256(KEY.getBytes("UTF-8"));
            Rfc2898DeriveBytes keyGenerator = new Rfc2898DeriveBytes(keySHA256, saltBytes, 1000);
            byte[] key = keyGenerator.getBytes(32);
            byte[] iv = keyGenerator.getBytes(16);
            SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
            AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);

            byte[] baText = text.getBytes("UTF-8");
            byte[] baSalt = getRandomBytes();
            byte[] baEncrypted = new byte[baText.length + baSalt.length];
            for (int i = 0; i < baSalt.length; i++) {
                baEncrypted[i] = baSalt[i];
            }
            for (int i = 0; i < baText.length; i++) {
                baEncrypted[i + baSalt.length] = baText[i];
            }
            Cipher cipher = Cipher.getInstance(AESCBC);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return Base64.encodeToString(cipher.doFinal(baEncrypted), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private int getSaltLength() {
        return 8;
    }

    private byte[] getRandomBytes() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.generateSeed(getSaltLength());
    }



    public String getMd5String(String sIn) {
        try {
            return toMd5(sIn.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    private String toMd5(byte[] bytes) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(bytes);

            return toHexString(algorithm.digest(), "");
        } catch (NoSuchAlgorithmException e) {
            Log.v("toMd5", "toMd5(): " + e);
            throw new RuntimeException(e);
        }
    }

    private byte[] toSHA256(byte[] bytes) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
            algorithm.reset();
            algorithm.update(bytes);
            return algorithm.digest();
        } catch (NoSuchAlgorithmException e) {
            Log.v("toSHA256", "toSHA256(): " + e);
            throw new RuntimeException(e);
        }
    }

    public String toHexString(String s) {
        try {
            return toHexString(s.getBytes("UTF-8"), "");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        String hs;
        for (byte b : bytes) {
            hs = Integer.toHexString(0xFF & b);
            if (hs.length() == 1) {
                hexString.append("0").append(hs);
            } else {
                hexString.append(hs);
            }
            hexString.append(separator);
        }
        return hexString.toString();
    }


    public String decryptAES(String text) {
        String decryptText = "";
        try {
            byte[] key = toSHA256(KEY.getBytes("UTF-8"));
            byte[] saltBytes = new byte[] { 1, 2, 3, 4, 5, 6, 7, 8 };
            Rfc2898DeriveBytes keyGenerator = new Rfc2898DeriveBytes(key, saltBytes, 1000);
            byte[] bKey = keyGenerator.getBytes(32);
            byte[] bIv = keyGenerator.getBytes(16);
            SecretKeySpec secretKey = new SecretKeySpec(bKey, ALGORITHM);
            AlgorithmParameterSpec iv = new IvParameterSpec(bIv);

            Cipher cipher = Cipher.getInstance(AESCBC);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
            byte[] baText = cipher.doFinal(Base64.decode(text, Base64.DEFAULT));
            int saltLength = getSaltLength();
            byte[] baResult = new byte[baText.length - saltLength];
            for (int i = 0; i < baResult.length; i++) {
                baResult[i] = baText[i + saltLength];
            }
            decryptText = new String(baResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptText;
    }

}