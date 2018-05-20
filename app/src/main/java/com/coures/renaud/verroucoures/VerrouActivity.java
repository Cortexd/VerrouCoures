package com.coures.renaud.verroucoures;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class VerrouActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verrou);
    }

    public void onClickPortailExterieur(View v){
        WebService ws = new WebService();
        ws.executionRequete("IMP",0);
    }

    public void onClickPortailMiev(View v){
        WebService ws = new WebService();
        ws.executionRequete("IMP",1);
    }

    public void onClickPortailIonic(View v){
        WebService ws = new WebService();
        ws.executionRequete("IMP",2);
    }
}
