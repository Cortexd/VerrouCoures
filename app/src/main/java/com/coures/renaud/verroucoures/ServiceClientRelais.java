package com.coures.renaud.verroucoures;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class ServiceClientRelais extends AsyncTask<ServiceClientRelaisParam, Void, Boolean>
{
    
    // Url du web service
    private String urlString;
    private Context context;
    
    // contructeur
    public ServiceClientRelais (Context context)
    {
        
        // A la création on recupere l'url du web service WIFI ou INTERNET
        ApplicationConfig conf = ApplicationConfig.getConfig();
        this.urlString = conf.getUrlWebService();
        this.context = context;
    }
    
    @Override
    protected Boolean doInBackground (ServiceClientRelaisParam... serviceClientRelaisParamRelays)
    {
        try
        {
            String soapAction = urlString + "/";
    
            String v1_cleSecurite = new CleSecure().getCleSecurite(this.context);
            String v2_action = serviceClientRelaisParamRelays[0].action;
            String v3_relais = Integer.toString(serviceClientRelaisParamRelays[0].relays);
            
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
            sb.append("<v3 xsi:type=\"xsd:int\">" + v3_relais + "</v3>\n");
            sb.append("</actionRelais>\n");
            sb.append("</SOAP-ENV:Body>\n");
            sb.append("</SOAP-ENV:Envelope>\n");
            
            String content = sb.toString();
            
            // create post for soap message
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(); // this needs to be HttpsURLConnection if you are using ssl
            int Timeout = 10000;
            connection.setReadTimeout(Timeout);
            connection.setConnectTimeout(Timeout);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "text/xml;charset=utf-8");
            connection.setRequestProperty("Content-Length", String.valueOf(content.length()));
            connection.setRequestProperty("SOAPAction", soapAction);
            
            // Get soap response
            OutputStream os = connection.getOutputStream();
            os.write(content.getBytes(StandardCharsets.UTF_8));
            os.flush();
            os.close();
            String response = "";
            int responseCode = connection.getResponseCode();
            BufferedReader br;
            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                String line;
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = br.readLine()) != null)
                {
                    response += line;
                }
            }
            else
            {
                throw new Exception("HTTP ERROR: " + responseCode);
            }
            
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
