package com.akula.arcenal.roadrunners.model;

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

    public void login(){
        //Call Parse Controller and Login with name and password
    }
}
