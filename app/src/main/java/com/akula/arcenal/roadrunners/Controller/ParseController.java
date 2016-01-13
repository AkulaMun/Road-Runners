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
import com.android.volley.toolbox.JsonObjectRequest;

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
    private String mParseAppId, mParseRESTAPIKey;

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
        mInstance.mParseAppId = givenId;
        mInstance.mParseRESTAPIKey = givenKey;
    }

    public void list(String dataType, final OnOperationCompleteListener listener){
        String url = "https://api.parse.com/1/classes/" + dataType;

        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
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
        }){
            @Override
            public Map<String, String> getHeaders(){
                return getParseAuthenticationHeaders();
            }
        };
        mRequestQueue.add(JSONrequest);
    }

    //Not Working At All. Suspect Request Body needs Overriding.
    public void search(String dataType, String searchData, String searchField, final OnOperationCompleteListener listener){
        String url = "https://api.parse.com/1/classes/" + dataType;

        final JSONObject searchParams = new JSONObject();
        final JSONObject requestBody = new JSONObject();
        try{
            searchParams.put(searchField, searchData);
            requestBody.put("where", searchParams);
            Log.e("Search", requestBody.toString());
        }
        catch(JSONException e){
            //ERROR HANDLING HERE
        }

        JsonObjectRequest JSONrequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
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
        }){
            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<>();
                params.put("where", searchParams.toString());
                return params;
            }
            @Override
            public Map<String, String> getHeaders(){
                return getParseAuthenticationHeaders();
            }
        };
        mRequestQueue.add(JSONrequest);
    }

    public void saveEvent(JSONObject eventJSON, boolean update, final OnOperationCompleteListener listener){
        String URL = "https://api.parse.com/1/classes/Event";
        if(update == false){
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, eventJSON, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onOperationComplete(response, null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError errors) {
                    listener.onOperationComplete(null, errors);
                }
            }){
                @Override
                public Map<String, String> getHeaders(){
                    return getParseAuthenticationHeaders();
                }
            };
            mRequestQueue.add(request);
        }
        else{
            String ID;
            if((ID = eventJSON.optString("id", null)) != null)
            {
                URL += "/" + ID;
            }
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.PUT, URL, eventJSON, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //TODO: Create Alert Box for Successful Save!
                    Log.e("Updating!", response.toString());
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError mErrors) {

                }
            }){
                @Override
                public Map<String, String> getHeaders(){
                    return getParseAuthenticationHeaders();
                }
            };
            mRequestQueue.add(request);
        }
    }

    public void deleteEvent(JSONObject eventJSON){

    }

    private Map<String, String> getParseAuthenticationHeaders(){
        Map<String,String> params = new HashMap<String, String>();
        params.put("X-Parse-Application-Id", mParseAppId);
        params.put("X-Parse-REST-API-Key", mParseRESTAPIKey);
        return params;
    }
}