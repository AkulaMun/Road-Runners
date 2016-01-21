package com.akula.arcenal.roadrunners.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arcenal on 13/1/2016.
 */
public class ParseRequest extends JsonObjectRequest {
    public ParseRequest(int method, String url, JSONObject requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public ParseRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<>();
        params.put("X-Parse-Application-Id", "X92n2Y7bH8mB3sjTDSe6vrNMIfYVEIrSiipWSiO2");
        params.put("X-Parse-REST-API-Key", "qVwtXXbkHkyLfEwcf41jCnvESafpxQ553CmgTzYK");
        return params;
    }
}
