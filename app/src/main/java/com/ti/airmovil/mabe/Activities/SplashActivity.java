package com.ti.airmovil.mabe.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ti.airmovil.mabe.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();
    private boolean direccion = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().getAttributes().windowAnimations = R.style.DialogAnimationDown;
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        direccion = false;

        if(direccion){
            startActivity(new Intent().setClass(SplashActivity.this, MainActivity.class));
            finish();
        }else {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    // Comenzara la siguiente Actividad
                    Intent mainIntent = new Intent().setClass(SplashActivity.this, MainActivity.class);
                    startActivity(mainIntent);
                    //overridePendingTransition(R.anim.anim_up, R.anim.anim_up);
                    // Cierra la actividad para que el usuario no pueda volver atrás
                    // Actividad pulsando el botón Atrás
                    finish();
                }
            };
            // Simula un proceso de carga largo en el asesor_fragmento_inicio de la aplicación.
            Timer timer = new Timer();
            timer.schedule(task, 2000);
        }
    }
}
