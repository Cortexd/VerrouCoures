package com.coures.renaud.verroucoures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.nio.charset.Charset;

public class VerrouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verrou);

        //https://stackoverflow.com/questions/45406996/how-to-encrypt-string-in-java-and-decrypt-in-python
        String plaintext = "Hello World!";
        System.out.println("Plain text: " + plaintext);
        //String ciphertext = new Encryption().encrypt(plaintext.getBytes("UTF-8"));
        String ciphertext = new Encryption().encrypt(plaintext.getBytes());
        System.out.println("Encrypted text: " + ciphertext);
    }

    public void onClickPortailExterieur(View v){
        new ServiceClient().execute(new ParamRelays("IMP",8));

    }

    public void onClickPortailMiev(View v){
        new ServiceClient().execute(new ParamRelays("IMP",6));
    }

    public void onClickPortailIonic(View v){
        new ServiceClient().execute(new ParamRelays("IMP",7));
    }
}
