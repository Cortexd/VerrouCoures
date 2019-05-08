package com.coures.renaud.verroucoures;

import android.app.Application;

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
        return "http://192.168.1.3:18099/";
        //return urlWebService;
    }
    
    public void setUrlWebService (String s)
    {
        this.urlWebService = s;
    }
    
}
