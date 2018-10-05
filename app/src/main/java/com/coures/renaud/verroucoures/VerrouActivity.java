package com.coures.renaud.verroucoures;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;


public class VerrouActivity extends AppCompatActivity implements MyTaskInformer {

    final MyTaskInformer callback = this;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verrou);

        // Ajoute event
        this.attacheEvenementPortailBouton();
        this.attacheEvenementGetEtatPortailsBouton();


        //new ServiceClientGetEtatPortail(this).execute();

    }


    private void attacheEvenementPortailBouton() {

        Button  button = (Button) findViewById(R.id.buttonExterieur);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Portail exterieur" ,    Toast.LENGTH_SHORT).show();
                // Appel web service
                new ServiceClientRelais().execute(new ParamRelays("IMP",8));
                return true;    // <- set to true
            }
        });

        button = (Button) findViewById(R.id.buttonIonic);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Portail IONIC" ,    Toast.LENGTH_SHORT).show();
                new ServiceClientRelais().execute(new ParamRelays("IMP",7));
                return true;    // <- set to true
            }
        });

        button = (Button) findViewById(R.id.buttonMiev);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Portail MIEV" ,    Toast.LENGTH_SHORT).show();
                new ServiceClientRelais().execute(new ParamRelays("IMP",6));
                return true;    // <- set to true
            }
        });
    }

    private void attacheEvenementGetEtatPortailsBouton() {

        Button  button = (Button) findViewById(R.id.buttonEtatPortail);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View arg0) {

                Toast.makeText(getApplicationContext(), "Recup état" ,    Toast.LENGTH_SHORT).show();
                new ServiceClientGetEtatPortail(callback).execute();
                return true;    // <- set to true
            }
        });

    }

    @Override
    public void onTaskDone(String output) {

        TextView tvEtatPortail = (TextView) findViewById(R.id.textViewEtatPortails);
        tvEtatPortail.setText(output);
    }

}
