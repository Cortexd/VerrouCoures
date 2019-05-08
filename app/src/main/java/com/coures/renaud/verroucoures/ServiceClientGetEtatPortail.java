package com.coures.renaud.verroucoures;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class ServiceClientGetEtatPortail extends AsyncTask<Void, Void, String>
{
    
    
    // Url du web service
    // "http://192.168.1.3:18099/"
    private String urlString;
    
    // ***** Hold weak reference *****
    // Issue de https://stackoverflow.com/questions/12575068/how-to-get-the-result-of-onpostexecute-to-main-activity-because-asynctask-is-a
    private WeakReference<MyTaskInformer> mCallBack;
    
    // contructeur
    public ServiceClientGetEtatPortail (MyTaskInformer callback)
    {
        this.mCallBack = new WeakReference<>(callback);
        
        // A la création on recupere l'url du web service WIFI ou INTERNET
        ApplicationConfig conf = ApplicationConfig.getConfig();
        this.urlString = conf.getUrlWebService();
        
    }
    
    private static Document convertStringToDocument (String xmlStr)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlStr)));
            return doc;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    protected String getString (String tagName, Element element)
    {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0)
        {
            NodeList subList = list.item(0).getChildNodes();
            
            if (subList != null && subList.getLength() > 0)
            {
                return subList.item(0).getNodeValue();
            }
        }
        
        return null;
    }
    
    
    @Override
    protected String doInBackground (Void... params)
    {
        try
        {
            
            
            String soapAction = urlString + "/";
            String v1_cleSecurite = new CleSecure().getCleSecurite();
            
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
            sb.append("<getEtatPortail SOAP-ENC:root=\"1\">\n");
            sb.append("<v1 xsi:type=\"xsd:string\">" + v1_cleSecurite + "</v1>\n");
            sb.append("</getEtatPortail>\n");
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
            
            
            Document xmlRetour = convertStringToDocument(response);
            Element rootElement = xmlRetour.getDocumentElement();
            String retour = getString("Result", rootElement);
            
            retour = retour.replace("Portail2", "\r\n" + "Portail2");
            
            // release all resources
            br.close();
            connection.disconnect();
            
            return retour;
            
        }
        catch (Exception ex)
        {
            return "Error " + ex.getMessage();
            //return false;
        }
    }
    
    @Override
    protected void onPostExecute (String s)
    {
        super.onPostExecute(s);
        final MyTaskInformer callBack = mCallBack.get();
        
        if (callBack != null)
        {
            callBack.onTaskDone(s);
        }
        
    }
    // <?xml version="1.0" encoding="UTF-8"?>
    // <SOAP-ENV:Envelope
    // SOAP-ENV:encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"
    // xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
    // xmlns:xsi="http://www.w3.org/1999/XMLSchema-instance"
    // xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
    // <SOAP-ENV:Body>
    // <getEtatPortailResponse SOAP-ENC:root="1">
    // <Result xsi:null="1"/>
    // </getEtatPortailResponse>
    // </SOAP-ENV:Body>
    // </SOAP-ENV:Envelope>
    
}
