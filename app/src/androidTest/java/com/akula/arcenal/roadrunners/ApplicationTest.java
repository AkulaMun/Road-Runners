package com.akula.arcenal.roadrunners;

import android.app.Application;
import android.test.AndroidTestCase;

import com.akula.arcenal.roadrunners.Model.Event;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {
    public ApplicationTest() {

        //TEST 1: DATA MODEL -> EVENT
        //Preparation for Test
        GregorianCalendar referenceCalendar = new GregorianCalendar(2016, 11, 22, 11, 30);
        referenceCalendar.getTime();
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
}