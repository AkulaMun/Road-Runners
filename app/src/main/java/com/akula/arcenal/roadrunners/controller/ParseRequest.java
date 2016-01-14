package com.akula.arcenal.roadrunners.controller;

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
    private static String sParseAppId, sParseRESTAPIKey;

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String,String> params = new HashMap<String, String>();
        params.put("X-Parse-Application-Id", sParseAppId);
        params.put("X-Parse-REST-API-Key", sParseRESTAPIKey);
        return params;
    }

    public static void setKeys(String AppKey, String APIKey){
        sParseAppId = AppKey;
        sParseRESTAPIKey = APIKey;
    }

    public ParseRequest(int method, String url, JSONObject requestBody, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public ParseRequest(int method, String url, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, listener, errorListener);
    }
}
