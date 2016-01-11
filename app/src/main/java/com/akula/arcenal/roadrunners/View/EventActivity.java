package com.akula.arcenal.roadrunners.View;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.akula.arcenal.roadrunners.R;

public class EventActivity extends AppCompatActivity {

    private FrameLayout mFragmentContainer;
    private EventListFragment mEventListFragment;
    private EventDetailFragment mEventDetailFragment;
    private FragmentTransaction mFragmentTransaction;
    private MenuItem mCreateEventMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mFragmentContainer = (FrameLayout)findViewById(R.id.fragment_container);

        mEventListFragment = new EventListFragment();

        mFragmentTransaction = getSupportFragmentManager().beginTransaction();

        mFragmentTransaction.add(R.id.fragment_container, mEventListFragment);

        mFragmentTransaction.commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mCreateEventMenuItem = (MenuItem)menu.findItem(R.id.event_create_button);
        mCreateEventMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        return true;
    }

    private void switchToCreateFragment(MenuItem clickedItem){
        mFragmentTransaction.remove(mEventListFragment);
        mFragmentTransaction.add(R.id.fragment_container, mEventDetailFragment);
    }
}
