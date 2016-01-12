package com.akula.arcenal.roadrunners.View;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.Controller.EventController;
import com.akula.arcenal.roadrunners.Model.Event;
import com.akula.arcenal.roadrunners.R;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventDetailFragment extends Fragment {

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

    public static EventDetailFragment newInstance(){
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        return eventDetailFragment;
    }

    public static EventDetailFragment newInstance(Event targetEvent){
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        eventDetailFragment.mTargetEvent = targetEvent;
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

        if(mTargetEvent == null){
            mEventActionButton.setText("Create Event");
            mEventActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveEvent(v);
                }
            });

            //Time and Date Pickers, communicating with Activity, across fragments.
            mEventDetailDateInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mParentActivity != null) {
                        mParentActivity.pickDate(v);
                    }
                }
            });

            mEventDetailTimeInput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mParentActivity != null){
                        mParentActivity.pickTime(v);
                    }
                }
            });
        }
        else{
            mEventActionButton.setText("Update Event");
            mEventActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            //To make it reeditable
            //textView.setTag(textView.getKeyListener());
            //textView.setKeyListener((KeyListener)textView.getTag());

            //To make it uneditable
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
            mEventDetailDateInput.setText(EventController.getDateAsString(mTargetEvent.getDate()));
            mEventDetailTimeInput.setText(EventController.getTimeAsString(mTargetEvent.getDate()));
        }

        return layout;
    };

    private void saveEvent(View v){
        if(checkData() == true) {
            Event newEvent = new Event(mNameInput.getText().toString(), mLocationInput.getText().toString(), mOrganizerInput.getText().toString(), Double.parseDouble(mDistanceInput.getText().toString()), mEventDate);
            EventController.saveEvent(newEvent);
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
        //TODO check date and time validity
        return valid;
    }

    public void setDate(int year, int month, int dayOfMonth){
        String dateString = Integer.toString(dayOfMonth) + " / " + EventController.parseMonth(month) + " / " + Integer.toString(year);
        mEventDetailDateInput.setText(dateString);
        mDateYear = year;
        mDateMonth = month;
        mDateDay = dayOfMonth;
        combiner();
    }

    public void setTime(int hourOfDay, int minute){
        String timeString = Integer.toString(hourOfDay) + " : " + Integer.toString(minute);
        mEventDetailTimeInput.setText(timeString);
        mDateHour = hourOfDay;
        mDateMinute = minute;
        combiner();
    }

    private void combiner(){
        if(mDateYear != 99 && mDateMonth != 99 && mDateDay != 99 && mDateHour != 99 && mDateMinute != 99){
            mEventDate = new GregorianCalendar(mDateYear, mDateMonth, mDateDay, mDateHour, mDateMinute).getTime();
        }
    }
}
