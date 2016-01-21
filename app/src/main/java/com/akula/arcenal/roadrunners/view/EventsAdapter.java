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

import java.util.List;

/**
 * Created by Arcenal on 5/1/2016.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventHolder> {

    public interface OnEventEntryClickListener {
        void OnEventClick(Event event);
    }

    private List<Event> mEventEntries;
    private OnEventEntryClickListener mListener;
    public EventsAdapter(List<Event> givenEntries, OnEventEntryClickListener listener) {
        mEventEntries = givenEntries;
        mListener = listener;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_event_entry, parent, false);
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

    public class EventHolder extends RecyclerView.ViewHolder {
        private CardView mEventEntry;
        private TextView mEventEntryName;
        private TextView mEventEntryLocation;
        private TextView mEventEntryDistance;
        private TextView mEventOrganizer;
        private TextView mEventDate;
        private TextView mEventTime;
        private Button mEventJoinButton;
        private boolean mJoined = false;
        private Event mEvent = null;

        public EventHolder(View itemView) {
            super(itemView);
            mEventEntry = (CardView) itemView.findViewById(R.id.event_entry);
            mEventEntryName = (TextView) itemView.findViewById(R.id.cardview_event_entry_tv_name);
            mEventEntryLocation = (TextView) itemView.findViewById(R.id.cardview_event_entry_tv_location);
            mEventEntryDistance = (TextView) itemView.findViewById(R.id.cardview_event_entry_tv_distance);
            mEventOrganizer = (TextView) itemView.findViewById(R.id.cardview_event_entry_tv_organizer);
            mEventDate = (TextView) itemView.findViewById(R.id.cardview_event_entry_tv_date);
            mEventTime = (TextView) itemView.findViewById(R.id.cardview_event_entry_tv_time);
            mEventJoinButton = (Button) itemView.findViewById(R.id.cardview_event_entry_btn_join_event);
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
}
