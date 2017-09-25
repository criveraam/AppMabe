package com.ti.airmovil.mabe.Helper;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by tecnicoairmovil on 25/09/17.
 */

public interface InterfaceResults {
    public void notifySuccess(String requestType,JSONObject response);
    public void notifyError(String requestType,VolleyError error);
}
