package com.akula.arcenal.roadrunners.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akula.arcenal.roadrunners.model.Event;
import com.akula.arcenal.roadrunners.R;
/**
 * Created by Arcenal on 6/1/2016.
 */
public class EventDetailFragment extends Fragment {
    private TextView mNameInput, mDistanceInput, mOrganizerInput, mLocationInput, mEventDetailDateInput, mEventDetailTimeInput;
    private Button mEventActionButton, mBackButton;

    //Defaulted to current time
    private Event mTargetEvent;

    public static EventDetailFragment newInstance(Event targetEvent) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        eventDetailFragment.mTargetEvent = targetEvent;
        return eventDetailFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_event_detail, container, false);
        mNameInput = (TextView) layout.findViewById(R.id.event_detail_name_display);
        mLocationInput = (TextView) layout.findViewById(R.id.event_detail_location_display);
        mDistanceInput = (TextView) layout.findViewById(R.id.event_detail_distance_display);
        mOrganizerInput = (TextView) layout.findViewById(R.id.event_detail_organizer_display);
        mEventActionButton = (Button) layout.findViewById(R.id.event_detail_action_button);
        mBackButton = (Button) layout.findViewById(R.id.event_detail_back_button);
        mEventDetailDateInput = (TextView) layout.findViewById(R.id.event_detail_date_display);
        mEventDetailTimeInput = (TextView) layout.findViewById(R.id.event_detail_time_display);

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEventList();
            }
        });

        mEventActionButton.setText("Edit Event");
        mEventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEventEditFragment();
            }
        });

        mNameInput.setText(mTargetEvent.getName());
        mLocationInput.setText(mTargetEvent.getLocation());
        mDistanceInput.setText(Double.toString(mTargetEvent.getDistance()));
        mOrganizerInput.setText(mTargetEvent.getOrganizer());
        mEventDetailDateInput.setText(mTargetEvent.getDateAsString("date"));
        mEventDetailTimeInput.setText(mTargetEvent.getDateAsString("time"));

        return layout;
    }

    private void displayEventEditFragment() {
        //IMPLEMENT CHECKS FOR OWNERSHIP OF EVENT ENTRY ONCE USER LOGIN IS IMPLEMENTED
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, EventEditFragment.newInstance(mTargetEvent))
                .addToBackStack("EventEditFragment")
                .commit();

    }

    private void displayEventList() {
        // go back to something that was added to the backstack
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        if (fragmentManager != null && fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStackImmediate();
        } else {
            //Shouldn't Happen to come here. If it does, recreate event list instead of crashing.
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EventListFragment.getInstance())
                    .addToBackStack("EventListFragment")
                    .commit();
        }
    }
}
