package com.akula.arcenal.roadrunners.view;

import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.controller.EventController;
import com.akula.arcenal.roadrunners.model.Event;
import com.akula.arcenal.roadrunners.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventDetailFragment extends EventsDetailFragmentInterface {

    private EventActivity mParentActivity = null;
    private Event mTargetEvent;
    private EditText mNameInput, mDistanceInput, mOrganizerInput, mLocationInput;
    private TextView mEventDetailDateInput, mEventDetailTimeInput;
    private Button mEventActionButton, mEventDeleteButton;

    private Date mEventDate = null;
    private int mDateYear = 99;
    private int mDateMonth = 99;
    private int mDateDay = 99;
    private int mDateHour = 99;
    private int mDateMinute = 99;

    public static EventDetailFragment newInstance(Event targetEvent){
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        eventDetailFragment.mTargetEvent = targetEvent;
        eventDetailFragment.mEventDate = targetEvent.getDate();
        return eventDetailFragment;
    }

    public void setParentActivity(EventActivity givenActivity){
        mParentActivity = givenActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.event_detail, container, false);
        mNameInput = (EditText)layout.findViewById(R.id.event_detail_name_input);
        mLocationInput = (EditText)layout.findViewById(R.id.event_detail_location_input);
        mDistanceInput = (EditText)layout.findViewById(R.id.event_detail_distance_input);
        mOrganizerInput = (EditText)layout.findViewById(R.id.event_detail_organizer_input);
        mEventActionButton = (Button)layout.findViewById(R.id.event_detail_action_button);
        mEventDetailDateInput = (TextView)layout.findViewById(R.id.event_detail_date_input);
        mEventDetailTimeInput = (TextView)layout.findViewById(R.id.event_detail_time_input);

        mEventActionButton.setText("Edit Event");
        mEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkToAllowUpdate();
            }
        });

        mEventDeleteButton = (Button)layout.findViewById(R.id.event_detail_delete_button);
        mEventDeleteButton.setVisibility(View.INVISIBLE);
        //To make it re-editable
        //textView.setTag(textView.getKeyListener());
        //textView.setKeyListener((KeyListener)textView.getTag());

        //To make it uneditable

        if(savedInstanceState == null){
            mNameInput.setTag(mNameInput.getKeyListener());
            mNameInput.setKeyListener(null);
            mNameInput.setText(mTargetEvent.getName());
            mLocationInput.setTag(mLocationInput.getKeyListener());
            mLocationInput.setKeyListener(null);
            mLocationInput.setText(mTargetEvent.getLocation());
            mDistanceInput.setTag(mDistanceInput.getKeyListener());
            mDistanceInput.setKeyListener(null);
            mDistanceInput.setText(Double.toString(mTargetEvent.getDistance()));
            mOrganizerInput.setTag(mOrganizerInput.getKeyListener());
            mOrganizerInput.setKeyListener(null);
            mOrganizerInput.setText(mTargetEvent.getOrganizer());
            mEventDetailDateInput.setText(getDateAsString(mTargetEvent.getDate()));
            mEventDetailTimeInput.setText(getTimeAsString(mTargetEvent.getDate()));
        }

        return layout;
    };

    private void updateEvent(View v){
        if(checkData() == true) {
            Event newEvent = new Event(mNameInput.getText().toString(), mLocationInput.getText().toString(), mOrganizerInput.getText().toString(), Double.parseDouble(mDistanceInput.getText().toString()), mEventDate);
            newEvent.setID(mTargetEvent.getID());
            EventController eventController = EventController.getInstance();
            eventController.updateEvent(newEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onDataEditComplete(String message) {
                    //TODO: Display an Alert Dialog
                }
            });
            //This Line resets the app to home page. Careful that alert Dialog might never be shown.
            mParentActivity.displayEventList();
        }
    }

    private void deleteEvent(View v){
        if(checkData() == true) {
            Event newEvent = new Event(mNameInput.getText().toString(), mLocationInput.getText().toString(), mOrganizerInput.getText().toString(), Double.parseDouble(mDistanceInput.getText().toString()), mEventDate);
            newEvent.setID(mTargetEvent.getID());
            EventController eventController = EventController.getInstance();
            eventController.deleteEvent(newEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onDataEditComplete(String message) {
                    //TODO: Display an Alert Dialog
                }
            });
            //This Line resets the app to home page. Careful that alert Dialog might never be shown.
            mParentActivity.displayEventList();
        }
    }

    private boolean checkData(){
        //Input Validity Checking here
        boolean valid = true;

        if(mNameInput.getText().toString().length() == 0){
            valid = false;
        }else if(mLocationInput.getText().toString().length() == 0){
            valid = false;
        }else if(mOrganizerInput.getText().toString().length() == 0){
            valid = false;
        }else if(mDistanceInput.getText().toString().length() == 0) {
            valid = false;
        }else if(mEventDate == null){
            valid = false;
        }

        try{
            Double.parseDouble(mDistanceInput.getText().toString());
        }
        catch(Exception e){
            //TODO set up a better way of handling error
            valid = false;
            Log.e("Number Format Invalid!", "Failed to Parse Distance into a double!");
        }
        return valid;
    }

    public void pickDate(View v){
        DatePickerFragment datePick = new DatePickerFragment();
        datePick.setHostFragment(this);
        datePick.show(mParentActivity.getSupportFragmentManager(), "pickDate");
    }

    public void pickTime(View v){
        TimePickerFragment timePick = new TimePickerFragment();
        timePick.setHostFragment(this);
        timePick.show(mParentActivity.getSupportFragmentManager(), "pickTime");
    }

    public void setDate(int year, int month, int dayOfMonth){
        String dateString = Integer.toString(dayOfMonth) + " / " + parseMonth(month) + " / " + Integer.toString(year);
        mEventDetailDateInput.setText(dateString);
        mDateYear = year;
        mDateMonth = month;
        mDateDay = dayOfMonth;
        dateTimeCombiner();
    }

    public void setTime(int hourOfDay, int minute){
        String timeString = Integer.toString(hourOfDay) + " : " + Integer.toString(minute);
        mEventDetailTimeInput.setText(timeString);
        mDateHour = hourOfDay;
        mDateMinute = minute;
        dateTimeCombiner();
    }

    private void dateTimeCombiner(){
        if(mDateYear != 99 && mDateMonth != 99 && mDateDay != 99 && mDateHour != 99 && mDateMinute != 99){
            mEventDate = new GregorianCalendar(mDateYear, mDateMonth, mDateDay, mDateHour, mDateMinute).getTime();
        }
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

    public static String getTimeAsString(Date givenDate){
        GregorianCalendar eventDate = new GregorianCalendar();
        eventDate.setTime(givenDate);

        String timeString = Integer.toString(eventDate.get(Calendar.HOUR_OF_DAY)) + " : " + Integer.toString(Calendar.MINUTE);
        return timeString;
    }

    private void checkToAllowUpdate(){
        //IMPLEMENT CHECKS FOR OWNERSHIP OF EVENT ENTRY ONCE USER LOGIN IS IMPLEMENTED
        mEventActionButton.setText("Update Event!");
        mEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvent(v);
            }
        });
        mEventDeleteButton.setVisibility(View.VISIBLE);
        mEventDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteEvent(v);
            }
        });
        mNameInput.setKeyListener((KeyListener) mNameInput.getTag());
        mLocationInput.setKeyListener((KeyListener)mLocationInput.getTag());
        mDistanceInput.setKeyListener((KeyListener)mDistanceInput.getTag());
        mOrganizerInput.setKeyListener((KeyListener)mOrganizerInput.getTag());
        mEventDetailDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickDate(v);
            }
        });
        mEventDetailTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickTime(v);
            }
        });
    }
}
