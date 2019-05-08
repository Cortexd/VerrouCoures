package com.coures.renaud.verroucoures;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.preference.PreferenceManager;

class WifiMaisonDetector
{
    private Activity activity;
    private Context context;
    
    
    WifiMaisonDetector (Activity activity, Context context)
    {
        this.activity = activity;
        this.context = context;
        
    }
    
    
    public boolean IsMaisonConnected ()
    {
        // Recup dans les préférences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                this.context);
        String SSID_MAISON = sharedPreferences.getString("SSID_Maison", "Error");
        
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected())
        {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(
                    Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !connectionInfo.getSSID().isEmpty())
            {
                ssid = connectionInfo.getSSID().replace("\"", "");
                return ssid.equals(SSID_MAISON);
            }
        }
        
        return false;
        
    }
    
}
