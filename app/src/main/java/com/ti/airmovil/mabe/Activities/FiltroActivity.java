package com.ti.airmovil.mabe.Activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
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
    private List<String> porcentajes, correos, gravedad;
    private CoordinatorLayout layoutFiltro;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(R.layout.activity_filtro);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        porcentajes();
        spinnerMail();
        btns();

    }

    private void porcentajes(){
        final SeekBar sk = (SeekBar) findViewById(R.id.sSeekBarPorcentaje1);
        sk.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                TextView textViewPorcentaje1 = (TextView) findViewById(R.id.textview_porcentaje_1);
                textViewPorcentaje1.setText(String.valueOf(progress)+"%");
                //Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();
            }
        });

        final SeekBar sk1 = (SeekBar) findViewById(R.id.sSeekBarPorcentaje2);
        sk1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                TextView textViewPorcentaje2 = (TextView) findViewById(R.id.textview_porcentaje_2);
                textViewPorcentaje2.setText(String.valueOf(progress)+"%");
                //Toast.makeText(getApplicationContext(), String.valueOf(progress),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void btns(){
        Button btn1 = (Button) findViewById(R.id.btn1filtro);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Snackbar.make(view, "Se ha enviado la información", Snackbar.LENGTH_SHORT).show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                NotificationCompat.Builder mBuilder;
                                NotificationManager mNotifyMgr =(NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
                                int icono = R.drawable.mabe_png;
                                Intent intent = new Intent(FiltroActivity.this, MainActivity.class);
                                PendingIntent pendingIntent = PendingIntent.getActivity(FiltroActivity.this, 0,intent, 0);
                                mBuilder = new NotificationCompat.Builder(getApplicationContext());
                                Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                mBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                                        .setContentIntent(pendingIntent)
                                        .setSmallIcon(icono)
                                        .setContentTitle("Notificación Mabe")
                                        .setContentText("Filtro recibido desde la aplicacion de monitoreo")
                                        //.setVibrate(new long[]{500,500,500,500,500,500,500,500,500})
                                        .setSound(soundUri)
                                        .setAutoCancel(true);
                                mNotifyMgr.notify(1, mBuilder.build());
                            }
                        });
                    }
                };
                thread.start();
            }
        });

        Button btn2 = (Button) findViewById(R.id.btn2filtro);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Se enviara sin filtro, espere un momento.", Snackbar.LENGTH_SHORT).show();
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1700);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent().setClass(FiltroActivity.this, ReporteProductosActivity.class));
                            }
                        });
                    }
                };
                thread.start();
            }
        });
    }

    private void spinnerMail(){
        correos = new ArrayList<String>();
        correos.add("Selecciona un email");
        correos.add("juan@mabe.com");
        correos.add("jorge@mabe.com");
        correos.add("cesar@mabe.com");
        correos.add("esau@mabe.com");

        gravedad = new ArrayList<String>();
        gravedad.add("Selecciona un nivel");
        gravedad.add("Moderado");
        gravedad.add("Alto");
        gravedad.add("Grave");

        Spinner sCorreo = (Spinner) findViewById(R.id.sCorreo);

        ArrayAdapter<String> adaptadorCorreo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, correos);
        adaptadorCorreo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sCorreo.setAdapter(adaptadorCorreo);

        Spinner spinner_gravedad = (Spinner) findViewById(R.id.spinner_gravedad);

        ArrayAdapter<String> adaptadorGravedad = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gravedad);
        adaptadorGravedad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gravedad.setAdapter(adaptadorGravedad);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            //startActivity(new Intent().setClass(FiltroActivity.this, MainActivity.class));
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
