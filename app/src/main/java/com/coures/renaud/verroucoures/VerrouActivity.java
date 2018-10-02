package com.coures.renaud.verroucoures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;

public class VerrouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verrou);

        //https://stackoverflow.com/questions/45406996/how-to-encrypt-string-in-java-and-decrypt-in-python
        //String plaintext = "Hello World!";
        //System.out.println("Plain text: " + plaintext);
        //String ciphertext = new Encryption().encrypt(plaintext.getBytes("UTF-8"));
        //String ciphertext = new Encryption().encrypt(plaintext.getBytes());
        //System.out.println("Encrypted text: " + ciphertext);

         this.attacheEvenementPortailBouton();
    }

    private void attacheEvenementPortailBouton() {

        Button  button = (Button) findViewById(R.id.buttonExterieur);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View arg0) {
                // Message
                Toast.makeText(getApplicationContext(), "Portail exterieur" ,    Toast.LENGTH_SHORT).show();
                // Appel web service
                new ServiceClient().execute(new ParamRelays("IMP",8));
                return true;    // <- set to true
            }
        });

        button = (Button) findViewById(R.id.buttonIonic);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Portail IONIC" ,    Toast.LENGTH_SHORT).show();
                new ServiceClient().execute(new ParamRelays("IMP",7));
                return true;    // <- set to true
            }
        });

        button = (Button) findViewById(R.id.buttonMiev);
        button.setOnLongClickListener(new View.OnLongClickListener()
        {
            public boolean onLongClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Portail MIEV" ,    Toast.LENGTH_SHORT).show();
                new ServiceClient().execute(new ParamRelays("IMP",6));
                return true;    // <- set to true
            }
        });
    }

    public void onClickGetEtatPortails(View v){

        TextView tvEtatPortail = (TextView) findViewById(R.id.textViewEtatPortails);
        //tvEtatPortail.setText("MIEV : Ouvert ?\nIONIC : Fermé ?\nExterieur : Fermé ?");

        //new ServiceClient().execute(new ParamRelays("IMP",6));

        //tvEtatPortail.setText(

    }
}
