package com.ti.airmovil.mabe.Activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ti.airmovil.mabe.Adapters.ProductosAdapter;
import com.ti.airmovil.mabe.Adapters.ReporteProductosAdapter;
import com.ti.airmovil.mabe.Helper.Config;
import com.ti.airmovil.mabe.Models.ProductosModel;
import com.ti.airmovil.mabe.Models.ReporteProductosModel;
import com.ti.airmovil.mabe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
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
    private Button buttomBuscar;
    private int indice_hora;
    private TextView textView_hour;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_productos);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_productos);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        getDatos1 = new ArrayList<>();
        getData();
        initCollapsingToolbar();
        setHour();
    }

    private void setHour(){
        textView_hour = (TextView) findViewById(R.id.textView_hour);
        textView_hour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar calendar = Calendar.getInstance();
                TimePickerDialog timePickerDialogfrom = new TimePickerDialog(ReporteProductosActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        //Conversion for AM/PM as you are doing
                        String amOrpm = (hourOfDay>11)? "PM":"AM";
                        String ceroHora = (hourOfDay < 10) ? 0 + "" + hourOfDay:""+hourOfDay;
                        String ceroMinuto = (minute < 10) ? 0+""+ minute: ""+ minute;
                        textView_hour.setText(ceroHora + ":" + ceroMinuto + " " + amOrpm);
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);
                timePickerDialogfrom.show();
            }
        });
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
                    public void onResponse(String response) {
                        parseJson(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                });
        queue.add(postRequest);
    }

    private void parseJson(String rqt){
        try{
            JSONArray obj = new JSONArray(rqt);
            for (int i = 0; i < obj.length(); i++){
                JSONObject jsonObject=null;
                ReporteProductosModel getDatos2 = new ReporteProductosModel();
                jsonObject = obj.getJSONObject(i);
                try{
                    getDatos2.setNombreProducto(jsonObject.getString("4"));

                    JSONArray v1Mabe = jsonObject.getJSONArray("6");
                    Log.e(TAG, "INFORMACION ::::>" + v1Mabe);


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
