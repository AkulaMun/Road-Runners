package com.akula.arcenal.roadrunners.View;

import android.app.DatePickerDialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 11/1/2016.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    EventActivity mParentActivity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar currentCalendar = Calendar.getInstance();
        int year = currentCalendar.get(Calendar.YEAR);
        int month = currentCalendar.get(Calendar.MONTH);
        int day = currentCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(new GregorianCalendar().getTimeInMillis());
        return datePickerDialog;
    }

    public void setParentActivity(EventActivity givenActivity){
        mParentActivity = givenActivity;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        mParentActivity.relayDate(view, year, monthOfYear, dayOfMonth);
    }
}
