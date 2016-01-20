package com.akula.arcenal.roadrunners.controller;

import android.content.Context;

import com.akula.arcenal.roadrunners.api.ParseRequest;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONObject;

/**
 * Created by Arcenal on 19/1/2016.
 */
public class UserController {
    public interface OnUserOperationCompleteListener{
        void OnComplete(String message, Exception error);
    }

    private static UserController sInstance;
    private RequestQueue mRequestQueue;
    private String userURL = "https://api.parse.com/1/users";

    public static UserController getInstance(Context context){
        if(sInstance == null) {
            sInstance = new UserController(context);
        }
        return sInstance;
    }

    private UserController(Context context) {
        Cache mCache = new DiskBasedCache(context.getCacheDir(), 512*1024);
        Network mNetwork = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(mCache, mNetwork);
        mRequestQueue.start ();
    }

    public void signUp(JSONObject user, final OnUserOperationCompleteListener listener){
        ParseRequest signUpRequest = new ParseRequest(Request.Method.POST, userURL, user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.OnComplete("User Registered!", null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.OnComplete(null, error);
            }
        });
    }

    public void login(JSONObject user, final OnUserOperationCompleteListener listener){
        ParseRequest signUpRequest = new ParseRequest(Request.Method.POST, userURL, user, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listener.OnComplete("Login Successful!", null);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.OnComplete(null, error);
            }
        });
    }


}
