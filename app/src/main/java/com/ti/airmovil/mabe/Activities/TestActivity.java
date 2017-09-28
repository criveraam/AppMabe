package com.ti.airmovil.mabe.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ti.airmovil.mabe.Adapters.ProductosAdapter;
import com.ti.airmovil.mabe.Dialog.DialogoDetalle;
import com.ti.airmovil.mabe.Helper.Config;
import com.ti.airmovil.mabe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestActivity extends AppCompatActivity {
    private static final String TAG = TestActivity.class.getSimpleName();
    private RequestQueue requestQueue;
    private String idProducto;
    private String imageEnvio;
    private ImageView imageViewProducto;
    private TextView textViewTitulo1, textViewTitulo2, textViewTitulo3;
    private ImageView imageViewIcon1, imageViewIcon2, imageViewIcon3;
    private TextView textViewPrecio1, textViewPrecio2,textViewPrecio3, textViewDescripcion, textViewTitulo;
    private Toolbar toolbar;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;
    private LinearLayout linearLayoutInfo;
    private Button buttonCerrar;
    boolean banderaFoto = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        setContentView(R.layout.activity_test);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        //toolbar = (Toolbar) findViewById(R.id.toolbar);
        imageViewProducto = (ImageView) findViewById(R.id.imageView_producto);
        textViewDescripcion = (TextView) findViewById(R.id.textView_descripcion);
        textViewPrecio1 = (TextView) findViewById(R.id.textView_precio1);
        textViewPrecio2 = (TextView) findViewById(R.id.textView_precio2);
        textViewPrecio3 = (TextView) findViewById(R.id.textView_precio3);
        textViewTitulo1 = (TextView) findViewById(R.id.textView_tienda1);
        textViewTitulo2 = (TextView) findViewById(R.id.textView_tienda2);
        textViewTitulo3 = (TextView) findViewById(R.id.textView_tienda3);
        imageViewIcon1 = (ImageView) findViewById(R.id.imageView_icon1);
        imageViewIcon2 = (ImageView) findViewById(R.id.imageView_icon2);
        imageViewIcon3 = (ImageView) findViewById(R.id.imageView_icon3);
        textViewTitulo = (TextView) findViewById(R.id.textView_titulo);
        buttonCerrar = (Button) findViewById(R.id.button_regresar);
        toolbar = (Toolbar) findViewById(R.id.toolbar_detalle);
        linearLayoutInfo = (LinearLayout) findViewById(R.id.linearlayout_info);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("id_producto")!= null){
            idProducto = bundle.getString("id_producto");
            Log.e(TAG, "Parametro obtenido del envio a esta actividad ::: " + idProducto);
        }

        getData();
        initCollapsingToolbar();

        linearLayoutInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appBarLayout.setExpanded(false);
            }
        });

        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnActivity();
            }
        });


    }

    private void getData(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.URL_DETALLE_PRODUCTO,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        parseResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error:::" + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id_crowler", idProducto);
                return params;
            }
        };
        queue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            returnActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void returnActivity(){
        Intent i= new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    private void parseResponse(String response){
        try {
            JSONObject obj = new JSONObject(response);

            String titulo = obj.getString("titulo");
            String descripcion = obj.getString("descripcion");
            String imagen = obj.getString("imagen");
            String precio_mabe = obj.getString("precio_mabe");
            String precio_walmart = obj.getString("precio_walmart");
            String precio_bestbuy = obj.getString("precio_bestbuy");

            textViewTitulo.setText(titulo);
            textViewDescripcion.setText(descripcion);
            textViewPrecio1.setText(Config.nf.format(Double.parseDouble(precio_mabe)));
            textViewPrecio2.setText(Config.nf.format(Double.parseDouble(precio_walmart)));
            textViewPrecio3.setText(Config.nf.format(Double.parseDouble(precio_bestbuy)));

            //Config.imagenDetalle = imagen;
            Log.e(TAG, "IMAGEN A MOSTRAT EN COLLAPSING::: " + imagen);
            new DownloadImageTask((ImageView) imageViewProducto).execute(imagen);
        } catch (JSONException e) {
            Log.e(TAG, "errores");
            e.printStackTrace();
        }
    }

    private void initCollapsingToolbar(){
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}
