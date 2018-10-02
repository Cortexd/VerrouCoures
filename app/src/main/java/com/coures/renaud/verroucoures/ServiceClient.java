package com.coures.renaud.verroucoures;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ServiceClient extends AsyncTask<ParamRelays, Void, Boolean> {




        private static int ReadTimeout = 10000;
        private static int ConnectTimeout = 10000;
        private static String urlString = "http://192.168.1.3:18099/";
        private static String namespace = "http://192.168.1.3:18099/";
        private static String soapAction = namespace +"/";

        @Override
        protected Boolean doInBackground(ParamRelays... paramRelays)
        {
            try {

               String v1_cleSecurite = this.getCleSecurite();
               String v2_action = paramRelays[0].action;
               String v3_relais = Integer.toString(paramRelays[0].relays);

//               <?xml version="1.0" encoding="UTF-8"?>
//<SOAP-ENV:Envelope
//                SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"
//                xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
//                xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance"
//                xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
//                xmlns:xsd="http://www.w3.org/1999/XMLSchema"
//                        >
//<SOAP-ENV:Body>
//<actionRelais SOAP-ENC:root="1">
//<v1 xsi:type="xsd:string">0F9EA69C0C838CA62880910BAC2E52598EB0FDC44E6158C58780651ED9399DB5900FB166122E32CFBA65234964EB169112D3E74B2931EA49CC09D50DBB9C6400</v1>
//<v2 xsi:type="xsd:string">IMP</v2>
//<v3 xsi:type="xsd:int">8</v3>
//</actionRelais>
//</SOAP-ENV:Body>
//</SOAP-ENV:Envelope>


                        // Create soap message
                StringBuilder sb = new StringBuilder();
                sb.append("<SOAP-ENV:Envelope\n");
                sb.append("    SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\"\n");
                sb.append("    xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\"\n");
                sb.append("    xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\"\n");
                sb.append("    xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\"\n");
                sb.append("    xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"\n");
                sb.append("            >\n");
                sb.append("<SOAP-ENV:Body>\n");
                sb.append("<actionRelais SOAP-ENC:root=\"1\">\n");
                sb.append("<v1 xsi:type=\"xsd:string\">" + v1_cleSecurite + "</v1>\n");
                sb.append("<v2 xsi:type=\"xsd:string\">" + v2_action + "</v2>\n");
                sb.append("<v3 xsi:type=\"xsd:int\">"+ v3_relais +"</v3>\n");
                sb.append("</actionRelais>\n");
                sb.append("</SOAP-ENV:Body>\n");
                sb.append("</SOAP-ENV:Envelope>\n");

                String content = sb.toString();

                // create post for soap message
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // this needs to be HttpsURLConnection if you are using ssl
                connection.setReadTimeout(ReadTimeout);
                connection.setConnectTimeout(ConnectTimeout);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
                connection.setRequestProperty("Content-Length", String.valueOf(content.length()));
                //connection.setRequestProperty("Accept-Encoding", "gzip"); // comment this line out if you dont want to compress all service calls
                //connection.setRequestProperty("SOAPAction", soapAction + Params[0]);
                connection.setRequestProperty("SOAPAction", soapAction);

                // Get soap response
                OutputStream os = connection.getOutputStream();
                os.write(content.getBytes("UTF-8"));
                os.flush();
                os.close();
                String response = "";
                int responseCode = connection.getResponseCode();
                BufferedReader br;
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    String line;
                    br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while ((line = br.readLine()) != null) {
                        response += line;
                    }
                } else {
                    throw new Exception("HTTP ERROR: " + responseCode);
                }

                // Extract data
//                int index = response.indexOf(Params[0] + "Result>") + 7+Params[0].length();
//                response = response.substring(index, response.indexOf("</"+Params[0], index));

                // release all resources
                br.close();
                connection.disconnect();

                return true;
            }
            catch (Exception ex)
            {
                return false;
            }
        }


        // Calcul clé secu
        protected String getCleSecurite()
        {
          String  ENCRYPTION_KEY = "09061979AaronAxel";
          // cleartext = "Y2018-M09-D26 H10:M10" + ENCRYPTION_KEY

          String  plaintext = getCurrentTimeStamp() + ENCRYPTION_KEY;
          String encrypted = new Encryption().get_SHA_512_SecurePassword(plaintext, "");

          return encrypted.toUpperCase();

        }

        //"Y2018-M09-D26 H10:M10"
    public String getCurrentTimeStamp() {

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

    public String leadingZeros(String s, int length) {
        if (s.length() >= length) return s;
        else return String.format("%0" + (length-s.length()) + "d%s", 0, s);
    }

}
