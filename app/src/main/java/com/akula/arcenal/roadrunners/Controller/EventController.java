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
    public interface OnFetchListCompleteListener {
        void onFetchListComplete(List<Event> events, Exception error);
    }

    public interface OnDataEditCompleteListener {
        void onDataEditComplete(String message, Exception error);
    }

    private static EventController mInstance = null;
    private RequestQueue mRequestQueue;
    String eventURL = "https://api.parse.com/1/classes/Event";

    //Private Constructor for Singleton
    private EventController(Context context) {
        Cache mCache = new DiskBasedCache(context.getCacheDir(), 1024*1024);
        Network mNetwork = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(mCache, mNetwork);
        mRequestQueue.start ();
        mInstance = this;
    }

    //Public method to get instance of Controller
    public static EventController getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new EventController(context);
        }
        return mInstance;
    }

    //List all Events into a list.
    public void listEvent(final OnFetchListCompleteListener listener) {
        ParseRequest JSONrequest = new ParseRequest(Request.Method.GET, eventURL, new Response.Listener<JSONObject>() {
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
                            listener.onFetchListComplete(events, null);
                        }
                    } catch (Exception error) {
                        if (listener != null) {
                            listener.onFetchListComplete(null, error);
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onFetchListComplete(null, error);
                }
            }
        });

        mRequestQueue.add(JSONrequest);
    }

    //Saves New Event
    public void saveEvent(Event event, final OnDataEditCompleteListener listener) {
        ParseRequest request = new ParseRequest(Request.Method.POST, eventURL, event.convertTOJSON(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onDataEditComplete("Event Successfully Created!\n" + response, null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onDataEditComplete(null, error);
                }
            }
        });

        mRequestQueue.add(request);
    }

    //Update an Existing Event
    public void updateEvent(Event event, final OnDataEditCompleteListener listener) {
        String ID;
        String URL = eventURL;
        if ((ID = event.convertTOJSON().optString("id", null)) != null) {
            URL += "/" + ID;
        }
        ParseRequest request = new ParseRequest(Request.Method.PUT, URL, event.convertTOJSON(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onDataEditComplete("Event Details Successfully Updated!\n" + response.toString(), null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onDataEditComplete(null, error);
                }
            }
        });

        mRequestQueue.add(request);
    }

    //Deletes an existing event
    public void deleteEvent(Event event, final OnDataEditCompleteListener listener){
        String ID;
        String URL = eventURL;
        if ((ID = event.convertTOJSON().optString("id", null)) != null) {
            URL += "/" + ID;
        }
        ParseRequest request = new ParseRequest(Request.Method.DELETE, URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (listener != null) {
                    listener.onDataEditComplete("Event has been Successfully Deleted!\n" + response.toString(), null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (listener != null) {
                    listener.onDataEditComplete(null, error);
                }
            }
        });

        mRequestQueue.add(request);
    }
}
