package com.akula.arcenal.roadrunners.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akula.arcenal.roadrunners.controller.EventController;
import com.akula.arcenal.roadrunners.R;
import com.akula.arcenal.roadrunners.model.Event;

import java.util.List;


/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventListFragment extends Fragment {
    private RecyclerView mEventListView;
    private LinearLayoutManager mEventListManager;
    private FragmentCommunicationListener mListener;

    public static EventListFragment newInstance(FragmentCommunicationListener listener){
        EventListFragment eventListFragment = new EventListFragment();
        eventListFragment.mListener = listener;
        return eventListFragment;
    }

    @Override
    public void onAttach(Context parent){
        super.onAttach(parent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.event_list, container, false);
        mEventListView = (RecyclerView)layout.findViewById(R.id.event_listing);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(getContext());
        mEventListView.setLayoutManager(listLayoutManager);
        listAllEvents();
    }

    private void listAllEvents(){
        if(getContext() != null){
            mEventListManager = new LinearLayoutManager(getContext());
            mEventListView.setLayoutManager(mEventListManager);
            EventController eventController = EventController.getInstance(getContext());
            eventController.listEvent(new EventController.OnFetchListCompleteListener() {
                @Override
                public void onFetchListComplete(final List<Event> eventsList, Exception error) {
                    if (eventsList != null && getContext() != null) {
                        //Check For Null here on getContext() required. Suspected case where request completes but activity is already dead.
                        mEventListView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
                        mEventListView.setAdapter(new EventRecyclerViewAdapter(eventsList, new EventRecyclerViewAdapter.OnEventEntryClickListener() {
                            @Override
                            public void OnEventClick(Event event) {
                                displayEventDetails(event);
                            }
                        }));
                    }
                    if (error != null) {
                        if (mListener != null) {
                            mListener.OnFragmentCommunicate("Connection Error");
                        }
                    }
                }
            });
        }
    }

    public void displayEventDetails(Event targetEvent){
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        EventDetailFragment eventDetailFragment = EventDetailFragment.newInstance(targetEvent, new FragmentCommunicationListener(){
            @Override
            public void OnFragmentCommunicate(String message) {
                if(!message.contentEquals("Error") && mListener != null){
                    mListener.OnFragmentCommunicate("Restart List Fragment.");
                }
            }
        });
        fragmentTransaction.replace(R.id.fragment_container, eventDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}