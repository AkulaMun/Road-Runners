package com.akula.arcenal.roadrunners.view;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.akula.arcenal.roadrunners.model.Event;
import com.akula.arcenal.roadrunners.R;

public class EventActivity extends AppCompatActivity {

    private MenuItem mCreateEventMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        displayEventList();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mCreateEventMenuItem = (MenuItem)menu.findItem(R.id.event_create_button);
        mCreateEventMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                displayCreateEvent(item);
                return false;
            }
        });
        return true;
    }

    public void displayEventList(){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EventListFragment eventListFragment = EventListFragment.newInstance();
        eventListFragment.setParentActivity(this);
        fragmentTransaction.replace(R.id.fragment_container, eventListFragment);
        fragmentTransaction.commit();
    }

    public void displayCreateEvent(MenuItem clickedItem){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EventCreateFragment createEventFragment = EventCreateFragment.newInstance();
        createEventFragment.setParentActivity(this);
        fragmentTransaction.replace(R.id.fragment_container, createEventFragment);
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void displayEventDetails(Event targetEvent){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EventDetailFragment eventDetailFragment = EventDetailFragment.newInstance(targetEvent);
        eventDetailFragment.setParentActivity(this);
        fragmentTransaction.replace(R.id.fragment_container, eventDetailFragment);
        //fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
