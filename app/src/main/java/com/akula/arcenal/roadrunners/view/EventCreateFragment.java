package com.akula.arcenal.roadrunners.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.R;
import com.akula.arcenal.roadrunners.controller.EventController;
import com.akula.arcenal.roadrunners.model.Event;

/**
 * Created by Arcenal on 13/1/2016.
 */
public class EventCreateFragment extends EventDataFragment {

    public static EventCreateFragment newInstance(){
        EventCreateFragment eventCreateFragment = new EventCreateFragment();
        return eventCreateFragment;
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

    protected void saveEvent(View v){
        if(checkData() == true) {
            Event newEvent = new Event(mNameInput.getText().toString(), mLocationInput.getText().toString(), mOrganizerInput.getText().toString(), Double.parseDouble(mDistanceInput.getText().toString()), mEventDate);
            EventController eventController = EventController.getInstance();
            eventController.saveEvent(newEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onDataEditComplete(String message) {
                    displayDialog(message);
                }
            });
            //This Line resets the app to home page. Careful that alert Dialog might never be shown.
            mParentActivity.displayEventList();
        }
    }
}
