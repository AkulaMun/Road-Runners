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

import com.akula.arcenal.roadrunners.R;
import com.akula.arcenal.roadrunners.controller.EventController;
import com.akula.arcenal.roadrunners.model.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 13/1/2016.
 */
public class EventCreateFragment extends EventsDetailFragmentInterface {
    private EventActivity mParentActivity = null;
    private Event mTargetEvent;
    private EditText mNameInput, mDistanceInput, mOrganizerInput, mLocationInput;
    private TextView mEventDetailDateInput, mEventDetailTimeInput;
    private Button mEventActionButton;

    private Date mEventDate = null;
    private int mDateYear = 99;
    private int mDateMonth = 99;
    private int mDateDay = 99;
    private int mDateHour = 99;
    private int mDateMinute = 99;

    public static EventCreateFragment newInstance(){
        EventCreateFragment eventCreateFragment = new EventCreateFragment();
        return eventCreateFragment;
    }

    public void setParentActivity(EventActivity givenActivity){
        mParentActivity = givenActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.event_create, container, false);
        mNameInput = (EditText)layout.findViewById(R.id.event_detail_name_input);
        mLocationInput = (EditText)layout.findViewById(R.id.event_detail_location_input);
        mDistanceInput = (EditText)layout.findViewById(R.id.event_detail_distance_input);
        mOrganizerInput = (EditText)layout.findViewById(R.id.event_detail_organizer_input);
        mEventActionButton = (Button)layout.findViewById(R.id.event_detail_action_button);
        mEventDetailDateInput = (TextView)layout.findViewById(R.id.event_detail_date_input);
        mEventDetailTimeInput = (TextView)layout.findViewById(R.id.event_detail_time_input);

        mEventActionButton.setText("Create Event");
        mEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent(v);
            }
        });

            //Set Text Areas To call up time and date pickers
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

        return layout;
    };

    protected boolean checkData(){
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

    private void saveEvent(View v){
        if(checkData() == true) {
            Event newEvent = new Event(mNameInput.getText().toString(), mLocationInput.getText().toString(), mOrganizerInput.getText().toString(), Double.parseDouble(mDistanceInput.getText().toString()), mEventDate);
            EventController eventController = EventController.getInstance();
            eventController.saveEvent(newEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onDataEditComplete(String message) {
                    //TODO: Create Alert Box.
                }
            });
            //This Line resets the app to home page. Careful that alert Dialog might never be shown.
            mParentActivity.displayEventList();
        }
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
}
