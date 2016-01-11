package com.akula.arcenal.roadrunners.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akula.arcenal.roadrunners.R;

/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.event_list, container, false);
        //mEventListView = (RecyclerView)layout.findViewById(R.id.event_listing);
        //LinearLayoutManager listLayoutManager = new LinearLayoutManager(this.mParentContext);
        //mEventListView.setLayoutManager(listLayoutManager);

        //listAllEvents();

        return layout;
    };
}
