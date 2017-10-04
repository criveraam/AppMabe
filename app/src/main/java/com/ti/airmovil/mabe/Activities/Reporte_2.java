package com.ti.airmovil.mabe.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ti.airmovil.mabe.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class Reporte_2 extends AppCompatActivity {

    private TableLayout trproductos;
    private LinearLayout linear_reporte_hora;
    private ProgressBar progressBar;
    private Button btn_aplicar_hora;
    private int indice_hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_2);
        trproductos = (TableLayout) findViewById(R.id.trproductos_reporte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reporte_2);
        btn_aplicar_hora = (Button) findViewById(R.id.btn_aplicar_hora);
        Spinner spinner = (Spinner) findViewById(R.id.sHora_reporte);

        String[] horas = new String[24];

        for(int i=0; i<24; i++){
            horas[i] = (i<10)? "0"+i+":00 Hrs" : i+":00 Hrs";
        }

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, horas));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                indice_hora = position;
            }
            @Override public void onNothingSelected(AdapterView<?> parent){}
        });

        btn_aplicar_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableRow encabezado = (TableRow) findViewById(R.id.table_row_encabezado);
                trproductos.removeAllViews();
                trproductos.addView(encabezado);
                getData();
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getData();
    }


    private void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://5piso.com/~walmart/servicios/getProductosamounts.php",
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        generarTabla(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("JMA", "Error:::" + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //params.put("id_crowler", idProducto);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void generarTabla(String response){
        try{
            JSONArray objArray = new JSONArray(response);
            ContextThemeWrapper wrappedContext = null;
            for(int j=0; j<objArray.length(); j++){
                JSONObject json_producto = objArray.getJSONObject(j);
                JSONArray precio_mabe = json_producto.getJSONArray(String.valueOf(6));
                JSONArray precio_wallmart = json_producto.getJSONArray(String.valueOf(7));
                JSONArray precio_best_buy = json_producto.getJSONArray(String.valueOf(8));

                TableRow tr1 = new TableRow(this);
                tr1.setLayoutParams(new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
                if(j%2==0){
                    wrappedContext = new ContextThemeWrapper(this, R.style.celda_table);
                    tr1.setBackgroundResource(R.color.cardview_light_background);
                }else{
                    wrappedContext = new ContextThemeWrapper(this, R.style.celda_table_2);
                    tr1.setBackgroundResource(R.color.azulFuerte);
                }

                TextView nombre_producto = new TextView(wrappedContext, null, 0);
                nombre_producto.setText(json_producto.getString(String.valueOf(4)));
                nombre_producto.setMaxWidth(300);
                nombre_producto.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                tr1.addView(nombre_producto);

                TextView tv_precio_mabe = new TextView(wrappedContext, null, 0);
                TextView tv_precio_wallmart = new TextView(wrappedContext, null, 0);
                TextView tv_precio_best_buy = new TextView(wrappedContext, null, 0);

                double m = precio_mabe.getDouble(indice_hora);
                double w = precio_wallmart.getDouble(indice_hora);
                double b = precio_best_buy.getDouble(indice_hora);
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
                    porcentaje_wallmart += "% ⇧";
                    tv_precio_wallmart.setTextColor(Color.GREEN);
                }else{
                    double mw = m - w;
                    double porcentaje_mw = mw/porcentaje_w;
                    porcentaje_wallmart = formato_decimal.format(porcentaje_mw);
                    porcentaje_wallmart = (porcentaje_wallmart.charAt(0)=='.')? "0"+porcentaje_wallmart : porcentaje_wallmart;
                    porcentaje_wallmart += "% ⇩";
                    tv_precio_wallmart.setTextColor(Color.RED);
                }

                if(m<b){
                    double mb = b - m;
                    double porcentaje_mb = mb/porcentaje_b;
                    porcentaje_best_buy = formato_decimal.format(porcentaje_mb);
                    porcentaje_best_buy = (porcentaje_best_buy.charAt(0)=='.')? "0"+porcentaje_best_buy : porcentaje_best_buy;
                    porcentaje_best_buy += "% ⇧";
                    tv_precio_best_buy.setTextColor(Color.GREEN);
                }else{
                    double mb = m - b;
                    double porcentaje_mb = mb/porcentaje_b;
                    porcentaje_best_buy = formato_decimal.format(porcentaje_mb);
                    porcentaje_best_buy = (porcentaje_best_buy.charAt(0)=='.')? "0"+porcentaje_best_buy : porcentaje_best_buy;
                    porcentaje_best_buy += "% ⇩";
                    tv_precio_best_buy.setTextColor(Color.RED);
                }

                tv_precio_mabe.setText("$"+m);
                tv_precio_wallmart.setText("$"+w+"\n"+porcentaje_wallmart);
                tv_precio_best_buy.setText("$"+b+"\n"+porcentaje_best_buy);

                tr1.addView(tv_precio_mabe);
                tr1.addView(tv_precio_wallmart);
                tr1.addView(tv_precio_best_buy);

                trproductos.addView(tr1, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            }
        }catch(Exception e){
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("JMA", "errores----->"+e.getMessage());
        }
    }

}
