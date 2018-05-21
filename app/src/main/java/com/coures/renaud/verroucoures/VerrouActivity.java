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
        new WebService().execute(new ParamRelays("IMP",1));

    }

    public void onClickPortailMiev(View v){
        new WebService().execute(new ParamRelays("IMP",1));
    }

    public void onClickPortailIonic(View v){
        new WebService().execute(new ParamRelays("IMP",1));
    }
}
