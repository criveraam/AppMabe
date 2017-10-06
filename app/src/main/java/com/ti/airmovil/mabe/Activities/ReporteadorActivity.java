package com.ti.airmovil.mabe.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ti.airmovil.mabe.Adapters.ReporteProductosAdapter;
import com.ti.airmovil.mabe.Helper.Config;
import com.ti.airmovil.mabe.Models.ProductosModel;
import com.ti.airmovil.mabe.Models.ReporteProductosModel;
import com.ti.airmovil.mabe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ReporteadorActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<ReporteProductosModel> getDatos1;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private Toolbar toolbar;
    private LinearLayout capa1;
    private RelativeLayout capa2;
    private int indice_hora = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporteador);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        setSupportActionBar(toolbar);
        initCollapsingToolbar();

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        getDatos1 = new ArrayList<>();

        initService();
    }

    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar2);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1)
                    scrollRange = appBarLayout.getTotalScrollRange();

                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Reportes");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private void initService(){

        getData();

        /*
        capa1.setVisibility(View.VISIBLE);
        if (Config.compruebaConexion(ReporteadorActivity.this)) {
            Log.e(TAG, "2");
            //capa1.setVisibility(View.GONE);
            capa2.setVisibility(View.GONE);
            getData();
        }else{
            Log.e(TAG, "3");
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
                            capa2.setVisibility(View.VISIBLE);
                            capa1.setVisibility(View.GONE);
                            Animation animation = AnimationUtils.loadAnimation(ReporteadorActivity.this, R.anim.anim_up);
                            animation.setDuration(1000);
                            capa2.setAnimation(animation);
                            capa2.animate();
                            animation.start();
                        }
                    });
                }
            };
            thread.start();
        }*/
    }

    private void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Config.URL_REPORTE_PRODUCTOS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJson(response);
                        /*Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1500);
                                } catch (InterruptedException e) {
                                }

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        capa1.setVisibility(View.GONE);
                                        parseJson(response);
                                    }
                                });
                            }
                        };
                        thread.start(); //start the thread*/
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        /*Thread thread = new Thread() {
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
                                        capa2.setVisibility(View.VISIBLE);
                                        capa1.setVisibility(View.GONE);
                                        Animation animation = AnimationUtils.loadAnimation(ReporteadorActivity.this, R.anim.anim_up);
                                        animation.setDuration(1000);
                                        capa2.setAnimation(animation);
                                        capa2.animate();
                                        animation.start();
                                    }
                                });
                            }
                        };
                        thread.start();*/
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

                    Log.e(TAG, "INFORMACION Mabe::::>" + precios_mabe);
                    Log.e(TAG, "INFORMACION Walmart::::>" + precios_wallmart);
                    Log.e(TAG, "INFORMACION Best Buy::::>" + precios_best_buy);

                }catch (JSONException e){
                    e.printStackTrace();
                }
                getDatos1.add(getDatos2);
            }
            Log.e(TAG, "Conversion a JSONObjet::: " + obj);
        }catch (JSONException e){
            e.printStackTrace();
        }
        adapter = new ReporteProductosAdapter(getApplicationContext(), getDatos1, recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
