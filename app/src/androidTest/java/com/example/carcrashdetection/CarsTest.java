package com.example.carcrashdetection;

import androidx.test.espresso.action.ViewActions;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class CarsTest {
    @Rule
    public FragmentTestRule<?, Cars> fragmentTestRule =
            FragmentTestRule.create(Cars.class);
    @Test
    public void clickButton(){
        onView(withId(R.id.addCar)).perform(ViewActions.click());
    }
    @Test
    public void typeData(){
        onView(withId(R.id.makeOfCar)).perform(typeText("Honda"));
    }
    @Test
    public void typeData1(){
        onView(withId(R.id.typeOfCar)).perform(typeText("Saloon"));
    }
    @Test
    public void typeData3(){
        onView(withId(R.id.yearOfCar)).perform(typeText("2009"));
    }
    @Test
    public void checkText(){
        onView(withText("Add New Car")).check(matches(isDisplayed()));
    }
    @Test
    public void changeText(){
        onView(withId(R.id.yearOfCar)).perform(typeText("Steve"));
    }
    @Test
    public void addCar() throws InterruptedException {
        onView(withId(R.id.makeOfCar)).perform(typeText("Honda"));
        onView(withId(R.id.typeOfCar)).perform(typeText("Saloon"));
        onView(withId(R.id.yearOfCar)).perform(typeText("2007"));
        Thread.sleep(250);
        onView(withId(R.id.addCar)).perform(ViewActions.click());
    }


}