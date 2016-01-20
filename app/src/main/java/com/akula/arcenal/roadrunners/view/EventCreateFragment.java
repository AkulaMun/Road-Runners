package com.akula.arcenal.roadrunners.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.akula.arcenal.roadrunners.R;
import com.akula.arcenal.roadrunners.controller.EventController;
import com.akula.arcenal.roadrunners.model.Event;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 13/1/2016.
 */
public class EventCreateFragment extends Fragment {
    private TextInputLayout mNameInput, mDistanceInput, mOrganizerInput, mLocationInput;
    private TextView mEventDetailDateInput, mEventDetailTimeInput;
    private Button mEventActionButton, mBackButton;

    //Defaulted to current time
    private Date mEventDate = new GregorianCalendar().getTime();
    private GregorianCalendar mCurrentCalendar = new GregorianCalendar();

    public static EventCreateFragment newInstance() {
        EventCreateFragment eventCreateFragment = new EventCreateFragment();
        eventCreateFragment.mCurrentCalendar.setTime(eventCreateFragment.mEventDate);
        return eventCreateFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_event_create, container, false);
        mNameInput = (TextInputLayout) layout.findViewById(R.id.event_create_name_input_layout);
        mLocationInput = (TextInputLayout) layout.findViewById(R.id.event_create_location_input_layout);
        mDistanceInput = (TextInputLayout) layout.findViewById(R.id.event_create_distance_input_layout);
        mOrganizerInput = (TextInputLayout) layout.findViewById(R.id.event_create_organizer_layout);
        mEventDetailDateInput = (TextView) layout.findViewById(R.id.event_create_date_input);
        mEventDetailTimeInput = (TextView) layout.findViewById(R.id.event_create_time_input);
        mEventActionButton = (Button) layout.findViewById(R.id.event_create_action_button);
        mBackButton = (Button) layout.findViewById(R.id.event_create_back_button);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEventList();
            }
        });

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
                selectDate(v);
            }
        });

        mEventDetailTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectTime(v);
            }
        });

        return layout;
    }

    protected void saveEvent(View v) {
        boolean valid = true;
        if (mNameInput.getEditText().getText().toString().length() == 0) {
            mNameInput.setError("Please provide a name for the event!");
            mNameInput.setErrorEnabled(true);
            valid = false;
        }
        if (mLocationInput.getEditText().getText().toString().length() == 0) {
            mLocationInput.setError("Please provide a location!");
            mLocationInput.setErrorEnabled(true);
            valid = false;
        }
        if (mOrganizerInput.getEditText().getText().toString().length() == 0) {
            mOrganizerInput.setError("Please provide event organizer!");
            mOrganizerInput.setErrorEnabled(true);
            valid = false;
        }
        if (mDistanceInput.getEditText().getText().toString().length() == 0) {
            mDistanceInput.setError("Please provide a valid distance or 0 if you're unsure.");
            mDistanceInput.setErrorEnabled(true);
            valid = false;
        }

        try{
            Double.parseDouble(mDistanceInput.getEditText().getText().toString());
        } catch(Exception e) {
            valid = false;
            displayErrorDialog("Invalid Distance!", "Please Check that you have entered the distance correctly!");
        }

        if(valid == true) {
            mNameInput.setErrorEnabled(false);
            mLocationInput.setErrorEnabled(false);
            mDistanceInput.setErrorEnabled(false);
            mOrganizerInput.setErrorEnabled(false);
        }

        if(valid) {
            Event newEvent = new Event(mNameInput.getEditText().getText().toString(), mLocationInput.getEditText().getText().toString(), mOrganizerInput.getEditText().getText().toString(), Double.parseDouble(mDistanceInput.getEditText().getText().toString()), mEventDate);
            EventController eventController = EventController.getInstance(getContext());
            eventController.saveEvent(newEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onComplete(String message, Exception ex) {
                    if(message != null){
                        displayDialog("Event Saved!", message);
                    }
                    else{
                        displayErrorDialog("Error!", ex.getMessage());
                    }
                }
            });
        }
    }

    private void selectDate(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mCurrentCalendar.set(Calendar.YEAR, year);
                mCurrentCalendar.set(Calendar.MONTH, monthOfYear);
                mCurrentCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                mEventDate = mCurrentCalendar.getTime();

                SimpleDateFormat dateFormat = new SimpleDateFormat("EEE d/MMMM/yyyy");
                mEventDetailDateInput.setText(dateFormat.format(mEventDate));
            }
        }, mCurrentCalendar.get(Calendar.YEAR), mCurrentCalendar.get(Calendar.MONTH), mCurrentCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Select Event Date");
        datePickerDialog.getDatePicker().setMinDate(new GregorianCalendar().getTimeInMillis());
        datePickerDialog.show();
    }

    private void selectTime(View v) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mCurrentCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                mCurrentCalendar.set(Calendar.MINUTE, minute);
                mEventDate = mCurrentCalendar.getTime();

                SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma ZZZZ");
                mEventDetailTimeInput.setText(timeFormat.format(mEventDate));
            }
        }, mCurrentCalendar.get(Calendar.HOUR_OF_DAY), mCurrentCalendar.get(Calendar.MINUTE), false);
        timePickerDialog.setTitle("Select Event Time");
        timePickerDialog.show();
    }

    private void displayDialog(String title, String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        displayEventList();
                    }
                })
                .show();
    }

    private void displayErrorDialog(String title, String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void displayEventList(){
        // go back to something that was added to the backstack
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStackImmediate();
        } else {
            //Shouldn't Happen to come here. If it does, recreate event list instead of crashing.
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            EventListFragment eventListFragment = EventListFragment.getInstance();
            fragmentTransaction.replace(R.id.fragment_container, eventListFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
}
