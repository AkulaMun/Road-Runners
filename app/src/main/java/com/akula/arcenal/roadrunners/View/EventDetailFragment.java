package com.akula.arcenal.roadrunners.View;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    private EventActivity mParentActivity;
    private EditText mNameInput, mDistanceInput, mOrganizerInput, mLocationInput;
    private TextView mEventDetailDateInput, mEventDetailTimeInput;
    private Button mEventActionButton;

    private Date mEventDate;

    public static EventDetailFragment newInstance(){
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
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

        mEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent(v);
            }
        });

        mEventDetailDateInput = (TextView)layout.findViewById(R.id.event_detail_date_input);
        mEventDetailDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mParentActivity != null){
                    mParentActivity.pickDate(v);
                    Log.e("Click", "Click Detected!");
                }
            }
        });

        return layout;
    };

    private void saveEvent(View v){
        if(checkData() == true) {
            GregorianCalendar referenceCalendar = new GregorianCalendar(2017, 00, 30, 00, 00);
            Event newEvent = new Event(mNameInput.getText().toString(), mLocationInput.getText().toString(), mOrganizerInput.getText().toString(), Double.parseDouble(mDistanceInput.getText().toString()), referenceCalendar.getTime());
            EventController.saveEvent(newEvent);
        }
    }

    private boolean checkData(){
        //Input Validity Checking here
        boolean valid = true;

        if(mNameInput.getText().toString().length() == 0){
            valid = false;
        }

        if(valid == true && mLocationInput.getText().toString().length() == 0){
            valid = false;
        }

        if(valid == true && mOrganizerInput.getText().toString().length() == 0){
            valid = false;
        }

        if(valid == true && mDistanceInput.getText().toString().length() == 0){
            try{
                Double.parseDouble(mDistanceInput.getText().toString());
            }
            catch(Exception e){
                //TODO set up a better way of handling error
                valid = false;
                Log.e("Number Format Invalid!", "Failed to Parse Distance into a double!");
            }
        }
        //TODO check date and time validity
        return valid;
    }

    public void setDate(int year, int month, int dayOfMonth){
        mEventDetailDateInput.setText(Integer.toString(year) + " / " + parseMonth(month) + " / " + Integer.toString(dayOfMonth));
    }

    private String parseMonth(int month){
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
