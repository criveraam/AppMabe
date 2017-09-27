package com.ti.airmovil.mabe.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.ti.airmovil.mabe.R;

import java.util.ArrayList;
import java.util.List;

public class FiltroActivity extends AppCompatActivity {

    private Spinner sPorcentaje, sCorreo, sGravedad;
    List<String> porcentajes, correos, gravedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtro);
        sPorcentaje = (Spinner) findViewById(R.id.sPorcentaje);
        sCorreo = (Spinner) findViewById(R.id.sCorreo);
        sGravedad = (Spinner) findViewById(R.id.sGravedad);
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
        gravedad.add("1");
        gravedad.add("2");
        gravedad.add("3");
        gravedad.add("4");
        gravedad.add("5");

        // Spinner click listener
        sPorcentaje.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adaptador,View vista,int id,long value){
                Toast.makeText(FiltroActivity.this,porcentajes.get(id).toString(), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sCorreo.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adaptador,View vista,int id,long value){
                Toast.makeText(FiltroActivity.this,correos.get(id).toString(), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        sGravedad.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> adaptador,View vista,int id,long value){
                Toast.makeText(FiltroActivity.this,gravedad.get(id).toString(), Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });



        ArrayAdapter<String> adaptadorPorcentaje = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, porcentajes);
        adaptadorPorcentaje.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adaptadorCorreo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, correos);
        adaptadorCorreo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adaptadorGravedad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gravedad);
        adaptadorCorreo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sPorcentaje.setAdapter(adaptadorPorcentaje);
        sCorreo.setAdapter(adaptadorCorreo);
        sGravedad.setAdapter(adaptadorGravedad);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

}
