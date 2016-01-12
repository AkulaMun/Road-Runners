package com.akula.arcenal.roadrunners.Controller;

import android.content.Context;
import android.util.Log;

import com.akula.arcenal.roadrunners.Model.Event;
import com.akula.arcenal.roadrunners.View.RecyclerViewAdapter;

import org.json.JSONArray;
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
                        //VULNERABLE CODE SECTION. CRASHES HERE IF DATA IS INCOMPLETE. DEFENSIVE PROGRAMMING REQUIRED.
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

    public static void saveEvent(Event event){
        ParseController parseController = ParseController.getInstance(sApplicationContext);
        parseController.saveEvent(event.JSONifyEvent());
    }

    //Improvement... Working
    public static Date dateFormat(JSONObject dateObject){
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

    public static String parseMonth(int month){
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

    public static String parseDayInWeek(int dayInWeek){
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

    public static String getDateAsString(Date givenDate){
        GregorianCalendar eventDate = new GregorianCalendar();
        eventDate.setTime(givenDate);
        int dayInWeek = eventDate.get(Calendar.DAY_OF_WEEK);
        String dayInWeekString = parseDayInWeek(dayInWeek);

        String dateString = dayInWeekString + " " + eventDate.get(Calendar.DAY_OF_MONTH) + " / " + parseMonth(eventDate.get(Calendar.MONTH)) + " / " + eventDate.get(Calendar.YEAR);
        return dateString;
    }
}
