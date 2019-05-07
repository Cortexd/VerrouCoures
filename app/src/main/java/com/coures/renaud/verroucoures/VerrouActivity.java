package com.coures.renaud.verroucoures;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;


public class VerrouActivity extends AppCompatActivity implements MyTaskInformer
{
    
    final MyTaskInformer callback = this;
    int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 0;
    
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verrou);
        
        // Ajoute event
        this.attacheEvenementPortailBouton();
        this.attacheEvenementGetEtatPortailsBouton();
        
        
        //new ServiceClientGetEtatPortail(this).execute();
    
        WebView wv =  findViewById(R.id.webViewImage);
        String mStringUrl = "https://picsum.photos/150/200/?random";
        wv.loadDataWithBaseURL(null, "<html><head></head><body><table style=\"width:100%; height:100%;\"><tr><td style=\"vertical-align:middle;\"><img src=\"" + mStringUrl + "\"></td></tr></table></body></html>", "html/css", "utf-8", null);
        
    }
    
    public void myClickHandlerTEST (View target)
    {
        
        try
        {
            TextView tvEtatPortail = findViewById(R.id.textViewEtatPortails);
            
            new WifiMaisonDetector(this, getApplicationContext()).IsMaisonConnected();
    
            
            tvEtatPortail.setText("Get IP");
            
            checkPermissionsAndGetIp();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        
    }
    
    private void attacheEvenementPortailBouton ()
    {
        
        Button button = findViewById(R.id.buttonExterieur);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick (View arg0)
            {
                Toast.makeText(getApplicationContext(), "Portail exterieur",
                               Toast.LENGTH_SHORT).show();
                // Appel web service
                new ServiceClientRelais().execute(new ParamRelays("IMP", 8));
                return true;    // <- set to true
            }
        });
        
        button = findViewById(R.id.buttonIonic);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick (View arg0)
            {
                Toast.makeText(getApplicationContext(), "Portail IONIC", Toast.LENGTH_SHORT).show();
                new ServiceClientRelais().execute(new ParamRelays("IMP", 7));
                return true;    // <- set to true
            }
        });
        
        button = findViewById(R.id.buttonMiev);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick (View arg0)
            {
                Toast.makeText(getApplicationContext(), "Portail MIEV", Toast.LENGTH_SHORT).show();
                new ServiceClientRelais().execute(new ParamRelays("IMP", 6));
                return true;    // <- set to true
            }
        });
    }
    
    
    private void attacheEvenementGetEtatPortailsBouton ()
    {
        
        
        Button button = findViewById(R.id.buttonEtatPortail);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick (View arg0)
            {
                
                Toast.makeText(getApplicationContext(), "Recup état", Toast.LENGTH_SHORT).show();
                new ServiceClientGetEtatPortail(callback).execute();
                return true;    // <- set to true
            }
        });
        
    }
    
    @Override
    public void onTaskDone (String output)
    {
        
        TextView tvEtatPortail = findViewById(R.id.textViewEtatPortails);
        tvEtatPortail.setText(output);
    }
    
    public void checkPermissionsAndGetIp()
    {
        
        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
    
        if (ActivityCompat.checkSelfPermission(this,
                                               Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
    
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Permission Needed");
                builder.setMessage("You must accept the permission.");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick (DialogInterface dialog, int id)
                    {
                        ActivityCompat.requestPermissions(VerrouActivity.this, permissions, MY_PERMISSIONS_REQUEST_READ_CONTACTS    );
                    }
                });
                builder.setNegativeButton("Annulé", new DialogInterface.OnClickListener()
                {
                    public void onClick (DialogInterface dialog, int id)
                    {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            else
            {
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
        else
        {
            new DropBoxInterface(this).execute();
        }
    
    }
}
