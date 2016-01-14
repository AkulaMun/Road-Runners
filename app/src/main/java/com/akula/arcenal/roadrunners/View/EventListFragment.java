package com.akula.arcenal.roadrunners.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private EventActivity mParentActivity = null;
    private Context mParentContext;
    private RecyclerView mEventListView;
    private LinearLayoutManager mEventListManager;

    public static EventListFragment newInstance(){
        EventListFragment eventListFragment = new EventListFragment();
        return eventListFragment;
    }

    @Override
    public void onAttach(Context parent){
        super.onAttach(parent);
        setParentContext(parent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.event_list, container, false);
        mEventListView = (RecyclerView)layout.findViewById(R.id.event_listing);
        LinearLayoutManager listLayoutManager = new LinearLayoutManager(mParentContext);
        mEventListView.setLayoutManager(listLayoutManager);

        listAllEvents();

        return layout;
    };

    public void setParentContext(Context givenContext){
        mParentContext = givenContext;
    }
    public void setParentActivity(EventActivity givenHost){
        mParentActivity = givenHost;
    }

    private void listAllEvents(){
        if(mParentContext != null){
            mEventListManager = new LinearLayoutManager(mParentContext);
            mEventListView.setLayoutManager(mEventListManager);
            EventController eventController = EventController.getInstance();
            eventController.listAllEvents(mParentActivity, new EventController.OnFetchListCompleteListener() {
                @Override
                public void onFetchListComplete(final ArrayList<Event> eventsList, Exception error) {
                    if(eventsList != null){
                        mEventListView.addItemDecoration(new SimpleDividerItemDecoration(mParentContext));
                        mEventListView.setAdapter(new RecyclerViewAdapter(eventsList, new RecyclerViewAdapter.OnEventEntryClickListener() {
                            @Override
                            public void OnEventClick(int position) {
                                mParentActivity.displayEventDetails(eventsList.get(position));
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
}
