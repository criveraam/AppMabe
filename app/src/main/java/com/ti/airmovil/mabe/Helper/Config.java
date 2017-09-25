package com.ti.airmovil.mabe.Helper;

import com.android.volley.RequestQueue;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class Config {
    public static final String URL_GENERAL = "http://5piso.com/";
    public static final String URL_PRODUCTOS = URL_GENERAL + "~walmart/servicios/getProductosPadre.php";
    public static RequestQueue mRequestQueue = null;
}
