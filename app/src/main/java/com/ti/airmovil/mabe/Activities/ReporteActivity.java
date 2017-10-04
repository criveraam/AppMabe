package com.ti.airmovil.mabe.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class ReporteActivity extends AppCompatActivity {

    private TableLayout trproductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reporte);
        trproductos = (TableLayout) findViewById(R.id.trproductos);
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
            JSONObject json_producto = objArray.getJSONObject(0);
            JSONArray horas = json_producto.getJSONArray(String.valueOf(5));
            ContextThemeWrapper wrappedContext = new ContextThemeWrapper(this, R.style.encabezado);
            TableRow trEncabezado = new TableRow(this);

/*********************/
            /* COLUMNA DE ENCABEZADO NOMBRE PRODUCTO */
            TextView encabezado = new TextView(wrappedContext, null, 0);
            encabezado.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            encabezado.setText("Producto");
            trEncabezado.addView(encabezado);
            /* COLUMNA DE COMPETENCIA */
            TextView competencia = new TextView(wrappedContext, null, 0);
            competencia.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            competencia.setText("Competencia");
            trEncabezado.addView(competencia);
            for(int j=12; j<18; j++){
                encabezado = new TextView(wrappedContext, null, 0);
                encabezado.setText(horas.get(j).toString()+":00");
                trEncabezado.addView(encabezado);
            }
            trproductos.addView(trEncabezado, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
/*********************/
            for(int j=0; j<objArray.length(); j++){
                json_producto = objArray.getJSONObject(j);
                JSONArray precio_mabe = json_producto.getJSONArray(String.valueOf(6));
                JSONArray precio_wallmart = json_producto.getJSONArray(String.valueOf(7));
                JSONArray precio_best_buy = json_producto.getJSONArray(String.valueOf(8));
                TableRow tr1 = new TableRow(this);
                tr1.setLayoutParams(new ViewGroup.LayoutParams( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                if(j%2==0){
                    wrappedContext = new ContextThemeWrapper(this, R.style.celda_table);
                    tr1.setBackgroundResource(R.color.cardview_light_background);
                }else{
                    wrappedContext = new ContextThemeWrapper(this, R.style.celda_table_2);
                    tr1.setBackgroundResource(R.color.azulFuerte);
                }
                /* CELDA DEL NOMBRE DEL PRODUCTO */
                TextView nombre_producto = new TextView(wrappedContext, null, 0);
                nombre_producto.setText(json_producto.getString(String.valueOf(4)));
                nombre_producto.setMaxWidth(300);
                tr1.addView(nombre_producto);
                /* CELDA DEL NOMBRE DE LA COMPETENCIA */
                TextView nombre_competencia = new TextView(wrappedContext, null, 0);
                nombre_competencia.setText("Wallmart\nBest Buy");
                nombre_competencia.setMaxWidth(100);
                nombre_competencia.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                tr1.addView(nombre_competencia);
                for(int k=12; k<18; k++){
                    TextView precio = new TextView(wrappedContext, null, 0);
                    String texto = "";
                    double m = precio_mabe.getDouble(k);
                    double w = precio_wallmart.getDouble(k);
                    double b = precio_best_buy.getDouble(k);
                    double porcentaje_w = w/100;
                    double porcentaje_b = b/100;
                    DecimalFormat formato_decimal = new DecimalFormat(".00");
                    String porcentaje = "";
                    if(m<w){
                        double mw = w - m;
                        double porcentaje_mw = mw/porcentaje_w;
                        porcentaje = formato_decimal.format(porcentaje_mw);
                        porcentaje = (porcentaje.charAt(0)=='.')? "0"+porcentaje : porcentaje;
                        texto = porcentaje+"% ⇩";
                    }else{
                        double mw = m - w;
                        double porcentaje_mw = mw/porcentaje_w;
                        porcentaje = formato_decimal.format(porcentaje_mw);
                        porcentaje = (porcentaje.charAt(0)=='.')? "0"+porcentaje : porcentaje;
                        texto = porcentaje+"% ⇧";
                    }
                    texto += (texto!="")? "\n":"";
                    if(m<b){
                        double mb = b - m;
                        double porcentaje_mb = mb/porcentaje_b;
                        porcentaje = formato_decimal.format(porcentaje_mb);
                        porcentaje = (porcentaje.charAt(0)=='.')? "0"+porcentaje : porcentaje;
                        texto += porcentaje+"% ⇩";
                    }else{
                        double mb = m - b;
                        double porcentaje_mb = mb/porcentaje_b;
                        porcentaje = formato_decimal.format(porcentaje_mb);
                        porcentaje = (porcentaje.charAt(0)=='.')? "0"+porcentaje : porcentaje;
                        texto += porcentaje+"% ⇧";
                    }
                    precio.setText(texto+" ");
                    tr1.addView(precio);
                }
                trproductos.addView(tr1, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        }catch(Exception e){
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("JMA", "errores----->"+e.getMessage());
        }
    }
}
