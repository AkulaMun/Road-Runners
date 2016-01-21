package com.akula.arcenal.roadrunners.view;

/**
 * Created by Arcenal on 19/1/2016.
 */

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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

public class EventEditFragment extends Fragment{
    private static String EVENT = "event";

    private TextInputLayout mNameInput;
    private TextInputLayout mDistanceInput;
    private TextInputLayout mOrganizerInput;
    private TextInputLayout mLocationInput;
    private TextView mEventDetailDateInput;
    private TextView mEventDetailTimeInput;
    private Button mEventActionButton;
    private Button mEventDeleteButton;
    private Date mEventDate;
    private GregorianCalendar mCurrentCalendar = new GregorianCalendar();
    private Event mTargetEvent = null;

    //Factory method to get instance. Provide a null event for new event creation. Handles event creation, editing and deletion.
    public static EventEditFragment newInstance(Event targetEvent) {
        EventEditFragment eventEditFragment = new EventEditFragment();
        Bundle args = new Bundle();
        args.putParcelable(EVENT, targetEvent);
        eventEditFragment.setArguments(args);
        return eventEditFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().getParcelable(EVENT) != null) {
                mTargetEvent = getArguments().getParcelable(EVENT);
                mEventDate = mTargetEvent.getDate();
                mCurrentCalendar.setTime(mEventDate);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_event_edit, container, false);
        mNameInput = (TextInputLayout) layout.findViewById(R.id.fragment_event_edit_til_name);
        mLocationInput = (TextInputLayout) layout.findViewById(R.id.fragment_event_edit_til_location);
        mDistanceInput = (TextInputLayout) layout.findViewById(R.id.fragment_event_edit_til_distance);
        mOrganizerInput = (TextInputLayout) layout.findViewById(R.id.fragment_event_edit_til_organizer);
        mEventActionButton = (Button) layout.findViewById(R.id.fragment_event_edit_btn_action);
        mEventDeleteButton = (Button) layout.findViewById(R.id.fragment_event_edit_btn_delete);
        mEventDetailDateInput = (TextView) layout.findViewById(R.id.fragment_event_edit_tv_date_input);
        mEventDetailTimeInput = (TextView) layout.findViewById(R.id.fragment_event_edit_tv_time_input);

        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(mTargetEvent != null) {
            //Program Flow for Event Editing
            mNameInput.getEditText().setText(mTargetEvent.getName());
            mLocationInput.getEditText().setText(mTargetEvent.getLocation());
            mDistanceInput.getEditText().setText(Double.toString(mTargetEvent.getDistance()));
            mOrganizerInput.getEditText().setText(mTargetEvent.getOrganizer());
            mEventDetailDateInput.setText(mTargetEvent.getDateAsString("date"));
            mEventDetailTimeInput.setText(mTargetEvent.getDateAsString("time"));

            mEventActionButton.setText("Update Event!");
            mEventDeleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteEvent(v);
                }
            });
        } else {
            //Program Flow for Event Creation
            mEventActionButton.setText("Create Event");
            mEventDeleteButton.setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.fragment_event_detail_tv_participants).setVisibility(View.INVISIBLE);
            getView().findViewById(R.id.fragment_event_detail_tv_participants_display).setVisibility(View.INVISIBLE);
        }

        mEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent(v);
            }
        });

        mEventDetailDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });

        mEventDetailTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    private void saveEvent(View v) {
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

        try {
            Double.parseDouble(mDistanceInput.getEditText().getText().toString());
        } catch (Exception e) {
            valid = false;
            displayErrorDialog("Invalid Distance!", "Please Check that you have entered the distance correctly!");
        }

        if (valid) {
            Event newEvent = new Event(mNameInput.getEditText().getText().toString(), mLocationInput.getEditText().getText().toString(), mOrganizerInput.getEditText().getText().toString(), Double.parseDouble(mDistanceInput.getEditText().getText().toString()), mEventDate);
            if (mTargetEvent != null) {
                newEvent.setID(mTargetEvent.getID());
            }
            EventController eventController = EventController.getInstance(getContext());
            eventController.saveEvent(newEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onComplete(String message, Exception ex) {
                    if (message != null) {
                        displayDialog("Event Updated!", message);
                    } else {
                        displayErrorDialog("Error!", ex.getMessage());
                    }
                }
            });
        }
    }

    private void deleteEvent(View v) {
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

        try {
            Double.parseDouble(mDistanceInput.getEditText().getText().toString());
        } catch(Exception e) {
            valid = false;
            displayErrorDialog("Invalid Distance!", "Please Check that you have entered the distance correctly!");
        }

        if (valid) {
            EventController eventController = EventController.getInstance(getContext());
            eventController.deleteEvent(mTargetEvent, new EventController.OnDataEditCompleteListener() {
                @Override
                public void onComplete(String message, Exception ex) {
                    if (message != null) {
                        displayDialog("Event Deleted!", message);
                    } else {
                        displayErrorDialog("Error!", ex.getMessage());
                    }
                }
            });
        }
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

    private void displayEventList() {
        //Go all the way back to the initial list fragment
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0) {
            int count = fragmentManager.getBackStackEntryCount();
            for(int i = 0; i < count - 1; i++) {
                fragmentManager.popBackStack();
            }
            fragmentManager.popBackStackImmediate();
        } else {
            //Shouldn't Happen to come here. If it does, recreate event list instead of crashing.
            Log.e("Critical Failure!", "Check Back Stack");
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EventsListFragment.newInstance())
                    .commit();
        }
    }
}