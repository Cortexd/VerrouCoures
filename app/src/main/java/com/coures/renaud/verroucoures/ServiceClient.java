package com.coures.renaud.verroucoures;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceClient extends AsyncTask<ParamRelays, Void, Boolean> {




        private static int ReadTimeout = 10000;
        private static int ConnectTimeout = 10000;
        private static String urlString = "http://192.168.1.3:18099/";
        private static String namespace = "http://192.168.1.3:18099/";
        private static String soapAction = namespace +"/";

      //  private Context context;

//    public ServiceClient(Context context)
//        {
//            this.context = context;
//        }
//
//        @Override
//        protected void onPreExecute()
//        {
//            try {
//
//            }
//            catch (Exception ignored)
//            {}
//        }

//************************************************************************
//*** Incoming SOAP ******************************************************
//<?xml version="1.0" encoding="UTF-8"?>
//<SOAP-ENV:Envelope
//    SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"
//    xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
//    xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance"
//    xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
//    xmlns:xsd="http://www.w3.org/1999/XMLSchema"
//            >
//<SOAP-ENV:Body>
//<actionRelais SOAP-ENC:root="1">
//<v1 xsi:type="xsd:string">IMP</v1>
//<v2 xsi:type="xsd:int">1</v2>
//</actionRelais>
//</SOAP-ENV:Body>
//</SOAP-ENV:Envelope>
//************************************************************************

        @Override
        protected Boolean doInBackground(ParamRelays... paramRelays)
        {
            try {


               String v1_action = paramRelays[0].action;
               String v2_relais = Integer.toString(paramRelays[0].relays);


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
                sb.append("<v1 xsi:type=\"xsd:string\">" + v1_action + "</v1>\n");
                sb.append("<v2 xsi:type=\"xsd:int\">"+ v2_relais +"</v2>\n");
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


}
