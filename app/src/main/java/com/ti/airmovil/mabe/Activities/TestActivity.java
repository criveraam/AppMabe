package com.ti.airmovil.mabe.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ti.airmovil.mabe.R;

public class TestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(R.layout.activity_test);

        Bundle bundle = getIntent().getExtras();

        if(bundle.getString("id_producto")!= null) {
             Log.e("ok", "1");
        }
    }
}
