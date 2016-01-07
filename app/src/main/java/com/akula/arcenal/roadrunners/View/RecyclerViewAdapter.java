package com.akula.arcenal.roadrunners.View;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.Model.Event;

import java.util.ArrayList;

/**
 * Created by Arcenal on 5/1/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.EventHolder> {

    private ArrayList<Event> mCurrentEntries;

    public RecyclerViewAdapter(ArrayList<Event> mGivenEntries){
        mCurrentEntries = mGivenEntries;
    }

    @Override
    public EventHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mListView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_entry, parent, false);
        EventHolder mEntriesHolder = new EventHolder(mListView);
        return mEntriesHolder;
    }

    @Override
    public void onBindViewHolder(EventHolder holder, int position) {
        holder.mEventEntryName.setText(mCurrentEntries.get(position).getName());
        holder.mEventEntryLocation.setText(mCurrentEntries.get(position).getLocation());
        holder.mEventEntryDistance.setText(Double.toString(mCurrentEntries.get(position).getDistance()));
    }

    @Override
    public int getItemCount() {
        return mCurrentEntries.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class EventHolder extends RecyclerView.ViewHolder{
        CardView mEventEntryHolder;
        TextView mEventEntryName, mEventEntryLocation, mEventEntryDistance;

        public EventHolder(View mItemView){
            super(mItemView);
            mEventEntryHolder = (CardView)mItemView.findViewById(R.id.eventEntry);
            mEventEntryName = (TextView)mItemView.findViewById(R.id.eventName);
            mEventEntryLocation = (TextView)mItemView.findViewById(R.id.eventLocation);
            mEventEntryDistance = (TextView)mItemView.findViewById(R.id.eventDistance);
        }
    }
