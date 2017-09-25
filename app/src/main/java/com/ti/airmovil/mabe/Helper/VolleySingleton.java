package com.ti.airmovil.mabe.Helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class VolleySingleton {
    private InterfaceResults mResultCallback = null;
    private Context mContext;

    private static VolleySingleton mInstance;
    private RequestQueue mRequestQueue;

    /**
     * metodo para generar la peticion REST
     * @param requestType tipo de respuesta
     * @param url direccion del servicio
     * @param sendObj objeto json
     */
    public void postDataVolley(final String requestType, String url,JSONObject sendObj){
        try {
            JsonObjectRequest jsonObj = new JsonObjectRequest(url,sendObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(requestType,response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType,error);
                }
            });
            addToRequestQueue(jsonObj);
        }catch(Exception e){
            Log.d("ERROR:", e.toString());
        }
    }

    /**
     * Peticion get por get
     * @param requestType
     * @param url
     */
    public void getDataVolley(final String requestType, String url){
        try {

            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if(mResultCallback != null)
                        mResultCallback.notifySuccess(requestType, response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    if(mResultCallback != null)
                        mResultCallback.notifyError(requestType, error);
                }
            });
            addToRequestQueue(jsonObj);
        }catch(Exception e){
            Log.d("ERROR:", e.toString());
        }
    }

    /**
     * singleton
     * @param resultCallback interfaz
     * @param context contexto
     */
    private VolleySingleton(InterfaceResults resultCallback,Context context) {
        mResultCallback = resultCallback;
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    /**
     * Sincronizacion con el mInstance
     * @param resultCallback interfaz
     * @param context contexto de referencia
     * @return
     */
    public static synchronized VolleySingleton getInstance(InterfaceResults resultCallback, Context context) {
        mInstance = new VolleySingleton(resultCallback, context);
        return mInstance;
    }

    /**
     * llama la interfaz
     * @param resultCallback
     */
    public void setCallback(InterfaceResults resultCallback){
        this.mResultCallback = resultCallback;
    }

    /**
     * Coloca el contexto
     * @param context
     */
    public void setContext(Context context){
        this.mContext = context;
    }

    /**
     * @return cola de peticiones
     */
    public RequestQueue getRequestQueue() {
        if (Config.mRequestQueue == null) {
            Config.mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return Config.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
