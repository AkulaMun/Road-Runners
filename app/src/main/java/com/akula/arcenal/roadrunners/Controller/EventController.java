package com.akula.arcenal.roadrunners.Controller;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.akula.arcenal.roadrunners.Model.Event;
import com.akula.arcenal.roadrunners.View.EventListFragment;
import com.akula.arcenal.roadrunners.View.RecyclerViewAdapter;

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
    public interface OnOperationCompleteListener{
        public void onOperationComplete(RecyclerViewAdapter adapter, Exception error);
    };

    private static Context sApplicationContext;

    public static void setContext(Context givenContext){
        sApplicationContext = givenContext;
    }

    public static void listAllEvents(final OnOperationCompleteListener listener){
        ParseController parseControl = ParseController.getInstance(sApplicationContext);
        parseControl.list("Event", new ParseController.OnReadCompleteListener() {
            @Override
            public void onReadComplete(JSONObject resultObject, Exception ex) {
                try{
                    JSONArray eventResultJSONArray = resultObject.getJSONArray("results");

                    ArrayList<Event> events = new ArrayList<>();

                    int eventsSize = eventResultJSONArray.length();
                    for(int i = 0; i < eventsSize; i ++){
                        JSONObject event = eventResultJSONArray.getJSONObject(i);
                        events.add(new Event(event.getString("name"), event.getString("location"), event.getString("organizer"), event.getDouble("distance"), dateFormat((JSONObject)event.get("date"))));
                    }
                    listener.onOperationComplete(new RecyclerViewAdapter(events), null);
                }
                catch(JSONException e){
                    listener.onOperationComplete(null, e);
                }
            }
        });
    }

    //tested, proven method. Uses old school idiot way......
    public static Date dateFormat(String dateString){
        Date result = null;
        dateString = dateString.replace("{", "");
        dateString = dateString.replace("}", "");
        dateString = dateString.replace("\"", "");
        dateString = dateString.replace("_type:Date,iso:", "");
        dateString = dateString.replace("T", " ");
        dateString = dateString.substring(0, 16);
        SimpleDateFormat ISOdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            result = ISOdateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Improvement... Experimental
    public static Date dateFormat(JSONObject dateObject){
        Date resultDate = null;
        SimpleDateFormat ISOdateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try{
            String dateString = dateObject.getString("iso");
            dateString = dateString.replace("T", " ");
            ISOdateFormat.parse(dateString);
        }
        catch(JSONException e){
            //Handle Error
        }
        catch(ParseException e){

        }
        return resultDate;
    }
}
