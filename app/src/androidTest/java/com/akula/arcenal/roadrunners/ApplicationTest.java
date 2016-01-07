package com.akula.arcenal.roadrunners;

import android.content.Context;
import android.test.AndroidTestCase;
import android.test.InstrumentationTestCase;

import com.akula.arcenal.roadrunners.Controller.ParseController;
import com.akula.arcenal.roadrunners.Model.Event;
import com.parse.Parse;

import java.util.GregorianCalendar;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends AndroidTestCase {
    public ApplicationTest() {

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

    public void testParseController(){
        //TEST 2: PARSECONTROLLER DIRECT INVOKE -> SAVING EVENT
        Context testContext = null;

        while(testContext == null){
            testContext = this.getContext();
        }
        Parse.initialize(testContext, "X92n2Y7bH8mB3sjTDSe6vrNMIfYVEIrSiipWSiO2", "lo9StIG2u95JM7pvVJKKxsLpIpywvByAaPJ5KL1j");

        ParseController parseController = ParseController.getInstance(testContext);

        GregorianCalendar referenceCalendar = new GregorianCalendar(2016, 11, 22, 11, 30);
        Event test = new Event("Viper's Nest", "Kuala Lumpur", "Viper Venom Pakour", 42, referenceCalendar.getTime());

        parseController.saveEvent(test);
    }
}