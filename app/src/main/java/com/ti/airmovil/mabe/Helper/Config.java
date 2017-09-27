package com.ti.airmovil.mabe.Helper;

import com.android.volley.RequestQueue;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public class Config {
    public static final String URL_GENERAL = "http://5piso.com/";
    public static final String URL_PRODUCTOS = URL_GENERAL + "~walmart/servicios/getProductosPadre.php";
    public static final String URL_DETALLE_PRODUCTO = URL_GENERAL + "~walmart/servicios/getProductoDetalle.php";
    public static final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
    public static String imagenDetalle = "";
    public static RequestQueue mRequestQueue = null;
}
