package com.akula.arcenal.roadrunners.View;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.Model.Event;
import com.akula.arcenal.roadrunners.R;

import java.util.ArrayList;

/**
 * Created by Arcenal on 5/1/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EventHolder> {

    public static class EventHolder extends RecyclerView.ViewHolder {
        private CardView mEventEntryHolder;
        private TextView mEventEntryName, mEventEntryLocation, mEventEntryDistance;

        public EventHolder(View mItemView) {
            super(mItemView);
            mEventEntryHolder = (CardView) mItemView.findViewById(R.id.eventEntry);
            mEventEntryName = (TextView) mItemView.findViewById(R.id.eventName);
            mEventEntryLocation = (TextView) mItemView.findViewById(R.id.eventLocation);
            mEventEntryDistance = (TextView) mItemView.findViewById(R.id.eventDistance);
        }
    }

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
        holder.mEventEntryDistance.setText(Double.toString(mEventEntries.get(position).getDistance()));
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
