package com.ti.airmovil.mabe.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Response;
import com.ti.airmovil.mabe.R;
import com.ti.airmovil.mabe.utilerias.AnimacionJAVA;

public class DetalleActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Response.Listener listener;
    private Response.ErrorListener listenerError;

    private ImageView ivFoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimacionJAVA.bigScaleAnimacion(ivFoto,1500);
            }
        });

    }

    public void mensaje(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setMessage("¿Salir?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Si", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                DetalleActivity.this.finish();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

}
