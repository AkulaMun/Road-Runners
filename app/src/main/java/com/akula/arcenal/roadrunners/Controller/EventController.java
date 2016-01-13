package com.akula.arcenal.roadrunners.controller;

import com.akula.arcenal.roadrunners.model.Event;
import com.akula.arcenal.roadrunners.view.EventActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventController {
    public interface OnFetchListCompleteListener {
        void onFetchListComplete(ArrayList<Event> events, Exception error);
    };

    public interface OnDataEditCompleteListener{
        void onDataEditComplete(String message);
    };

    private static EventController mInstance = null;

    private EventController(){
    }

    public static EventController getInstance(){
        if(mInstance == null){
            mInstance = new EventController();
        }
        return mInstance;
    }

    public void listAllEvents(final EventActivity hostActivity, final OnFetchListCompleteListener listener){
        ParseController parseControl = ParseController.getInstance();
        parseControl.list("Event", new ParseController.OnOperationCompleteListener() {
            @Override
            public void onOperationComplete(JSONObject resultObject, Exception ex) {
                if(resultObject != null) {
                    try {
                        JSONArray eventResultJSONArray = resultObject.getJSONArray("results");
                        ArrayList<Event> events = new ArrayList<>();
                        int eventsSize = eventResultJSONArray.length();
                        for (int i = 0; i < eventsSize; i++) {
                            //VULNERABLE CODE SECTION. CRASHES HERE IF DATA IS INCOMPLETE. DEFENSIVE PROGRAMMING REQUIRED.
                            JSONObject event = eventResultJSONArray.getJSONObject(i);
                            Event eventObject = new Event(event.getString("name"), event.getString("location"), event.getString("organizer"), event.getDouble("distance"), dateFormat((JSONObject) event.get("date")));
                            eventObject.setID(event.getString("objectId"));
                            events.add(eventObject);
                        }
                        listener.onFetchListComplete(events, null);
                    } catch (JSONException e) {
                        listener.onFetchListComplete(null, e);
                    }
                }

                if(ex != null){
                    //error handling here
                }
            }
        });
    }

    public void saveEvent(Event event, final OnDataEditCompleteListener listener){
        ParseController parseController = ParseController.getInstance();
        parseController.saveEvent(event.JSONifyEvent(), false, new ParseController.OnOperationCompleteListener() {
            @Override
            public void onOperationComplete(JSONObject resultObject, Exception ex) {
                listener.onDataEditComplete("New Event Successfully Saved!");
            }
        });
    }

    public void updateEvent(Event event, final OnDataEditCompleteListener listener){
        ParseController parseController = ParseController.getInstance();
        parseController.saveEvent(event.JSONifyEvent(), true, new ParseController.OnOperationCompleteListener() {
            @Override
            public void onOperationComplete(JSONObject resultObject, Exception ex) {
                listener.onDataEditComplete("Event Details Successfully Updated!");
            }
        });
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
}
