package com.coures.renaud.verroucoures;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Environment;
import android.preference.PreferenceManager;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.users.FullAccount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

public class DropBoxInterface extends AsyncTask<Void, Void, String>
{
    
    private String accessTokenDropBox;
    private Activity activity;
    private Context myCtx;
    private Exception mException;
    //TextView tvEtatPortail;
    
    public DropBoxInterface (Activity activity, Context myCtx)
    {
        this.activity = activity;
        this.myCtx = myCtx;
    
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.myCtx.getApplicationContext());
        this.accessTokenDropBox = sharedPreferences.getString("accessTokenDropBox", "");
    }
    
    @Override
    protected void onPreExecute ()
    {
        super.onPreExecute();
        //tvEtatPortail = (TextView) activity.findViewById(R.id.textViewEtatPortails);
    }
    
    @Override
    protected String doInBackground (Void... v)
    {
        
        try
        {
            // Create Dropbox client
            DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
            DbxClientV2 client = new DbxClientV2(config, accessTokenDropBox);
            
            File path = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS);
            String currentName = "ip.txt";
            File file = new File(path, currentName);
            
            // Make sure the Downloads directory exists.
            if (!path.exists())
            {
                if (!path.mkdirs())
                {
                    mException = new RuntimeException("Unable to create directory: " + path);
                }
            }
            else if (!path.isDirectory())
            {
                mException = new IllegalStateException("Download path is not a directory: " + path);
                return null;
            }
            
            try (OutputStream outputStream = new FileOutputStream(file))
            {
                client.files().download("/Application/VerrouCoures/ip.txt").download(outputStream);
            }
            
            // DbxDownloader<FileMetadata> downloader = client.files().download("/Application/VerrouCoures/ip.txt");
            
            // Get current account info
            FullAccount account = client.users().getCurrentAccount();
            System.out.println(account.getName().getDisplayName());
            
            if (file.exists())
            {
                //Read text from file
                StringBuilder text = new StringBuilder();
                
                try
                {
                    BufferedReader br = new BufferedReader(new FileReader(file));
                    String line;
                    
                    while ((line = br.readLine()) != null)
                    {
                        text.append(line);
                        //text.append('\n');
                    }
                    br.close();
                }
                catch (IOException e)
                {
                    //You'll need to add proper error handling here
                }
               
                return text.toString();
            }
            
        }
        catch (Exception ex)
        {
            return "Exception";
        }
        
        return "test rien";
    }
    
    
    @Override
    protected void onPostExecute (String result)
    {
        
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(
                this.myCtx.getApplicationContext());
        String port = sharedPreferences.getString("Port_Banane", "Error");
        
        String url = "http://" + result + ":" + port + "/";
        
        // maj des parametres partagées
        ApplicationConfig conf = ApplicationConfig.getConfig();
        conf.setUrlWebServiceInternet(url);
        
    }
    
}


