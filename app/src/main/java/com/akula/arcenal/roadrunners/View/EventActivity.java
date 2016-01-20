package com.akula.arcenal.roadrunners.view;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayEventList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mCreateEventMenuItem = menu.findItem(R.id.event_create_button);
        mCreateEventMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                displayCreateEvent(item);
                return false;
            }
        });
        return true;
    }

    public boolean checkConnectivity(){
        //Check for Internet Connection. Closes the app with an error dialog if internet is unavailable.
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }

    public void displayEventList() {
        if(checkConnectivity()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EventListFragment.getInstance())
                    .commit();
        }
        else{
            displayErrorDialog("Error!", "No Network Connection. Check your settings!");
        }
    }

    //CreateEventFragment is displayed by Menu Item click.
    public void displayCreateEvent(MenuItem clickedItem) {
        if(checkConnectivity()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EventCreateFragment.newInstance())
                    .addToBackStack("createEventFragment")
                    .commit();
        }
        else{
            displayErrorDialog("Error!", "No Network Connection. Check your settings!");
        }
    }

    private void displayErrorDialog(String title, String message) {
        //Line below is very picky on context. getApplicationContext and getBaseContext crashes it.
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }
}
