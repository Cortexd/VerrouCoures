package com.coures.renaud.verroucoures;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CleSecure {

    //https://stackoverflow.com/questions/45406996/how-to-encrypt-string-in-java-and-decrypt-in-python

    public String getCleSecurite()
    {
        String  ENCRYPTION_KEY = "09061979AaronAxel";

        String  plaintext = getCurrentTimeStamp() + ENCRYPTION_KEY;
        String encrypted = new Encryption().get_SHA_512_SecurePassword(plaintext, "");

        return encrypted.toUpperCase();

    }

    //"Y2018-M09-D26 H10:M10"
    private String getCurrentTimeStamp() {

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        String year = Integer.toString(calendar.get(Calendar.YEAR));
        String month = Integer.toString(calendar.get(Calendar.MONTH)+1);
        String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
        String hour = Integer.toString(calendar.get(Calendar.HOUR_OF_DAY));
        String minute = Integer.toString(calendar.get(Calendar.MINUTE));

        String dateTimeFormat = "Y" + year + "-M" + leadingZeros(month,2) + "-D" + leadingZeros(day,2) + " H" + leadingZeros(hour,2) + ":M" + leadingZeros(minute,2);

        return dateTimeFormat;
    }

    private String leadingZeros(String s, int length) {
        if (s.length() >= length) return s;
        else return String.format("%0" + (length-s.length()) + "d%s", 0, s);
    }
}
