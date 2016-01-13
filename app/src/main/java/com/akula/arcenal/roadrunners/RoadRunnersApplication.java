package com.akula.arcenal.roadrunners;

import android.app.Application;

import com.akula.arcenal.roadrunners.controller.ParseController;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class RoadRunnersApplication extends android.app.Application {
    //Code that can only be ran once per life placed here!

    @Override
    public void onCreate() {
        super.onCreate();
        //This will only be called once in your app's entire lifecycle.
        ParseController.initialize(getApplicationContext(), "X92n2Y7bH8mB3sjTDSe6vrNMIfYVEIrSiipWSiO2", "qVwtXXbkHkyLfEwcf41jCnvESafpxQ553CmgTzYK");
    }
}
