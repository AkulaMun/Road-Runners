package com.akula.arcenal.roadrunners.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.akula.arcenal.roadrunners.R;

public class EventActivity extends AppCompatActivity {

    FrameLayout mFragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mFragmentContainer = (FrameLayout)findViewById(R.id.fragment_container);
    }
}
