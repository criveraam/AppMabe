package com.ti.airmovil.mabe.Activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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

    private TableLayout tl_productos;
    private TableRow tr_productos, tr_precios_mabe, tr_precios_walmart, tr_precios_best_buy;
    private Button btn_aplicar_hora;
    private int indice_hora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_2);
        tl_productos = (TableLayout) findViewById(R.id.tlproductos_reporte);
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
                tr_productos.removeAllViews();
                tr_precios_mabe.removeAllViews();
                tr_precios_walmart.removeAllViews();
                tr_precios_best_buy.removeAllViews();
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

    @SuppressLint("WrongViewCast")
    private void generarTabla(String response){
        try{
            JSONArray objArray = new JSONArray(response);
            ContextThemeWrapper wrappedContext = new ContextThemeWrapper(this, R.style.celda_table);

            tr_productos = (TableRow) findViewById(R.id.tr_productos);
            tr_precios_mabe = (TableRow) findViewById(R.id.tr_precios_mabe);
            tr_precios_walmart = (TableRow) findViewById(R.id.tr_precios_walmart);
            tr_precios_best_buy = (TableRow) findViewById(R.id.tr_precios_best_buy);

            LinearLayout ll_mabe = new LinearLayout(this,null, R.style.contenedor_encabezado_iconos);
            //ll_mabe.setOrientation(LinearLayout.VERTICAL);
            ll_mabe.getLayoutParams().width = 100;
            //ll_mabe.getLayoutParams().height = 50;


            LinearLayout ll_walmart = new LinearLayout(this,null, R.style.contenedor_encabezado_iconos);
            LinearLayout ll_best_buy = new LinearLayout(this,null, R.style.contenedor_encabezado_iconos);

            TextView encabezado_nombre = new TextView(new ContextThemeWrapper(this, R.style.encabezado_producto),null,0);

            ImageView encabezado_mabe = new ImageView(this);
            encabezado_mabe.setImageResource(R.drawable.mabe_png);

            encabezado_mabe.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));

            TextView encabezado_walmart = new TextView(new ContextThemeWrapper(this, R.style.encabezado_producto),null,0);
            TextView encabezado_best_buy = new TextView(new ContextThemeWrapper(this, R.style.encabezado_producto),null,0);

            ll_mabe.addView(encabezado_mabe);
            ll_walmart.addView(encabezado_walmart);
            ll_best_buy.addView(encabezado_best_buy);

            tr_productos.addView(encabezado_nombre);
            tr_precios_mabe.addView(ll_mabe);
            tr_precios_walmart.addView(ll_walmart);
            tr_precios_best_buy.addView(ll_best_buy);


            for(int j=0; j<objArray.length(); j++){
                JSONObject json_producto = objArray.getJSONObject(j);
                JSONArray precios_mabe = json_producto.getJSONArray(String.valueOf(6));
                JSONArray precios_wallmart = json_producto.getJSONArray(String.valueOf(7));
                JSONArray precios_best_buy = json_producto.getJSONArray(String.valueOf(8));



                TextView nombre_producto = new TextView(wrappedContext, null, 0);
                nombre_producto.setText(json_producto.getString(String.valueOf(4)));
                nombre_producto.setMaxWidth(300);
                nombre_producto.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
                tr_productos.addView(nombre_producto);

                TextView precio_mabe = new TextView(wrappedContext, null, 0);
                precio_mabe.setText(precios_mabe.getString(indice_hora));
                precio_mabe.setMaxWidth(300);

                TextView precio_walmart = new TextView(wrappedContext, null, 0);
                precio_walmart.setText(precios_wallmart.getString(indice_hora));
                precio_walmart.setMaxWidth(300);

                TextView precio_best_buy = new TextView(wrappedContext, null, 0);
                precio_best_buy.setText(precios_best_buy.getString(indice_hora));
                precio_best_buy.setMaxWidth(300);

                double m = precios_mabe.getDouble(indice_hora);
                double w = precios_wallmart.getDouble(indice_hora);
                double b = precios_best_buy.getDouble(indice_hora);
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
                    precio_walmart.setTextColor(Color.GREEN);
                }else{
                    double mw = m - w;
                    double porcentaje_mw = mw/porcentaje_w;
                    porcentaje_wallmart = formato_decimal.format(porcentaje_mw);
                    porcentaje_wallmart = (porcentaje_wallmart.charAt(0)=='.')? "0"+porcentaje_wallmart : porcentaje_wallmart;
                    porcentaje_wallmart += "% ⇩";
                    precio_walmart.setTextColor(Color.RED);
                }

                if(m<b){
                    double mb = b - m;
                    double porcentaje_mb = mb/porcentaje_b;
                    porcentaje_best_buy = formato_decimal.format(porcentaje_mb);
                    porcentaje_best_buy = (porcentaje_best_buy.charAt(0)=='.')? "0"+porcentaje_best_buy : porcentaje_best_buy;
                    porcentaje_best_buy += "% ⇧";
                    precio_best_buy.setTextColor(Color.GREEN);
                }else{
                    double mb = m - b;
                    double porcentaje_mb = mb/porcentaje_b;
                    porcentaje_best_buy = formato_decimal.format(porcentaje_mb);
                    porcentaje_best_buy = (porcentaje_best_buy.charAt(0)=='.')? "0"+porcentaje_best_buy : porcentaje_best_buy;
                    porcentaje_best_buy += "% ⇩";
                    precio_best_buy.setTextColor(Color.RED);
                }

                precio_mabe.setText("$"+m);
                precio_walmart.setText("$"+w+"\n"+porcentaje_wallmart);
                precio_best_buy.setText("$"+b+"\n"+porcentaje_best_buy);

                tr_precios_mabe.addView(precio_mabe);
                tr_precios_walmart.addView(precio_walmart);
                tr_precios_best_buy.addView(precio_best_buy);

            }
        }catch(Exception e){
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("JMA", "errores----->"+e.getMessage());
        }
    }

}
