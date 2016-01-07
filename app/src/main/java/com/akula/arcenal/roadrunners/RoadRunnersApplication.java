package com.akula.arcenal.roadrunners;

import com.parse.Parse;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class RoadRunnersApplication extends android.app.Application {
    //Code that can only be ran once per life placed here!

    @Override
    public void onCreate() {
        super.onCreate();
        //This will only be called once in your app's entire lifecycle.
        Parse.initialize(this);
    }
}
