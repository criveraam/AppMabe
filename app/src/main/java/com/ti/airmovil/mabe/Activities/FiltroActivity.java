package com.ti.airmovil.mabe.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ti.airmovil.mabe.R;

import java.util.ArrayList;
import java.util.List;

public class FiltroActivity extends AppCompatActivity {

    private Spinner sCorreo, sGravedad;
    private List<String> porcentajes, correos, gravedad;
    private TextView tvPorcentaje;
    private Button btnEnviarCorreo, btnOmitirFiltro;
    private CoordinatorLayout layoutrFiltro;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(R.layout.activity_filtro);
        sCorreo = (Spinner) findViewById(R.id.sCorreo);
        sGravedad = (Spinner) findViewById(R.id.sGravedad);
        btnEnviarCorreo = (Button) findViewById(R.id.btnEnviarCorreo);
        btnOmitirFiltro = (Button) findViewById(R.id.btnOmitirFiltro);
        tvPorcentaje = (TextView) findViewById(R.id.tvPorcentaje);
        layoutrFiltro = (CoordinatorLayout) findViewById(R.id.layoutrFiltro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        porcentajes = new ArrayList<String>();
        porcentajes.add("15%");
        porcentajes.add("13%");
        porcentajes.add("10%");
        porcentajes.add("6%");

        correos = new ArrayList<String>();
        correos.add("juan@mabe.com");
        correos.add("jorge@mabe.com");
        correos.add("cesar@mabe.com");
        correos.add("esau@mabe.com");

        gravedad = new ArrayList<String>();
        gravedad.add("Leve");
        gravedad.add("Moderado");
        gravedad.add("Alto");
        gravedad.add("Grave");

        sCorreo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adaptador,View vista,int id,long value){
                //Toast.makeText(FiltroActivity.this,correos.get(id).toString(), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sGravedad.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adaptador,View vista,int id,long value){
                //Toast.makeText(FiltroActivity.this,gravedad.get(id).toString(), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {}
        });



        ArrayAdapter<String> adaptadorPorcentaje = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, porcentajes);
        adaptadorPorcentaje.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adaptadorCorreo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, correos);
        adaptadorCorreo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adaptadorGravedad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gravedad);
        adaptadorCorreo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sCorreo.setAdapter(adaptadorCorreo);
        sGravedad.setAdapter(adaptadorGravedad);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnEnviarCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mensajeSnack("Enviando correctamente");
            }
        });

        btnOmitirFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(FiltroActivity.this, ReporteProductosActivity.class));
            }
        });

        final SeekBar sk = (SeekBar) findViewById(R.id.sSeekBarPorcentaje);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                tvPorcentaje.setText(String.valueOf(progress)+"%");
                //Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();
            }
        });
    }


    public void mensajeSnack(String mensaje){
        Snackbar snackbar = Snackbar.
                make(layoutrFiltro, mensaje, Snackbar.LENGTH_LONG)
                .setAction("Cerrar", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });

        snackbar.setActionTextColor(Color.parseColor("#8eeeff"));
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        snackbar.show();
    }

}
