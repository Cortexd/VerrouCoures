package com.coures.renaud.verroucoures;
import android.util.Base64;

import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


 public class Encryption {



        private static int KEY_SIZE = 16;
        private static String ENCRYPTION_KEY = "9061979aaronaxel";


        /**
         * Encrypt using AES 128-bit encryption with CBC mode
         *
         * @param plaintext (byte[]) The plain text
         *
         * @return (String) Encrypted text
         */
        public String encrypt(byte[] plaintext) {
            try {

                String passphrase = (ENCRYPTION_KEY.length() > KEY_SIZE) ? ENCRYPTION_KEY.substring(0, KEY_SIZE) : ENCRYPTION_KEY;
                byte[] key = passphrase.getBytes("UTF-8");
                byte[] iv = generateInitializationVector(KEY_SIZE);

                SecretKeySpec secretKeySpec;
                secretKeySpec = new SecretKeySpec(key, "AES");



                // PKCS#5 Padding
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                AlgorithmParameters algorithmParams = AlgorithmParameters.getInstance("AES");
                algorithmParams.init(new IvParameterSpec(iv));
                cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, algorithmParams);
                byte[] encryptedBytes = cipher.doFinal(plaintext);
                return encodeBase64URLSafeString(encryptedBytes);
            } catch (NoSuchPaddingException | BadPaddingException e) {
                System.out.println("Padding exception in encrypt(): " + e);
            } catch ( NoSuchAlgorithmException | InvalidKeyException	| IllegalBlockSizeException e ) {
                System.out.println("Validation exception in encrypt(): " + e);
            } catch (Exception e) {
                System.out.println("Exception in encrypt(): " + e);
            }
            return null;
        }


    private static String encodeBase64URLSafeString(byte[] binaryData) {

        return android.util.Base64.encodeToString(binaryData, Base64.URL_SAFE);

    }
        /**
         * Utility function to generate initialization vector
         *
         * @return bytes
         */
        private static byte[] generateInitializationVector(int len) {
            try {
                char[] CHAR_ARRAY = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456879".toCharArray();

                return "9061979aaronaxel".substring(0, KEY_SIZE).getBytes("UTF-8");
            } catch (Exception e) {
                System.out.println("Error generating Initialization Vector: " + e);
            }
            return null;
        }
    }

