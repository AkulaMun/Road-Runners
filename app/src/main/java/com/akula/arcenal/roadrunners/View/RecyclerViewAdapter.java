package com.akula.arcenal.roadrunners.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.model.Event;
import com.akula.arcenal.roadrunners.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Arcenal on 5/1/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EventHolder>{

    public interface OnEventEntryClickListener{
        void OnEventClick(int position);
    }

    public static class EventHolder extends RecyclerView.ViewHolder{
        private CardView mEventEntry;
        private TextView mEventEntryName, mEventEntryLocation, mEventEntryDistance, mEventOrganizer, mEventDate, mEventTime;
        private Button mEventJoinButton;
        private boolean mJoined = false;

        public EventHolder(View itemView) {
            super(itemView);
            mEventEntry = (CardView)itemView.findViewById(R.id.event_entry);
            mEventEntryName = (TextView) itemView.findViewById(R.id.event_name);
            mEventEntryLocation = (TextView) itemView.findViewById(R.id.event_location);
            mEventEntryDistance = (TextView) itemView.findViewById(R.id.event_distance);
            mEventOrganizer = (TextView) itemView.findViewById(R.id.event_organizer);
            mEventDate = (TextView) itemView.findViewById(R.id.event_date);
            mEventTime = (TextView) itemView.findViewById(R.id.event_time);
            mEventJoinButton = (Button)itemView.findViewById(R.id.join_event_button);
            mEventJoinButton.setBackgroundResource(R.drawable.standing);
        }

        public void toggleJoin(){
            if(mJoined == false){
                mEventJoinButton.setBackgroundResource(R.drawable.running);
                mJoined = true;
            }
            else{
                mEventJoinButton.setBackgroundResource(R.drawable.standing);
                mJoined = false;
            }
        }
    }
    //ABOVE IS VIEW HOLDER (INDIVIDUAL ENTRY) CLASS. BELOW IS ADAPTER CLASS.
    private ArrayList<Event> mEventEntries;
    private OnEventEntryClickListener mListener;
    public RecyclerViewAdapter(ArrayList<Event> givenEntries, OnEventEntryClickListener listener){
        mEventEntries = givenEntries;
        mListener = listener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_entry, parent, false);
        return new EventHolder(listView);
    }

    @Override
    public void onBindViewHolder(final EventHolder holder, final int position) {
        holder.mEventEntryName.setText(mEventEntries.get(position).getName());
        holder.mEventEntryLocation.setText(mEventEntries.get(position).getLocation());
        holder.mEventEntryDistance.setText(Double.toString(mEventEntries.get(position).getDistance()) + " KM");
        holder.mEventOrganizer.setText(mEventEntries.get(position).getOrganizer());
        holder.mEventDate.setText(getDateAsString(mEventEntries.get(position).getDate()));
        holder.mEventTime.setText(getTimeAsString(mEventEntries.get(position).getDate()));
        holder.mEventEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.OnEventClick(position);
            }
        });
        holder.mEventJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.toggleJoin();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventEntries.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public String getDateAsString(Date givenDate){
        GregorianCalendar eventDate = new GregorianCalendar();
        eventDate.setTime(givenDate);
        int dayInWeek = eventDate.get(Calendar.DAY_OF_WEEK);
        String dayInWeekString = parseDayInWeek(dayInWeek);

        String dateString = dayInWeekString + " " + eventDate.get(Calendar.DAY_OF_MONTH) + " / " + parseMonth(eventDate.get(Calendar.MONTH)) + " / " + eventDate.get(Calendar.YEAR);
        return dateString;
    }

    public String getTimeAsString(Date givenDate){
        GregorianCalendar eventDate = new GregorianCalendar();
        eventDate.setTime(givenDate);
        int hour = eventDate.get(Calendar.HOUR_OF_DAY);
        int minute = eventDate.get(Calendar.MINUTE);
        String hourString = Integer.toString(hour);
        String minString = Integer.toString(minute);

        if(hour < 10){
            hourString = "0" + hourString;
        }
        if(minute < 10){
            minString = "0" + minString;
        }
        String timeString = hourString + " : " + minString;
        return timeString;
    }

    public static String parseDayInWeek(int dayInWeek){
        String dayInWeekString = "ERR";
        switch(dayInWeek){
            case 1:
                dayInWeekString = "SUN";
                break;
            case 2:
                dayInWeekString = "MON";
                break;
            case 3:
                dayInWeekString = "TUE";
                break;
            case 4:
                dayInWeekString = "WED";
                break;
            case 5:
                dayInWeekString = "THU";
                break;
            case 6:
                dayInWeekString = "FRI";
                break;
            case 7:
                dayInWeekString = "SAT";
                break;
        }
        return dayInWeekString;
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
}
