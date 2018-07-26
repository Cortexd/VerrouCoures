package com.coures.renaud.verroucoures;
import android.util.Base64;

import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


 public class Encryption {



        private static int KEY_SIZE = 16;
        private static String ENCRYPTION_KEY = "9061979AaronAxel";


       public String simpleMD5Encrypt(String message) throws NoSuchAlgorithmException {

            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] crypted =  digest.digest((message + ENCRYPTION_KEY).getBytes());

            // retour en hexa string
           StringBuilder sb = new StringBuilder();
           for (byte b : crypted) {
               sb.append(String.format("%02X", b));
           }
          return sb.toString();

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

