package com.akula.arcenal.roadrunners.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
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

    public Event(JSONObject event){
        mName = event.optString("name");
        mLocation = event.optString("location");
        mOrganizer = event.optString("organizer");
        mDistance = event.optDouble("distance");
        mDate = dateFormat((JSONObject) event.opt("date"));
    }

    public JSONObject JSONifyEvent(){
        final JSONObject eventJSON = new JSONObject();
        try{
            if(mID != null){
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
        }
        catch(JSONException e){
            Log.e("JSON Failure", "Save data corrupted!");
        }
        return eventJSON;
    }


    public Date dateFormat(JSONObject dateObject){
        Date resultDate = null;
        SimpleDateFormat ISOdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            String dateString = dateObject.getString("iso");
            dateString = dateString.replace("T", " ");
            resultDate = ISOdateFormat.parse(dateString);
        }
        catch(JSONException e){
            //Handle Error
        }
        catch(ParseException e){
            //Handle Error
        }
        return resultDate;
    }



    public String parseDayInWeek(int dayInWeek){
        String dayInWeekString = "ERR";
        switch(dayInWeek){
            case 1:
                dayInWeekString = "SUN";
                break;
            case 2:
                dayInWeekString = "MON";
                break;
            case 3:
                dayInWeekString = "TUE";
                break;
            case 4:
                dayInWeekString = "WED";
                break;
            case 5:
                dayInWeekString = "THU";
                break;
            case 6:
                dayInWeekString = "FRI";
                break;
            case 7:
                dayInWeekString = "SAT";
                break;
        }
        return dayInWeekString;
    }

    public String parseMonth(int month){
        String monthInString = "ERR";
        switch(month){
            case 0:
                monthInString = "JAN";
                break;
            case 1:
                monthInString = "FEB";
                break;
            case 2:
                monthInString = "MAR";
                break;
            case 3:
                monthInString = "APR";
                break;
            case 4:
                monthInString = "MAY";
                break;
            case 5:
                monthInString = "JUN";
                break;
            case 6:
                monthInString = "JUL";
                break;
            case 7:
                monthInString = "AUG";
                break;
            case 8:
                monthInString = "SEP";
                break;
            case 9:
                monthInString = "OCT";
                break;
            case 10:
                monthInString = "NOV";
                break;
            case 11:
                monthInString = "DEC";
                break;
        }
        return monthInString;
    }

    public String getDateAsString(){
        GregorianCalendar eventDate = new GregorianCalendar();
        eventDate.setTime(mDate);
        int dayInWeek = eventDate.get(Calendar.DAY_OF_WEEK);
        String dayInWeekString = parseDayInWeek(dayInWeek);

        String dateString = dayInWeekString + " " + eventDate.get(Calendar.DAY_OF_MONTH) + " / " + parseMonth(eventDate.get(Calendar.MONTH)) + " / " + eventDate.get(Calendar.YEAR);
        return dateString;
    }

    public String getTimeAsString(){
        GregorianCalendar eventDate = new GregorianCalendar();
        eventDate.setTime(mDate);
        String timeString = Integer.toString(eventDate.get(Calendar.HOUR_OF_DAY)) + " : " + Integer.toString(eventDate.get(Calendar.MINUTE));
        return timeString;
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
}
