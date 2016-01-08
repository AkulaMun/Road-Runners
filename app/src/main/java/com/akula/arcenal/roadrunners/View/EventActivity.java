package com.akula.arcenal.roadrunners.View;


import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.akula.arcenal.roadrunners.R;

public class EventActivity extends AppCompatActivity {

    private FrameLayout mFragmentContainer;
    private EventListFragment mEventListFragment;
    private FragmentTransaction mFragmentTransaction;

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
}
