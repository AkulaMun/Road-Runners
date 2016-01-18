package com.akula.arcenal.roadrunners.view;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.akula.arcenal.roadrunners.R;

public class EventActivity extends AppCompatActivity {

    private MenuItem mCreateEventMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Log.w("Activity Restart.", "Activity Restarted!");
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

    public boolean checkConnectivity(){
        //Check for Internet Connection. Closes the app with an error dialog if internet is unavailable.
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork == null) {
            return false;
        }
        else{
            return true;
        }
    }

    public void displayEventList(){
        if(checkConnectivity()){
            clearBackStack();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            EventListFragment eventListFragment = EventListFragment.newInstance(new FragmentCommunicationListener() {
                @Override
                public void OnFragmentCommunicate(String message) {
                    if(message.contentEquals("Connection Error")){
                        displayErrorDialog("Error", message + "! Please Try Again Later.");
                    }
                    else{
                        displayEventList();
                    }
                }
            });
            fragmentTransaction.replace(R.id.fragment_container, eventListFragment);
            fragmentTransaction.commit();
        }
        else{
            displayErrorDialog("Error!", "No Network Connection. Check your settings!");
        }
    }

    public void displayCreateEvent(MenuItem clickedItem) {
        if(checkConnectivity()) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            EventCreateFragment createEventFragment = EventCreateFragment.newInstance(new FragmentCommunicationListener() {
                @Override
                public void OnFragmentCommunicate(String message) {
                    if(!message.contentEquals("Error")) {
                        displayEventList();
                    }
                }
            });
            fragmentTransaction.replace(R.id.fragment_container, createEventFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else{
            displayErrorDialog("Error!", "No Network Connection. Check your settings!");
        }
    }

    private void displayErrorDialog(String title, String message) {
        //Line below is very picky on context. getApplicationContext and getBaseContext crashes it.
        AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
        errorDialog.setTitle(title);
        errorDialog.setMessage(message);
        errorDialog.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        errorDialog.show();
    }

    //Clears the back navigation.
    private void clearBackStack() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack(fragmentManager.getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
