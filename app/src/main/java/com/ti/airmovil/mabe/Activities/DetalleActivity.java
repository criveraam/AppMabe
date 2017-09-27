package com.ti.airmovil.mabe.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Response;
import com.ti.airmovil.mabe.R;

public class DetalleActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Response.Listener listener;
    private Response.ErrorListener listenerError;

    private ImageView ivFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        /* barra superior de la activity */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*****/

        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

}
