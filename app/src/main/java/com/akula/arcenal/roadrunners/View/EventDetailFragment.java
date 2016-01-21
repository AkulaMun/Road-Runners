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
    private static String EVENT = "event";
    //Defaulted to current time
    private Event mTargetEvent;

    public static EventDetailFragment newInstance(Event targetEvent) {
        EventDetailFragment eventDetailFragment = new EventDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EVENT, targetEvent);
        eventDetailFragment.setArguments(args);
        return eventDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTargetEvent = getArguments().getParcelable(EVENT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Called Upon Fragment Creation
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_event_detail, container, false);
        TextView nameInput = (TextView) layout.findViewById(R.id.fragment_event_detail_tv_name_display);
        TextView locationInput = (TextView) layout.findViewById(R.id.fragment_event_detail_tv_location_display);
        TextView distanceInput = (TextView) layout.findViewById(R.id.fragment_event_detail_tv_distance_display);
        TextView organizerInput = (TextView) layout.findViewById(R.id.fragment_event_detail_tv_organizer_display);
        Button  eventActionButton = (Button) layout.findViewById(R.id.fragment_event_detail_btn_action);
        Button  backButton = (Button) layout.findViewById(R.id.event_detail_back_button);
        TextView eventDetailDateInput = (TextView) layout.findViewById(R.id.fragment_event_detail_tv_date_display);
        TextView eventDetailTimeInput = (TextView) layout.findViewById(R.id.fragment_event_detail_tv_time_display);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEventList();
            }
        });

        eventActionButton.setText("Edit Event");
        eventActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayEventEditFragment();
            }
        });

        nameInput.setText(mTargetEvent.getName());
        locationInput.setText(mTargetEvent.getLocation());
        distanceInput.setText(Double.toString(mTargetEvent.getDistance()));
        organizerInput.setText(mTargetEvent.getOrganizer());
        eventDetailDateInput.setText(mTargetEvent.getDateAsString("date"));
        eventDetailTimeInput.setText(mTargetEvent.getDateAsString("time"));

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
                    .replace(R.id.fragment_container, EventsListFragment.newInstance())
                    .addToBackStack("EventListFragment")
                    .commit();
        }
    }
}
