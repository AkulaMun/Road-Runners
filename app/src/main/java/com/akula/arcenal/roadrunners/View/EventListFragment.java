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

import java.util.ArrayList;


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
            EventController eventController = EventController.getInstance();
            eventController.listAllEvents(new EventController.OnFetchListCompleteListener() {
                @Override
                public void onFetchListComplete(final ArrayList<Event> eventsList, Exception error) {
                    if(eventsList != null && getContext()!= null){
                        //Check For Null here on getContext() required, else if the fetch request completes but activity died off, crashes. Now how to stop all outgoing request when activity dies?
                        mEventListView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
                        mEventListView.setAdapter(new RecyclerViewAdapter(eventsList, new RecyclerViewAdapter.OnEventEntryClickListener() {
                            @Override
                            public void OnEventClick(int position) {
                                displayEventDetails(eventsList.get(position));
                            }
                        }));
                    }
                    if(error != null){
                        //Error handling
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
                mListener.OnFragmentCommunicate("Reset Fragments, Restart List Fragment.");
            }
        });
        fragmentTransaction.replace(R.id.fragment_container, eventDetailFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
