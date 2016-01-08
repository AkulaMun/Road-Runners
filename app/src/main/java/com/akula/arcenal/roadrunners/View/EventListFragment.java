package com.akula.arcenal.roadrunners.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akula.arcenal.roadrunners.Controller.EventController;
import com.akula.arcenal.roadrunners.Controller.ParseController;
import com.akula.arcenal.roadrunners.Model.Event;
import com.akula.arcenal.roadrunners.R;

import java.util.ArrayList;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventListFragment extends Fragment {

    private static Context sParentContext;

    private RecyclerView mEventListView;


    @Override
    public void onAttach(Context parent){
        super.onAttach(parent);

        setParentContext(parent);

        if(sParentContext != null){
            LinearLayoutManager mEventListManager = new LinearLayoutManager(sParentContext);
            mEventListView.setLayoutManager(mEventListManager);

            EventController.listAllEvents(mEventListView);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.event_list, container, false);
        mEventListView = (RecyclerView)layout.findViewById(R.id.eventList);
        return layout;
    };

    public static void setParentContext(Context givenContext){
        sParentContext = givenContext;
    }

    private void display(ArrayList<Event> eventList){
        RecyclerViewAdapter mListEntries = new RecyclerViewAdapter(eventList);
        mEventListView.setAdapter(mListEntries);
    }
}
