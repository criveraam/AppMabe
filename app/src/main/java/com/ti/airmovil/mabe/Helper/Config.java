package com.ti.airmovil.mabe.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
    public static final String URL_REPORTE_PRODUCTOS = URL_GENERAL + "~walmart/servicios/getProductosamounts.php";
    public static final NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.getDefault());
    public static String imagenDetalle = "";
    public static RequestQueue mRequestQueue = null;

    public static boolean compruebaConexion(Context context) {
        boolean connected = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Recupera todas las redes (tanto móviles como wifi)
        NetworkInfo[] redes = connec.getAllNetworkInfo();

        for (int i = 0; i < redes.length; i++) {
            // Si alguna red tiene conexión, se devuelve true
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                connected = true;
            }
        }
        return connected;
    }
}
