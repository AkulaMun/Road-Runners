package com.akula.arcenal.roadrunners.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
public class EventsListFragment extends Fragment {
    private RecyclerView mEventListView;

    public static EventsListFragment newInstance() {
        return new EventsListFragment();
    }

    @Override
    public void onAttach(Context parent){
        super.onAttach(parent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_event_list, container, false);
        mEventListView = (RecyclerView)layout.findViewById(R.id.fragment_event_list_rv);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        listAllEvents();
    }

    private void listAllEvents() {
        if (getContext() != null) {
            mEventListView.setLayoutManager(new LinearLayoutManager(getContext()));
            EventController.getInstance(getContext())
                    .listEvents(new EventController.OnFetchListCompleteListener() {
                        @Override
                        public void onComplete(final List<Event> eventsList, Exception error) {
                            if (eventsList != null && getContext() != null) {
                                //Check For Null here on getContext() required. Suspected case where request completes but activity is already dead.
                                mEventListView.setAdapter(new EventsAdapter(eventsList, new EventsAdapter.OnEventEntryClickListener() {
                                    @Override
                                    public void OnEventClick(Event event) {
                                        displayEventDetails(event);
                                    }
                                }));
                            }
                            if (error != null) {
                                displayErrorDialog("A Problem Occured!", error.getMessage());
                            }
                        }
                    });
        }
    }

    public void displayEventDetails(Event targetEvent){
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, EventDetailFragment.newInstance(targetEvent))
                .addToBackStack("EventDetailFragment")
                .commit();
    }

    private void displayErrorDialog(String title, String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}