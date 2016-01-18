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
import java.util.List;

/**
 * Created by Arcenal on 5/1/2016.
 */
public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.EventHolder> {

    public interface OnEventEntryClickListener {
        void OnEventClick(Event event);
    }

    public class EventHolder extends RecyclerView.ViewHolder {
        private CardView mEventEntry;
        private TextView mEventEntryName, mEventEntryLocation, mEventEntryDistance, mEventOrganizer, mEventDate, mEventTime;
        private Button mEventJoinButton;
        private boolean mJoined = false;
        private Event mEvent = null;

        public EventHolder(View itemView) {
            super(itemView);
            mEventEntry = (CardView) itemView.findViewById(R.id.event_entry);
            mEventEntryName = (TextView) itemView.findViewById(R.id.event_name);
            mEventEntryLocation = (TextView) itemView.findViewById(R.id.event_location);
            mEventEntryDistance = (TextView) itemView.findViewById(R.id.event_distance);
            mEventOrganizer = (TextView) itemView.findViewById(R.id.event_organizer);
            mEventDate = (TextView) itemView.findViewById(R.id.event_date);
            mEventTime = (TextView) itemView.findViewById(R.id.event_time);
            mEventJoinButton = (Button) itemView.findViewById(R.id.join_event_button);
            mEventJoinButton.setBackgroundResource(R.drawable.standing);
        }

        public void setEvent(Event event) {
            mEvent = event;
        }

        public void toggleJoin() {
            if (!mJoined) {
                mEventJoinButton.setBackgroundResource(R.drawable.running);
                mJoined = true;
            } else {
                mEventJoinButton.setBackgroundResource(R.drawable.standing);
                mJoined = false;
            }
        }
    }
    //ABOVE IS VIEW HOLDER (INDIVIDUAL ENTRY) CLASS. BELOW IS ADAPTER CLASS.
    private List<Event> mEventEntries;
    private OnEventEntryClickListener mListener;
    public EventRecyclerViewAdapter(List<Event> givenEntries, OnEventEntryClickListener listener) {
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
        setEventHolder(holder, mEventEntries.get(position));
    }

    @Override
    public int getItemCount() {
        return mEventEntries.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    private void setEventHolder(final EventHolder holder, Event event) {
        holder.mEventEntryName.setText(event.getName());
        holder.mEventEntryLocation.setText(event.getLocation());
        holder.mEventEntryDistance.setText(Double.toString(event.getDistance()) + " KM");
        holder.mEventOrganizer.setText(event.getOrganizer());
        holder.mEventDate.setText(event.getDateAsString("date"));
        holder.mEventTime.setText(event.getDateAsString("time"));
        holder.setEvent(event);
        holder.mEventEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnEventClick(holder.mEvent);
                }
            }
        });
        holder.mEventJoinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.toggleJoin();
            }
        });
    }
}
