package com.coures.renaud.verroucoures;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.RadioButton;
import android.widget.TextView;

public  class WifiMaisonDetector
{
    private Activity activity;
    private Context context;
    private RadioButton radioWifi;
    
    public WifiMaisonDetector (Activity activity, Context context)
    {
        this.activity = activity;
        this.context = context;
        this.radioWifi = (RadioButton) this.activity.findViewById(R.id.radioButton);
        IsMaisonConnected();
    }
    
    
    public void IsMaisonConnected()
    {
        
        String ssid = null;
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null && !connectionInfo.getSSID().isEmpty())
            {
                ssid = connectionInfo.getSSID();
            }
        }
        radioWifi.setChecked(true);
    }
    
}
