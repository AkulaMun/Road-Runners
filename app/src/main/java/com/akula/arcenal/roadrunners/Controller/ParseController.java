package com.akula.arcenal.roadrunners.Controller;
import android.content.Context;
import android.util.Log;

import com.akula.arcenal.roadrunners.Model.Event;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class ParseController {
    public interface OnReadCompleteListener {
        public void onReadComplete(JSONObject resultObject, Exception ex);
    }

    private static ParseController mInstance = null;
    private Context mContext;
    private RequestQueue mRequestQueue;

    private ParseController(Context givenContext){
        mContext = givenContext;

        Cache mCache = new DiskBasedCache(mContext.getCacheDir(), 1024*1024);
        Network mNetwork = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(mCache, mNetwork);

        mRequestQueue.start();

        mInstance = this;
    }

    public static ParseController getInstance(Context givenContext){
        if(mInstance != null){
            return mInstance;
        }
        else{
            return new ParseController(givenContext);
        }
    }

    //DO NOT USE THIS FOR USER DATA!
    public void list(String dataType, final OnReadCompleteListener listener){
        String url = "https://api.parse.com/1/classes/" + dataType;

        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onReadComplete(response, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError mErrors) {
                if (listener != null) {
                    listener.onReadComplete(null, mErrors);
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", "X92n2Y7bH8mB3sjTDSe6vrNMIfYVEIrSiipWSiO2");
                params.put("X-Parse-REST-API-Key", "qVwtXXbkHkyLfEwcf41jCnvESafpxQ553CmgTzYK");
                return params;
            }
        };
        mRequestQueue.add(JSONrequest);
    }

    //DO NOT USE THIS FOR USER DATA!
    public void search(String dataType, String searchData, String searchField, final OnReadCompleteListener listener){
        String url = "https://api.parse.com/1/classes/" + dataType;

        final JSONObject searchParams = new JSONObject();
        try{
            searchParams.put(searchField, searchData);
        }
        catch(JSONException e){
            //ERROR HANDLING HERE
        }

        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onReadComplete(response, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError mErrors) {
                if (listener != null) {
                    listener.onReadComplete(null, mErrors);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("where", searchParams.toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("X-Parse-Application-Id", "X92n2Y7bH8mB3sjTDSe6vrNMIfYVEIrSiipWSiO2");
                params.put("X-Parse-REST-API-Key", "qVwtXXbkHkyLfEwcf41jCnvESafpxQ553CmgTzYK");
                return params;
            }
        };
        mRequestQueue.add(JSONrequest);
    }

    public void saveEvent(JSONObject eventJSON){
        String URL = "https://api.parse.com/1/classes/Event";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, eventJSON, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: Create Alert Box for Successful Save!
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError mErrors) {

            }
        }){
            @Override
            public Map<String, String> getHeaders(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                params.put("X-Parse-Application-Id", "X92n2Y7bH8mB3sjTDSe6vrNMIfYVEIrSiipWSiO2");
                params.put("X-Parse-REST-API-Key", "qVwtXXbkHkyLfEwcf41jCnvESafpxQ553CmgTzYK");
                return params;
            }
        };

        try{Log.e("Request URL: ", request.getBody().toString());}catch(Exception e){}
        mRequestQueue.add(request);
    }
}
