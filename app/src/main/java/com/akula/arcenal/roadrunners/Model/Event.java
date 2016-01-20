package com.akula.arcenal.roadrunners.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
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

    public Event(String givenName, String givenLocation, String givenOrganizer, double givenDistance, Date givenDate) {
        mName = givenName;
        mLocation = givenLocation;
        mDistance = givenDistance;
        mDate = givenDate;
        mOrganizer = givenOrganizer;
    }

    public Event(JSONObject event) {
        mName = event.optString("name");
        mLocation = event.optString("location");
        mOrganizer = event.optString("organizer");
        mDistance = event.optDouble("distance");
        mDate = dateFormat((JSONObject) event.opt("date"));
    }

    public JSONObject convertTOJSON() {
        final JSONObject eventJSON = new JSONObject();
        try {
                if (mID != null) {
                    eventJSON.put("id", mID);
                }
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
        } catch (JSONException e) {
            Log.e("JSON Conversion Failed", e.getMessage());
        }
        return eventJSON;
    }

    public Date dateFormat(JSONObject dateObject) {
        Date resultDate = null;
        SimpleDateFormat ISOdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            String dateString = dateObject.getString("iso");
            dateString = dateString.replace("T", " ");
            resultDate = ISOdateFormat.parse(dateString);
        } catch (Exception e) {
            Log.e("Date Parse Failure", e.getMessage());
        }
        return resultDate;
    }

    public String getDateAsString() {
        SimpleDateFormat isoDateFormat = new SimpleDateFormat("EEE d/MMMM/yyyy  hh:mma ZZZZ");
        return isoDateFormat.format(mDate);
    }

    public String getDateAsString(String field) {
        if(field.equals("date")) {
            SimpleDateFormat isoDateFormat = new SimpleDateFormat("EEE d/MMMM/yyyy");
            return isoDateFormat.format(mDate);
        }
        if (field.equals("time")) {
            SimpleDateFormat isoDateFormat = new SimpleDateFormat("hh:mma ZZZZ");
            return isoDateFormat.format(mDate);
        }
        return "ERROR";
    }

    public void setID(String givenID) {
        mID = givenID;
    }

    public String getID() {
        return mID;
    }

    public void setName(String givenName) {
        mName = givenName;
    }

    public String getName() {
        return mName;
    }

    public void setLocation(String givenLocation) {
        mLocation = givenLocation;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setDistance(double givenDistance) {
        mDistance = givenDistance;
    }

    public double getDistance() {
        return mDistance;
    }

    public void setDate(Date givenDate) {
        mDate = givenDate;
    }

    public Date getDate() {
        return mDate;
    }

    public void setOrganizer(String givenOrganizer) {
        mOrganizer = givenOrganizer;
    }

    public String getOrganizer() {
        return mOrganizer;
    }

    public void addParticipants(String participant) {
        mParticipants.add(participant);
    }

    public ArrayList<String> getParticipants() {
        return mParticipants;
    }
}
