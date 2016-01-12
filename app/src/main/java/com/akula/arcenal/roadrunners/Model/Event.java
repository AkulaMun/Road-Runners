package com.akula.arcenal.roadrunners.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

    public JSONObject JSONifyEvent(){
        final JSONObject eventJSON = new JSONObject();
        try{
            eventJSON.put("name", mName);
            eventJSON.put("location", mLocation);
            eventJSON.put("distance", mDistance);
            eventJSON.put("organizer", mOrganizer);

            SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String isoDateString = isoDateFormat.format(mDate);
            JSONObject dateDataObject = new JSONObject();
            dateDataObject.put("__type", "Date");
            dateDataObject.put("iso", isoDateString);
            eventJSON.put("date", dateDataObject);
        }
        catch(JSONException e){
            Log.e("JSON Failure", "Save data corrupted!");
        }
        return eventJSON;
    }

    public void setID(String givenID){
        mID = givenID;
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

    public void addParticipants(String participant){
        mParticipants.add(participant);
    }

    public ArrayList<String> getParticipants(){
        return mParticipants;
    }

        /*
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
    */
}
