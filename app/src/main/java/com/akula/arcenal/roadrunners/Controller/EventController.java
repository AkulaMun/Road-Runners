package com.akula.arcenal.roadrunners.controller;

import android.content.Context;

import com.akula.arcenal.roadrunners.model.Event;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventController {
    public interface OnOperationCompleteListener {
        void onOperationComplete(JSONObject resultObject, Exception error);
    }

    public interface OnFetchListCompleteListener {
        void onFetchListComplete(List<Event> events, Exception error);
    }

    public interface OnDataEditCompleteListener{
        void onDataEditComplete(String message, Exception error);
    }

    private static EventController mInstance = null;
    private RequestQueue mRequestQueue;
    String eventURL = "https://api.parse.com/1/classes/Event";

    private EventController(Context context){
        Cache mCache = new DiskBasedCache(context.getCacheDir(), 1024*1024);
        Network mNetwork = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(mCache, mNetwork);
        mRequestQueue.start();
        mInstance = this;
    }

    public static EventController getInstance(Context context){
        if(mInstance == null){
            mInstance = new EventController(context);
        }
        return mInstance;
    }

    private void list(String dataType, final OnOperationCompleteListener listener){
        String url = "https://api.parse.com/1/classes/" + dataType;

        ParseRequest JSONrequest = new ParseRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onOperationComplete(response, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError mErrors) {
                if (listener != null) {
                    listener.onOperationComplete(null, mErrors);
                }
            }
        });
        mRequestQueue.add(JSONrequest);
    }

    private void saveEvent(JSONObject eventJSON, boolean update, final OnOperationCompleteListener listener){
        if(!update){
            ParseRequest request = new ParseRequest(Request.Method.POST, eventURL, eventJSON, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onOperationComplete(response, null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError errors) {
                    listener.onOperationComplete(null, errors);
                }
            });
            mRequestQueue.add(request);
        }
        else{
            String ID;
            String URL = eventURL;
            if((ID = eventJSON.optString("id", null)) != null)
            {
                URL += "/" + ID;
            }
            ParseRequest request = new ParseRequest(Request.Method.PUT, URL, eventJSON, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    listener.onOperationComplete(response, null);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError errors) {
                    listener.onOperationComplete(null, errors);
                }
            });
            mRequestQueue.add(request);
        }
    }

    public void delete(JSONObject eventJSON, final OnOperationCompleteListener listener){
        String ID;
        String URL = eventURL;
        if((ID = eventJSON.optString("id", null)) != null)
        {
            URL += "/" + ID;
        }
        ParseRequest request = new ParseRequest(Request.Method.DELETE, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if(listener != null){
                    listener.onOperationComplete(response, null);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError errors) {
                if(listener != null){
                    listener.onOperationComplete(null, errors);
                }
            }
        });
        mRequestQueue.add(request);
    }

    public void listAllEvents(final OnFetchListCompleteListener listener){
        list("Event", new OnOperationCompleteListener() {
            @Override
            public void onOperationComplete(JSONObject resultObject, Exception ex) {
                if (resultObject != null) {
                    try {
                        JSONArray eventResultJSONArray = resultObject.getJSONArray("results");
                        List<Event> events = new ArrayList<>();
                        int eventsSize = eventResultJSONArray.length();
                        for (int i = 0; i < eventsSize; i++) {
                            //VULNERABLE CODE SECTION. CRASHES HERE IF DATA IS INCOMPLETE. DEFENSIVE PROGRAMMING REQUIRED.
                            JSONObject event = eventResultJSONArray.getJSONObject(i);
                            Event eventObject = new Event(event);
                            eventObject.setID(event.getString("objectId"));
                            events.add(eventObject);
                        }
                        listener.onFetchListComplete(events, null);
                    } catch (JSONException e) {
                        listener.onFetchListComplete(null, e);
                    }
                }
                if (ex != null) {
                    listener.onFetchListComplete(null, ex);
                }
            }
        });
    }

    public void saveEvent(Event event, final OnDataEditCompleteListener listener){
        saveEvent(event.JSONifyEvent(), false, new OnOperationCompleteListener() {
            @Override
            public void onOperationComplete(JSONObject resultObject, Exception ex) {
                listener.onDataEditComplete("New Event Successfully Saved!", ex);
            }
        });
    }

    public void updateEvent(Event event, final OnDataEditCompleteListener listener){
       saveEvent(event.JSONifyEvent(), true, new OnOperationCompleteListener() {
           @Override
           public void onOperationComplete(JSONObject resultObject, Exception ex) {
               listener.onDataEditComplete("Event Details Successfully Updated!", ex);
           }
       });
    }

    public void delete(Event event, final OnDataEditCompleteListener listener){
        delete(event.JSONifyEvent(), new OnOperationCompleteListener() {
            @Override
            public void onOperationComplete(JSONObject resultObject, Exception ex) {
                listener.onDataEditComplete("Event has been Deleted!", ex);
            }
        });
    }
}
