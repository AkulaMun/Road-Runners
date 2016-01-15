package com.akula.arcenal.roadrunners.view;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Arcenal on 11/1/2016.
 */
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
    public interface OnTimePickerCompleteListener{
        void OnTimePickerComplete(int hourOfDay, int minute);
    }

    private OnTimePickerCompleteListener mListener;
    private Date mDate;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar currentCalendar = Calendar.getInstance();
        if(mDate != null){
            currentCalendar.setTime(mDate);
        }
        int hour = currentCalendar.get(Calendar.HOUR);
        int min = currentCalendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(), this, hour, min, false);
    }

    public void setListener(OnTimePickerCompleteListener listener){
        mListener = listener;
    }

    public void setDefaultTime(Date date){
        mDate = date;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if(mListener != null){
            mListener.OnTimePickerComplete(hourOfDay, minute);
        }
    }
}
