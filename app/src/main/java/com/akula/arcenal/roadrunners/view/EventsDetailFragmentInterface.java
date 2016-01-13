package com.akula.arcenal.roadrunners.view;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * Created by Arcenal on 13/1/2016.
 */

//Provides an interface for fragments that needs to handle the details of an event.
public abstract class EventsDetailFragmentInterface extends Fragment {
    abstract void pickDate(View v);
    abstract void pickTime(View v);
    abstract void setDate(int year, int monthOfYear, int dayOfMonth);
    abstract void setTime(int hourOfDay, int minute);
}
