package com.akula.arcenal.roadrunners.view;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.akula.arcenal.roadrunners.R;

public class EventActivity extends AppCompatActivity {

    private MenuItem mCreateEventMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        //Clears the back navigation, since the whole App is supposed to reset when activity is recreated.
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(savedInstanceState != null && fragmentManager.getBackStackEntryCount() > 0){
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        displayEventList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //For some reason, putting displayEventList here breaks on screen rotation. getContext() returns null..
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
        EventListFragment eventListFragment = EventListFragment.newInstance(new FragmentCommunicationListener() {
            @Override
            public void OnFragmentCommunicate(String message) {
                displayEventList();
            }
        });
        fragmentTransaction.replace(R.id.fragment_container, eventListFragment);
        fragmentTransaction.commit();
    }

    public void displayCreateEvent(MenuItem clickedItem){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        EventCreateFragment createEventFragment = EventCreateFragment.newInstance(new FragmentCommunicationListener() {
            @Override
            public void OnFragmentCommunicate(String message) {
                displayEventList();
            }
        });
        fragmentTransaction.replace(R.id.fragment_container, createEventFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
