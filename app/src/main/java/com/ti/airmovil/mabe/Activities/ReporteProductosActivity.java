package com.ti.airmovil.mabe.Activities;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ti.airmovil.mabe.Adapters.ReporteProductosAdapter;
import com.ti.airmovil.mabe.Helper.Config;
import com.ti.airmovil.mabe.Models.ReporteProductosModel;
import com.ti.airmovil.mabe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ReporteProductosActivity extends AppCompatActivity {
    private static final String TAG = ReporteProductosActivity.class.getSimpleName();
    private List<ReporteProductosModel> getDatos1;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ReporteProductosAdapter adapter;
    private RequestQueue requestQueue;
    private CollapsingToolbarLayout collapsingToolbar;
    private int indice_hora = 0;
    private LinearLayout capa_progressbar, capa_contenedor, capa_sin_conexion;
    private Spinner spinner_hora;
    private Button button_aplicar;
    public static Activity FA2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_productos);

        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation3;

        MainActivity.FA1.finish();
        FA2 = this;

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reportes);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent().setClass(ReporteProductosActivity.this, MainActivity.class));
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        capa_progressbar = (LinearLayout) findViewById(R.id.capa_progressbar);
        capa_contenedor = (LinearLayout) findViewById(R.id.capa_sin_conexion);
        capa_sin_conexion = (LinearLayout) findViewById(R.id.capa_sin_conexion);
        spinner_hora = (Spinner) findViewById(R.id.spinner_hora);

        TextView textViewFecha = (TextView) findViewById(R.id.textView_msj_filtro);

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);


        textViewFecha.setText(getYesterdayDateString());
        //textViewFecha.setText("Reporte horas día" + day+"-"+month+"-"+year+"");

        List horas = new ArrayList();

        for (int i = 0; i<24; i++){
            if(i<10){
                horas.add("0"+i+":00");
            }else{
                horas.add(i+":00");
            }

        }

        ArrayAdapter spinner_adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, horas);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_hora.setAdapter(spinner_adapter);

        button_aplicar = (Button) findViewById(R.id.button_aplicar);
        button_aplicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //startActivity(new Intent(ReporteProductosActivity.this, SplashActivity.class));

                if(spinner_hora.getSelectedItem().toString().split(":").length >=2){
                    initService();
                    indice_hora = Integer.parseInt(spinner_hora.getSelectedItem().toString().split(":")[0]);
                    getData();
                }else{
                    Toast.makeText(ReporteProductosActivity.this, "Seleccione una hora para mostrar", Toast.LENGTH_SHORT).show();
                }


            }
        });

        ((Button) findViewById(R.id.button_refrescar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initService();
            }
        });

        //getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_productos);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        getDatos1 = new ArrayList<>();
        initService();
        initCollapsingToolbar();
    }

    private Date yesterday() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    private String getYesterdayDateString()  {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return dateFormat.format(yesterday());
    }

    private void initCollapsingToolbar(){
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_reportes);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Filtros de Busqueda");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Config.URL_REPORTE_PRODUCTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        capa_progressbar.setVisibility(View.GONE);
                                        parseJson(response);
                                    }
                                });
                            }
                        };
                        thread.start(); //start the thread
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        capa_contenedor.setVisibility(View.VISIBLE);
                                        capa_progressbar.setVisibility(View.GONE);
                                        Animation animation = AnimationUtils.loadAnimation(ReporteProductosActivity.this, R.anim.anim_up);
                                        animation.setDuration(1000);
                                        capa_contenedor.setAnimation(animation);
                                        capa_contenedor.animate();
                                        animation.start();
                                    }
                                });
                            }
                        };
                        thread.start();
                    }
                });
        queue.add(postRequest);
    }

    private void parseJson(String rqt){
        try{
            JSONArray obj = new JSONArray(rqt);
            getDatos1 = new ArrayList<>();

            for (int i = 0; i < obj.length(); i++){
                ReporteProductosModel getDatos2 = new ReporteProductosModel();
                JSONObject producto = obj.getJSONObject(i);
                JSONArray precios_mabe = producto.getJSONArray("6");
                JSONArray precios_wallmart = producto.getJSONArray("7");
                JSONArray precios_best_buy = producto.getJSONArray("8");

                try{
                    double m = precios_mabe.getDouble(indice_hora);
                    double w = precios_wallmart.getDouble(indice_hora);
                    double b = precios_best_buy.getDouble(indice_hora);
                    int colorWalmart = android.R.color.black;
                    int colorBestBuy = android.R.color.black;
                    double porcentaje_w = w/100;
                    double porcentaje_b = b/100;

                    DecimalFormat formato_decimal = new DecimalFormat(".00");
                    String porcentaje_wallmart = "";
                    String porcentaje_best_buy = "";
                    if(m<w){
                        double mw = w - m;
                        double porcentaje_mw = mw/porcentaje_w;
                        porcentaje_wallmart = formato_decimal.format(porcentaje_mw);
                        porcentaje_wallmart = (porcentaje_wallmart.charAt(0)=='.')? "0"+porcentaje_wallmart : porcentaje_wallmart;
                        colorWalmart = Color.parseColor("#009933");
                    }else{
                        double mw = m - w;
                        double porcentaje_mw = mw/porcentaje_w;
                        porcentaje_wallmart = formato_decimal.format(porcentaje_mw);
                        porcentaje_wallmart = (porcentaje_wallmart.charAt(0)=='.')? "0"+porcentaje_wallmart : porcentaje_wallmart;
                        colorWalmart = Color.parseColor("#FF0000");
                    }

                    if(m<b){
                        double mb = b - m;
                        double porcentaje_mb = mb/porcentaje_b;
                        porcentaje_best_buy = formato_decimal.format(porcentaje_mb);
                        porcentaje_best_buy = (porcentaje_best_buy.charAt(0)=='.')? "0"+porcentaje_best_buy : porcentaje_best_buy;
                        colorBestBuy = Color.parseColor("#009933");
                    }else{
                        double mb = m - b;
                        double porcentaje_mb = mb/porcentaje_b;
                        porcentaje_best_buy = formato_decimal.format(porcentaje_mb);
                        porcentaje_best_buy = (porcentaje_best_buy.charAt(0)=='.')? "0"+porcentaje_best_buy : porcentaje_best_buy;
                        colorBestBuy = Color.parseColor("#FF0000");
                    }


                    getDatos2.setNombreProducto(producto.getString("4"));
                    getDatos2.setPrecioMabe("$ "+m);
                    getDatos2.setPrecioWalmart("$ "+w);
                    getDatos2.setPrecioBestBuy("$ "+b);
                    getDatos2.setPorcentajeWalmart(porcentaje_wallmart+" %");
                    getDatos2.setPorcentajeBestBuy(porcentaje_best_buy+" %");
                    getDatos2.setColorWalmart(colorWalmart);
                    getDatos2.setColorBestBuy(colorBestBuy);
                    int id = i;
                    getDatos2.setCrawler(String.valueOf(id));


                }catch (JSONException e){
                    e.printStackTrace();
                }
                getDatos1.add(getDatos2);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        adapter = new ReporteProductosAdapter(getApplicationContext(), getDatos1, recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initService(){
        capa_progressbar.setVisibility(View.VISIBLE);
        if (Config.compruebaConexion(ReporteProductosActivity.this)) {
            capa_contenedor.setVisibility(View.GONE);
            getData();
        }else{
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            capa_contenedor.setVisibility(View.GONE);
                            capa_progressbar.setVisibility(View.GONE);
                            capa_sin_conexion.setVisibility(View.VISIBLE);
                            Animation animation = AnimationUtils.loadAnimation(ReporteProductosActivity.this, R.anim.anim_up);
                            animation.setDuration(1000);
                            capa_sin_conexion.setAnimation(animation);
                            capa_sin_conexion.animate();
                            animation.start();
                        }
                    });
                }
            };
            thread.start();
        }
    }
}
