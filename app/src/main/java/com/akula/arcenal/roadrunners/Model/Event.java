package com.akula.arcenal.roadrunners.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class Event implements Parcelable{
    private String mID;
    private String mName;
    private String mLocation;
    private String mOrganizer;
    private double mDistance = 0;
    private ArrayList<String> mParticipants;
    private Date mDate;

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {

        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[0];
        }
    };

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

    private Event(Parcel in) {
        String name = in.readString();
        String location = in.readString();
        String organizer = in.readString();
        double distance = in.readDouble();
        Date date = new Date();
        date.setTime(in.readLong());
        String id;
        if( (id = in.readString() ) != null) {
            mID = id;
        }
        mName = name;
        mLocation = location;
        mOrganizer = organizer;
        mDistance = distance;
        mDate = date;
    }

    public void setID(String givenID) {
        mID = givenID;
    }

    public String getID() {
        return mID;
    }

    public String getName() {
        return mName;
    }

    public String getLocation() {
        return mLocation;
    }

    public double getDistance() {
        return mDistance;
    }

    public Date getDate() {
        return mDate;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeString(mLocation);
        dest.writeString(mOrganizer);
        dest.writeDouble(mDistance);
        dest.writeLong(mDate.getTime());

        if (mID != null) {
            dest.writeString(mID);
        }
    }
}
