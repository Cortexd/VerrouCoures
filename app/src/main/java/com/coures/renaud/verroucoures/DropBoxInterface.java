package com.coures.renaud.verroucoures;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;



import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.IOException;

public class DropBoxInterface extends AsyncTask<Void, Void, String> {

    private static String ACCESS_TOKEN = 

    TextView tvEtatPortail;

    private Context mContext;
    private View rootView;

    public DropBoxInterface(Context context){
        this.mContext=context;

    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        tvEtatPortail = (TextView) mContext.findViewById(R.id.textViewEtatPortails);

    }
        @Override
    protected String doInBackground(Void... v)
    {

        try
        {
            // Create Dropbox client
            DbxRequestConfig config = new DbxRequestConfig("dropbox/java-tutorial", "en_US");
            DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

            // Get current account info
            FullAccount account = client.users().getCurrentAccount();
            System.out.println(account.getName().getDisplayName());

            // Get files and folder metadata from Dropbox root directory
            //ListFolderResult result = client.files().listFolder("/Application/VerrouCoures/");

            client.files().download("/Application/VerrouCoures/ip.txt");
        }
        catch (Exception ex)
        {
            return "";
        }

      return "test";
    }

    @Override
    protected void onPostExecute(String  result) {

               tvEtatPortail.setText("ip " + result);
    }

}


