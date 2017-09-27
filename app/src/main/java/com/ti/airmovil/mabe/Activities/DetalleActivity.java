package com.ti.airmovil.mabe.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.ti.airmovil.mabe.R;
import com.ti.airmovil.mabe.utilerias.AnimacionJAVA;

import static com.ti.airmovil.mabe.R.id.imageView;

public class DetalleActivity extends AppCompatActivity {

    private ImageView ivFoto;
    boolean banderaFoto = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        ivFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Display display = getWindowManager().getDefaultDisplay();
                int ancho = display.getWidth();
                int  alto= display.getHeight();
                ViewGroup.LayoutParams params = ivFoto.getLayoutParams();
                if(banderaFoto){
                    params.height = 600;
                    ivFoto.setLayoutParams(params);
                    banderaFoto = false;
                }else{
                    params.height = alto-15;
                    ivFoto.setLayoutParams(params);
                    banderaFoto = true;
                }
            }
        });
    }

    public void salir(){
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
