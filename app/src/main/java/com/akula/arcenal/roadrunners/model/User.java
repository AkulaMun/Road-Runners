package com.akula.arcenal.roadrunners.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Arcenal on 13/1/2016.
 */
public class User {
    private String mName, mPassword;

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public JSONObject convertToJSON(){
        try {
            return new JSONObject()
                    .put("username", mName)
                    .put("password", mPassword);
        } catch (JSONException e) {
            Log.e("JSON Conversion Failed", e.getMessage());
            return null;
        }
    }
}
