package com.akula.arcenal.roadrunners.view;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 13/1/2016.
 */

//Provides an interface for fragments that needs to handle the details of an event.
public abstract class EventsDataFragment extends Fragment {
    protected EventActivity mParentActivity = null;
    protected EditText mNameInput, mDistanceInput, mOrganizerInput, mLocationInput;
    protected TextView mEventDetailDateInput, mEventDetailTimeInput;
    protected Button mEventActionButton;

    protected Date mEventDate = null;
    protected int mDateYear = 99;
    protected int mDateMonth = 99;
    protected int mDateDay = 99;
    protected int mDateHour = 99;
    protected int mDateMinute = 99;

    abstract void saveEvent(View v);

    public void setParentActivity(EventActivity givenActivity){
        mParentActivity = givenActivity;
    }

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
        }
        return valid;
    }

    protected void pickDate(View v){
        DatePickerFragment datePick = new DatePickerFragment();
        datePick.setHostFragment(this);
        datePick.show(mParentActivity.getSupportFragmentManager(), "pickDate");
    }

    protected void pickTime(View v){
        TimePickerFragment timePick = new TimePickerFragment();
        timePick.setHostFragment(this);
        timePick.show(mParentActivity.getSupportFragmentManager(), "pickTime");
    }

    protected void setDate(int year, int month, int dayOfMonth){
        String dateString = Integer.toString(dayOfMonth) + " / " + parseMonth(month) + " / " + Integer.toString(year);
        mEventDetailDateInput.setText(dateString);
        mDateYear = year;
        mDateMonth = month;
        mDateDay = dayOfMonth;
        dateTimeCombiner();
    }

    protected void setTime(int hourOfDay, int minute){
        String timeString = Integer.toString(hourOfDay) + " : " + Integer.toString(minute);
        mEventDetailTimeInput.setText(timeString);
        mDateHour = hourOfDay;
        mDateMinute = minute;
        dateTimeCombiner();
    }

    protected void dateTimeCombiner(){
        if(mDateYear != 99 && mDateMonth != 99 && mDateDay != 99 && mDateHour != 99 && mDateMinute != 99){
            mEventDate = new GregorianCalendar(mDateYear, mDateMonth, mDateDay, mDateHour, mDateMinute).getTime();
        }
    }

    public String parseMonth(int month){
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

    protected void displayDialog(String message){
        AlertDialogFragment alertDialog = new AlertDialogFragment();
        alertDialog.setMessage(message);
        alertDialog.setListener(new AlertDialogFragment.OnDialogConfirmListener() {
            @Override
            public void OnDialogConfirm() {
                mParentActivity.displayEventList();
            }
        });
        alertDialog.show(mParentActivity.getSupportFragmentManager(), "AlertDialog");
    }
}
