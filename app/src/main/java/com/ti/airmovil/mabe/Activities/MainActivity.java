package com.ti.airmovil.mabe.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Rect;
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
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ti.airmovil.mabe.Adapters.ProductosAdapter;
import com.ti.airmovil.mabe.Helper.Config;
import com.ti.airmovil.mabe.Models.ProductosModel;
import com.ti.airmovil.mabe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private List<ProductosModel> getDatos1;
    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private RequestQueue requestQueue;
    private ProgressBar progressBar;
    private Toolbar toolbar;
    private LinearLayout capa1;
    private RelativeLayout capa2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        getDatos1 = new ArrayList<>();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        capa1 = (LinearLayout) findViewById(R.id.linear_layout_capa1);
        capa2 = (RelativeLayout) findViewById(R.id.linear_layout_capa2);
        layoutManager = new LinearLayoutManager(this);



        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        columns(2);
        initService();

        Button btnRefresh = (Button) findViewById(R.id.button_refrescar);
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initService();
            }
        });


    }

    private void initService(){
        capa1.setVisibility(View.VISIBLE);
        if (Config.compruebaConexion(MainActivity.this)) {
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
                            Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_up);
                            animation.setDuration(1000);
                            capa2.setAnimation(animation);
                            capa2.animate();
                            animation.start();
                        }
                    });
                }
            };
            thread.start();
        }
    }

    private void test(){
        adapter = new ProductosAdapter(getApplicationContext(), getDatos1, recyclerView);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void columns(int val){

        layoutManager = new LinearLayoutManager(this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, val);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(val, dpToPx(0), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
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
                    collapsingToolbar.setTitle(getString(R.string.app_name));
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
        StringRequest postRequest = new StringRequest(Request.Method.POST, Config.URL_PRODUCTOS,
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
                                        capa1.setVisibility(View.GONE);
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
                                        capa2.setVisibility(View.VISIBLE);
                                        capa1.setVisibility(View.GONE);
                                        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.anim_up);
                                        animation.setDuration(1000);
                                        capa2.setAnimation(animation);
                                        capa2.animate();
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
        Log.e(TAG, "RESPONSE::: " + rqt);
        try {
            JSONArray obj = new JSONArray(rqt);
            for (int i = 0; i < obj.length(); i++){
                JSONObject jsonObject=null;
                ProductosModel getDatos2 = new ProductosModel();
                jsonObject = obj.getJSONObject(i);
                try{
                    getDatos2.setIdProducto(jsonObject.getString("id_crawler"));
                    getDatos2.setNombre(jsonObject.getString("titulo"));
                    getDatos2.setPrecio(jsonObject.getString("precio"));
                    getDatos2.setImagen(jsonObject.getString("imagen"));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                getDatos1.add(getDatos2);
            }
            Log.e(TAG, "Conversion a JSONObjet::: " + obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapter = new ProductosAdapter(getApplicationContext(), getDatos1, recyclerView);
        //recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration{
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column
            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option, menu);
        /*menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {}

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        menu.getItem(0).setVisible(true);
                        menu.getItem(1).setVisible(true);
                        menu.getItem(2).setVisible(true);
                    }
                });
            }
        };
        thread.start(); //start the thread*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_reportes:
                startActivity(new Intent().setClass(MainActivity.this, ReporteProductosActivity.class));
                return true;
            case R.id.menu_filtro:
                startActivity(new Intent().setClass(MainActivity.this, FiltroActivity.class));
                return true;
            case R.id.menu_columnas:
                columns(2);
                return true;
            case R.id.menu_lista:
                columns(1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
