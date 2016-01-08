package com.akula.arcenal.roadrunners;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;
import android.util.Log;

import com.akula.arcenal.roadrunners.Controller.EventController;
import com.akula.arcenal.roadrunners.Controller.ParseController;
import com.akula.arcenal.roadrunners.Model.Event;
import com.parse.Parse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {

    public ApplicationTest() {

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //Parse.initialize(getContext(), "X92n2Y7bH8mB3sjTDSe6vrNMIfYVEIrSiipWSiO2", "lo9StIG2u95JM7pvVJKKxsLpIpywvByAaPJ5KL1j");
    }

    public void testEventModel(){
        //TEST 1: DATA MODEL -> EVENT
        //Preparation for Test
        GregorianCalendar referenceCalendar = new GregorianCalendar(2016, 11, 22, 11, 30);
        String expectedName = "Viper's Nest";
        String expectedLocation = "Kuala Lumpur";
        String expectedOrganizer = "Viper Venom Pakour";
        double expectedDistance = 42;
        String expectedDate = referenceCalendar.getTime().toString();
        String expectedID = "VipKuaVip-" + expectedDate + "42";

        Event test = new Event("Viper's Nest", "Kuala Lumpur", "Viper Venom Pakour", 42, referenceCalendar.getTime());

        assertEquals(expectedName, test.getName());
        assertEquals(expectedLocation, test.getLocation());
        assertEquals(expectedOrganizer, test.getOrganizer());
        assertEquals(expectedDistance, test.getDistance());
        assertEquals(expectedDate, test.getDate().toString());
    }


/*
    public void testParseController(){
        //TEST 2: PARSECONTROLLER DIRECT INVOKE -> SAVING EVENT
        ParseController parseController = ParseController.getInstance(getContext());

        GregorianCalendar referenceCalendar = new GregorianCalendar(2016, 11, 22, 11, 30);
        Event test = new Event("Viper's Nest", "Kuala Lumpur", "Viper Venom Pakour", 42, referenceCalendar.getTime());

        parseController.saveEvent(test);
    }
    */

    public void testDate(){
        GregorianCalendar referenceCalendar = new GregorianCalendar(2016, 10, 22, 11, 30);
        GregorianCalendar testDate = new GregorianCalendar(2017, 10, 13, 10, 55);
        String dateString = referenceCalendar.getTime().toString();
        SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MMMM-dd'T'HH:mm:ss.SSS'Z'");
        String formatted = isoDateFormat.format(testDate.getTime());
        Log.e("????", formatted);
    }
}