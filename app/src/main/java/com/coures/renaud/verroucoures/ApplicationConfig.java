package com.coures.renaud.verroucoures;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

// Classe qui permet de passer des paramétres surtout lors de tache asycnhrone
// Ex : class ServiceClientRelais extends AsyncTask
// Il faut penser à rajouter dans le AndroidManifest.xml :
// <application
//        android:name=".ApplicationConfig"

// utilisation :
//      ApplicationConfig conf = ApplicationConfig.getConfig();
//      this.urlString = conf.getUrlWebService();
// ou
//      conf.setUrlWebService("TOTO");

public class ApplicationConfig extends Application
{
    private static ApplicationConfig instance;
    private String urlWebService = "";
    private String urlWebServiceWifi = "";
    private String urlWebServiceInternet = "";
    
    public static ApplicationConfig getConfig ()
    {
        return instance;
    }
    
    public void onCreate ()
    {
        super.onCreate();
        instance = this;
    }

    public String getUrlWebService ()
    {
       return urlWebService;
    }

    public String getUrlWebServiceWifi ()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sharedPreferences.getString("IP_Locale_Banane", "Error");
        String port = sharedPreferences.getString("Port_Banane", "Error");

        String url = "http://" + ip + ":" + port + "/";
        return url;
    }

    public String getUrlWebServiceInternet ()
    {
        return urlWebServiceInternet;
    }
    
    public void setUrlWebService (String s)
    {
        this.urlWebService = s;
    }

    public void setUrlWebServiceWifi (String s)
    {
        this.urlWebServiceWifi = s;
    }

    public void setUrlWebServiceInternet (String s)
    {
        this.urlWebServiceInternet = s;
    }
    
}
