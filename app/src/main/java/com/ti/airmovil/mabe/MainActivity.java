package com.ti.airmovil.mabe;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ti.airmovil.mabe.Adapters.MyRecyclerAdapter;
import com.ti.airmovil.mabe.Adapters.ProductosAdapter;
import com.ti.airmovil.mabe.Helper.Config;
import com.ti.airmovil.mabe.Helper.InterfaceResults;
import com.ti.airmovil.mabe.Helper.NetworkController;
import com.ti.airmovil.mabe.Helper.VolleySingleton;
import com.ti.airmovil.mabe.Models.ProductosModel;
import com.ti.airmovil.mabe.Models.Test;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RequestQueue queue;
    private RecyclerView recyclerView;
    private List<Test> listTest = new ArrayList<Test>();
    private MyRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        adapter = new MyRecyclerAdapter(listTest, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);

        queue = NetworkController.getInstance(this).getRequestQueue();
        //Volley's inbuilt class to make Json array request
        JsonArrayRequest newsReq = new JsonArrayRequest(Config.URL_PRODUCTOS, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e(TAG, "consumo:::" + response);
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        Test feeds = new Test(obj.getString("titulo"), obj.getString("titulo"));
                        // adding movie to movies array
                        listTest.add(feeds);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    } finally {
                        //Notify adapter about data changes
                        adapter.notifyItemChanged(i);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        });
        queue.add(newsReq);
    }



}
