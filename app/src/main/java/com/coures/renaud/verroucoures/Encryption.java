package com.coures.renaud.verroucoures;
import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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

     public String get_SHA_512_SecurePassword(String passwordToHash, String   salt){
         String generatedPassword = null;
         try {
             MessageDigest md = MessageDigest.getInstance("SHA-512");
             md.update(salt.getBytes(StandardCharsets.UTF_8));
             byte[] bytes = md.digest(passwordToHash.getBytes(StandardCharsets.UTF_8));
             StringBuilder sb = new StringBuilder();
             for(int i=0; i< bytes.length ;i++){
                 sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
             }
             generatedPassword = sb.toString();
         }
         catch (NoSuchAlgorithmException e){
             e.printStackTrace();
         }
         return generatedPassword;
     }

    }

