package com.akula.arcenal.roadrunners.controller;

import android.content.Context;

import com.akula.arcenal.roadrunners.api.ParseRequest;
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
import org.json.JSONObject;

import java.util.List;
import java.util.ArrayList;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventController {
    private RequestQueue mRequestQueue;
    private static EventController sInstance = null;
    private static final String EVENT_URL = "https://api.parse.com/1/classes/Event";

    //Private Constructor for Singleton
    private EventController(Context context) {
        Cache mCache = new DiskBasedCache(context.getCacheDir(), 1024*1024);
        Network mNetwork = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(mCache, mNetwork);
        mRequestQueue.start ();
    }

    //Public method to get instance of Controller
    public static EventController getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new EventController(context);
        }
        return sInstance;
    }

    public interface OnFetchListCompleteListener {
        void onComplete(List<Event> events, Exception error);
    }
    //List all Events into a list.
    public void listEvents(final OnFetchListCompleteListener listener) {
        ParseRequest JSONrequest = new ParseRequest(Request.Method.GET, EVENT_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        JSONArray eventResultJSONArray = response.getJSONArray("results");
                        List<Event> events = new ArrayList<>();
                        int eventsSize = eventResultJSONArray.length();
                        for (int i = 0; i < eventsSize; i++) {
                            JSONObject event = eventResultJSONArray.getJSONObject(i);
                            Event eventObject = new Event(event);
                            eventObject.setID(event.getString("objectId"));
                            events.add(eventObject);
                        }

                        if (listener != null) {
                            listener.onComplete(events, null);
                        }
                    } catch (Exception error) {
                        if (listener != null) {
                            listener.onComplete(null, error);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onComplete(null, error);
                }
            }
        });
        mRequestQueue.add(JSONrequest);
    }

    public interface OnDataEditCompleteListener {
        void onComplete(String message, Exception error);
    }
    //Saves/Updates New Event
    public void saveEvent(Event event, final OnDataEditCompleteListener listener) {
        String ID;
        String URL = EVENT_URL;
        String message = "Event Successfully Created!";
        int requestMethod = Request.Method.POST;
        if ((ID = event.convertTOJSON().optString("id", null)) != null) {
            URL += "/" + ID;
            requestMethod = Request.Method.PUT;
            message = "Event Details Successfully Updated!";
        }
        final String listenerMessage = message;
        ParseRequest request = new ParseRequest(requestMethod, URL, event.convertTOJSON(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onComplete(listenerMessage, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onComplete(null, error);
                }
            }
        });
        mRequestQueue.add(request);
    }

    //Deletes an existing event
    public void deleteEvent(Event event, final OnDataEditCompleteListener listener){
        String ID;
        String URL = EVENT_URL;
        if ((ID = event.convertTOJSON().optString("id", null)) != null) {
            URL += "/" + ID;
        }
        ParseRequest request = new ParseRequest(Request.Method.DELETE, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onComplete("Event has been Successfully Deleted!", null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onComplete(null, error);
                }
            }
        });
        mRequestQueue.add(request);
    }
}
