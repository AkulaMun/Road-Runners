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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        if(checkConnectivity()) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, EventsListFragment.newInstance())
                        .commit();
            }
        }
        else{
            displayErrorDialog("No network Access!", "No Network Connection. Some functions might not work!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.findItem(R.id.event_create_button).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                displayCreateEvent(item);
                return false;
            }
        });
        return true;
    }

    public boolean checkConnectivity(){
        //Check for Internet Connection.
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null;
    }

    //CreateEventFragment is displayed by Menu Item click.
    public void displayCreateEvent(MenuItem clickedItem) {
        if(checkConnectivity()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, EventEditFragment.newInstance(null))
                    .addToBackStack("createEventFragment")
                    .commit();
        } else {
            displayErrorDialog("No network Access!", "No Network Connection. Some functions might not work!");
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

                    }
                })
                .show();
    }
}