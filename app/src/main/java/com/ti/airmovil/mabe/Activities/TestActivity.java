package com.ti.airmovil.mabe.Activities;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private TextView textViewPrecio, textViewDescripcion, textViewTitulo;
    private Toolbar toolbar;
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
        textViewPrecio = (TextView) findViewById(R.id.textView_precio);
        textViewTitulo = (TextView) findViewById(R.id.textView_titulo);
        toolbar = (Toolbar) findViewById(R.id.toolbar_detalle);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("id_producto")!= null)
            idProducto =bundle.getString("id_producto");

        getData();
        initCollapsingToolbar();

        imageViewProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager1 = getSupportFragmentManager();
                DialogoDetalle d1 = new DialogoDetalle();
                d1.show(fragmentManager1, "txt_login");
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
                params.put("id_crowler", "4");
                return params;
            }
        };
        queue.add(stringRequest);
    }

    private void parseResponse(String response){
        try {
            JSONObject obj = new JSONObject(response);

            String titulo = obj.getString("titulo");
            String descripcion = obj.getString("descripcion");
            String imagen = obj.getString("imagen");
            String precio = obj.getString("precio");

            textViewTitulo.setText(titulo);
            textViewDescripcion.setText(descripcion);
            textViewPrecio.setText(Config.nf.format(Double.parseDouble(precio)));
            Config.imagenDetalle = imagen;
            new DownloadImageTask((ImageView) imageViewProducto).execute(imagen);
        } catch (JSONException e) {
            Log.e(TAG, "errores");
            e.printStackTrace();
        }
    }

    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
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
