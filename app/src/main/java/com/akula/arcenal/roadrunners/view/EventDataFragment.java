package com.akula.arcenal.roadrunners.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 13/1/2016.
 */

//Provides an interface for fragments that needs to handle the details of an event.
public abstract class EventDataFragment extends Fragment {
    protected EditText mNameInput, mDistanceInput, mOrganizerInput, mLocationInput;
    protected TextView mEventDetailDateInput, mEventDetailTimeInput;
    protected Button mEventActionButton;
    protected FragmentCommunicationListener mListener;

    //Defaulted to current time
    protected Date mEventDate = new GregorianCalendar().getTime();
    protected GregorianCalendar mCurrentCalendar = new GregorianCalendar();

    abstract void saveEvent(View v);

    protected boolean checkData(){
        //Input Validity Checking here
        boolean valid = true;

        if(mNameInput.getText().toString().length() == 0 || mLocationInput.getText().toString().length() == 0 || mOrganizerInput.getText().toString().length() == 0 || mDistanceInput.getText().toString().length() == 0){
            valid = false;
        }

        try{
            Double.parseDouble(mDistanceInput.getText().toString());
        }
        catch(Exception e){
            //TODO set up a better way of handling error
            valid = false;
        }
        return valid;
    }

    protected void selectDate(View v) {
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

    protected void selectTime(View v) {
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

    protected void displayDialog(String title, String message) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    protected void displayErrorDialog(String title, String message){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }
}
