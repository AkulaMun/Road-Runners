package com.akula.arcenal.roadrunners.controller;

import android.content.Context;
import android.util.Log;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class ParseController {
    public interface OnOperationCompleteListener {
        void onOperationComplete(JSONObject resultObject, Exception ex);
    }

    private static ParseController mInstance = null;
    private RequestQueue mRequestQueue;

    private String eventURL = "https://api.parse.com/1/classes/Event";

    private ParseController(Context givenContext){
        Cache mCache = new DiskBasedCache(givenContext.getCacheDir(), 1024*1024);
        Network mNetwork = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(mCache, mNetwork);
        mRequestQueue.start();
        mInstance = this;
    }

    public static ParseController getInstance(){
            return mInstance;
    }

    public static void initialize(Context givenContext, String givenId, String givenKey){
        mInstance = new ParseController(givenContext);
        ParseRequest.setKeys(givenId, givenKey);
    }

    public void list(String dataType, final OnOperationCompleteListener listener){
        String url = "https://api.parse.com/1/classes/" + dataType;

        ParseRequest JSONrequest = new ParseRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onOperationComplete(response, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError mErrors) {
                if (listener != null) {
                    listener.onOperationComplete(null, mErrors);
                }
            }
        });
        mRequestQueue.add(JSONrequest);
    }

    //Not Working At All. Suspect Request Body needs Overriding.
    public void search(String dataType, String searchData, String searchField, final OnOperationCompleteListener listener){
        String url = "https://api.parse.com/1/classes/" + dataType;

        final JSONObject searchParams = new JSONObject();
        //final Map<String, JSONObject> requestBody = new HashMap<>();
        final JSONObject requestBody = new JSONObject();
        try{
            searchParams.put(searchField, searchData);
            requestBody.put("where", searchParams);
            Log.e("Search", requestBody.toString());
        }
        catch(JSONException e){
            //ERROR HANDLING HERE
        }
        ParseRequest JSONrequest = new ParseRequest(Request.Method.GET, url, requestBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response Search:", response.toString());
                if (listener != null) {
                    listener.onOperationComplete(response, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError mErrors) {
                if (listener != null) {
                    listener.onOperationComplete(null, mErrors);
                }
            }
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("where", searchParams.toString());
                return params;
            }};
        mRequestQueue.add(JSONrequest);
    }

    public void saveEvent(JSONObject eventJSON, boolean update, final OnOperationCompleteListener listener){
        if(update == false){
            ParseRequest request = new ParseRequest(Request.Method.POST, eventURL, eventJSON, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onOperationComplete(response, null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError errors) {
                    listener.onOperationComplete(null, errors);
                }
            });
            mRequestQueue.add(request);
        }
        else{
            String ID;
            String URL = eventURL;
            if((ID = eventJSON.optString("id", null)) != null)
            {
                URL += "/" + ID;
            }
            ParseRequest request = new ParseRequest(Request.Method.PUT, URL, eventJSON, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onOperationComplete(response, null);
                    Log.e("Response", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError errors) {
                    listener.onOperationComplete(null, errors);
                }
            });
            mRequestQueue.add(request);
        }
    }

    public void deleteEvent(JSONObject eventJSON, final OnOperationCompleteListener listener){
        String ID;
        String URL = eventURL;
        if((ID = eventJSON.optString("id", null)) != null)
        {
            URL += "/" + ID;
        }
        ParseRequest request = new ParseRequest(Request.Method.DELETE, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.onOperationComplete(response, null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errors) {
                listener.onOperationComplete(null, errors);
            }
        });
        mRequestQueue.add(request);
    }
}