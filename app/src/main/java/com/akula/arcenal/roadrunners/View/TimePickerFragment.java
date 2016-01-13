package com.akula.arcenal.roadrunners.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by Arcenal on 11/1/2016.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    EventsDetailFragmentInterface mParentFragment;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar currentCalendar = Calendar.getInstance();
        int hour = currentCalendar.get(Calendar.HOUR);
        int min = currentCalendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, min, false);
    }

    public void setHostFragment(EventsDetailFragmentInterface givenFragment){
        mParentFragment = givenFragment;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mParentFragment.setTime(hourOfDay, minute);
    }
}
