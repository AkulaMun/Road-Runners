package com.akula.arcenal.roadrunners.View;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.Controller.EventController;
import com.akula.arcenal.roadrunners.Model.Event;
import com.akula.arcenal.roadrunners.R;

import java.util.ArrayList;

/**
 * Created by Arcenal on 5/1/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EventHolder> {

    public static class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mEventEntryName, mEventEntryLocation, mEventEntryDistance, mEventOrganizer, mEventDate;

        public EventHolder(View itemView) {
            super(itemView);
            mEventEntryName = (TextView) itemView.findViewById(R.id.event_name);
            mEventEntryLocation = (TextView) itemView.findViewById(R.id.event_location);
            mEventEntryDistance = (TextView) itemView.findViewById(R.id.event_distance);
            mEventOrganizer = (TextView) itemView.findViewById(R.id.event_organizer);
            mEventDate = (TextView) itemView.findViewById(R.id.event_date);
        }

        @Override
        public void onClick(View v) {

        }
    }
    //ABOVE IS VIEW HOLDER (INDIVIDUAL ENTRY) CLASS. BELOW IS ADAPTER CLASS.
    private ArrayList<Event> mEventEntries;
    public RecyclerViewAdapter(ArrayList<Event> givenEntries) {
        mEventEntries = givenEntries;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_entry, parent, false);
        return new EventHolder(listView);
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.mEventEntryName.setText(mEventEntries.get(position).getName());
        holder.mEventEntryLocation.setText(mEventEntries.get(position).getLocation());
        holder.mEventEntryDistance.setText(Double.toString(mEventEntries.get(position).getDistance()) + " KM");
        holder.mEventOrganizer.setText(mEventEntries.get(position).getOrganizer());
        holder.mEventDate.setText(EventController.getDateAsString(mEventEntries.get(position).getDate()));
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
