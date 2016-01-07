package com.akula.arcenal.roadrunners.Model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class Event {
    private String mID, mName, mLocation, mOrganizer;
    private double mDistance = 0;
    private ArrayList<String> mParticipants;
    private Date mDate;

    public Event(String givenName, String givenLocation, String givenOrganizer, double givenDistance, Date givenDate){
        mName = givenName;
        mLocation = givenLocation;
        mDistance = givenDistance;
        mDate = givenDate;
        mOrganizer = givenOrganizer;
    }

    private void IDGenerate(){
        String distIDComponent;
        if(mDistance < 0){
            distIDComponent = "0" + Double.toString(mDistance);
        }
        else{
            distIDComponent = Double.toString(mDistance).substring(0, 2);
        }

        mID = mName.substring(0, 3) + mLocation.substring(0, 3) + mOrganizer.substring(0, 3) + "-" + mDate.toString() + distIDComponent;
    }

    public String getID(){
        return mID;
    }

    public void setName(String givenName){
        mName = givenName;
    }

    public String getName(){
        return mName;
    }

    public void setLocation(String givenLocation){
        mLocation = givenLocation;
    }

    public String getLocation(){
        return mLocation;
    }

    public void setDistance(double givenDistance){
        mDistance = givenDistance;
    }

    public double getDistance(){
        return mDistance;
    }

    public void setDate(Date givenDate){
        mDate = givenDate;
    }

    public Date getDate(){
        return mDate;
    }

    public void setOrganizer(String givenOrganizer){
        mOrganizer = givenOrganizer;
    }

    public String getOrganizer(){
        return mOrganizer;
    }

}
