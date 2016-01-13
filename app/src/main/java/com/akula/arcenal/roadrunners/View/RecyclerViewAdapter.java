package com.akula.arcenal.roadrunners.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.model.Event;
import com.akula.arcenal.roadrunners.R;

import java.util.ArrayList;

/**
 * Created by Arcenal on 5/1/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EventHolder>{

    public interface OnEventEntryClickListener{
        void OnEventClick(int position);
    }

    public static class EventHolder extends RecyclerView.ViewHolder{
        private CardView mEventEntry;
        private TextView mEventEntryName, mEventEntryLocation, mEventEntryDistance, mEventOrganizer, mEventDate;

        public EventHolder(View itemView) {
            super(itemView);
            mEventEntryName = (TextView) itemView.findViewById(R.id.event_name);
            mEventEntryLocation = (TextView) itemView.findViewById(R.id.event_location);
            mEventEntryDistance = (TextView) itemView.findViewById(R.id.event_distance);
            mEventOrganizer = (TextView) itemView.findViewById(R.id.event_organizer);
            mEventDate = (TextView) itemView.findViewById(R.id.event_date);
            mEventEntry = (CardView)itemView.findViewById(R.id.event_entry);
        }
    }
    //ABOVE IS VIEW HOLDER (INDIVIDUAL ENTRY) CLASS. BELOW IS ADAPTER CLASS.
    private ArrayList<Event> mEventEntries;
    private EventActivity mHostActivity;
    public RecyclerViewAdapter(ArrayList<Event> givenEntries, EventActivity givenHostActivity){
        mEventEntries = givenEntries;
        mHostActivity = givenHostActivity;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_entry, parent, false);
        return new EventHolder(listView);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, final int position) {
        holder.mEventEntryName.setText(mEventEntries.get(position).getName());
        holder.mEventEntryLocation.setText(mEventEntries.get(position).getLocation());
        holder.mEventEntryDistance.setText(Double.toString(mEventEntries.get(position).getDistance()) + " KM");
        holder.mEventOrganizer.setText(mEventEntries.get(position).getOrganizer());
        holder.mEventDate.setText(EventDetailFragment.getDateAsString(mEventEntries.get(position).getDate()));
        holder.mEventEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHostActivity.displayEventDetails(mEventEntries.get(position));
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
}
