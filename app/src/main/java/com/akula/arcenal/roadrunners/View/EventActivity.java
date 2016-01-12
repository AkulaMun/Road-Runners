package com.akula.arcenal.roadrunners.View;


import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.akula.arcenal.roadrunners.Model.Event;
import com.akula.arcenal.roadrunners.R;

public class EventActivity extends AppCompatActivity {

    private EventListFragment mEventListFragment;
    private EventDetailFragment mEventDetailFragment;
    private MenuItem mCreateEventMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mEventListFragment = new EventListFragment();
        mEventListFragment.setParentActivity(this);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, mEventListFragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mCreateEventMenuItem = (MenuItem)menu.findItem(R.id.event_create_button);
        mCreateEventMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switchToCreateFragment(item);
                return false;
            }
        });
        return true;
    }

    private void switchToCreateFragment(MenuItem clickedItem){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(mEventListFragment);
        mEventDetailFragment = EventDetailFragment.newInstance();
        mEventDetailFragment.setParentActivity(this);
        fragmentTransaction.replace(R.id.fragment_container, mEventDetailFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void pickDate(View v){
        DatePickerFragment datePick = new DatePickerFragment();
        datePick.setParentActivity(this);
        datePick.show(getSupportFragmentManager(), "pickDate");
    }

    public void relayDate(View v, int year, int month, int dayOfMonth){
        mEventDetailFragment.setDate(year, month, dayOfMonth);
    }

    public void pickTime(View v){
        TimePickerFragment timePick = new TimePickerFragment();
        timePick.setParentActivity(this);
        timePick.show(getSupportFragmentManager(), "pickTime");
    }

    public void relayTime(View v, int hourOfDay, int minute){
        mEventDetailFragment.setTime(hourOfDay, minute);
    }

    public void displayEventDetails(Event targetEvent){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.remove(mEventListFragment);
        mEventDetailFragment = EventDetailFragment.newInstance(targetEvent);
        mEventDetailFragment.setParentActivity(this);
        fragmentTransaction.replace(R.id.fragment_container, mEventDetailFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
