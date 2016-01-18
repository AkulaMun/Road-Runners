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

import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventDetailFragment extends EventDataFragment {
    private Event mTargetEvent;
    private Button mEventDeleteButton;

    public static EventDetailFragment newInstance(Event targetEvent, final FragmentCommunicationListener listener) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        eventDetailFragment.mTargetEvent = targetEvent;
        eventDetailFragment.mEventDate = targetEvent.getDate();
        eventDetailFragment.mCurrentCalendar.setTime(eventDetailFragment.mEventDate);
        eventDetailFragment.mListener = listener;
        return eventDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.event_detail, container, false);
        mNameInput = (EditText) layout.findViewById(R.id.event_detail_name_input);
        mLocationInput = (EditText) layout.findViewById(R.id.event_detail_location_input);
        mDistanceInput = (EditText) layout.findViewById(R.id.event_detail_distance_input);
        mOrganizerInput = (EditText) layout.findViewById(R.id.event_detail_organizer_input);
        mEventActionButton = (Button) layout.findViewById(R.id.event_detail_action_button);
        mEventDetailDateInput = (TextView) layout.findViewById(R.id.event_detail_date_input);
        mEventDetailTimeInput = (TextView) layout.findViewById(R.id.event_detail_time_input);

        mEventActionButton.setText("Edit Event");
        mEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkToAllowUpdate();
            }
        });

        mEventDeleteButton = (Button) layout.findViewById(R.id.event_detail_delete_button);
        mEventDeleteButton.setVisibility(View.INVISIBLE);

        //To make it re-editable
        //textView.setTag(textView.getKeyListener());
        //textView.setKeyListener((KeyListener)textView.getTag());

        //To make it uneditable. Checks if it is newly created. Causes crashes on screen rotate while in this view if unchecked.
        if (savedInstanceState == null){
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
            mEventDetailDateInput.setText(mTargetEvent.getDateAsString("date"));
            mEventDetailTimeInput.setText(mTargetEvent.getDateAsString("time"));

            GregorianCalendar currentTime = new GregorianCalendar();
            currentTime.setTime(mEventDate);
        }
        return layout;
    };

    protected void saveEvent(View v){
        if(checkData() == true) {
            Event newEvent = new Event(mNameInput.getText().toString(), mLocationInput.getText().toString(), mOrganizerInput.getText().toString(), Double.parseDouble(mDistanceInput.getText().toString()), mEventDate);
            newEvent.setID(mTargetEvent.getID());
            EventController eventController = EventController.getInstance(getContext());
            eventController.updateEvent(newEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onDataEditComplete(String message, Exception ex) {
                    if (message != null) {
                        displayDialog("Event Updated!", message);
                    }
                    else{
                        displayErrorDialog("Error!", ex.getMessage());
                    }
                }
            });
        }
    }

    private void deleteEvent(View v){
        if(checkData() == true) {
            Event newEvent = new Event(mNameInput.getText().toString(), mLocationInput.getText().toString(), mOrganizerInput.getText().toString(), Double.parseDouble(mDistanceInput.getText().toString()), mEventDate);
            newEvent.setID(mTargetEvent.getID());
            EventController eventController = EventController.getInstance(getContext());
            eventController.deleteEvent(newEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onDataEditComplete(String message, Exception ex) {
                    if (message != null) {
                        displayDialog("Event Deleted!", message);
                    } else {
                        displayErrorDialog("Error!", ex.getMessage());
                    }
                }
            });
        }
    }

    private void checkToAllowUpdate(){
        //IMPLEMENT CHECKS FOR OWNERSHIP OF EVENT ENTRY ONCE USER LOGIN IS IMPLEMENTED
        mEventActionButton.setText("Update Event!");
        mEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent(v);
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
        mOrganizerInput.setKeyListener((KeyListener) mOrganizerInput.getTag());
        mEventDetailDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate(v);
            }
        });
        mEventDetailTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(v);
            }
        });
    }
}
