package com.example.carcrashdetection;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class updateDeleteCarsTest {
    @Rule
    public ActivityTestRule<updateDeleteCars> activity = new ActivityTestRule<>(updateDeleteCars.class);
    @Test
    public void clickButton(){
        // Button button = (Button) fragmentTestRule.getFragment().getActivity().findViewById(R.id.uploadCar);
        onView(withId(R.id.updateCar)).perform(ViewActions.click());
    }

    @Test
    public void pressbtn(){
        onView(withId(R.id.deleteCar)).perform(ViewActions.click());
    }
    @Test
    public void pressbtn1(){
        onView(withId(R.id.cancelCar)).perform(ViewActions.click());
    }
    @Test
    public void typeData1(){
        onView(withId(R.id.updateType)).perform(typeText("Saloon"));
    }
    @Test
    public void typeData2(){
        onView(withId(R.id.updateMake)).perform(typeText("Saloon"));
    }
    @Test
    public void typeData3(){
        onView(withId(R.id.updateYear)).perform(typeText("2009"));
    }
    @Test
    public void checkText(){
        onView(withText("Update Car")).check(matches(isDisplayed()));
    }
    @Test
    public void updateCar() throws InterruptedException {
        // Button button = (Button) fragmentTestRule.getFragment().getActivity().findViewById(R.id.uploadCar);
        onView(withId(R.id.updateMake)).perform(typeText("Honda"));
        onView(withId(R.id.updateType)).perform(typeText("Saloon"));
        onView(withId(R.id.updateYear)).perform(typeText("2009"));
        Thread.sleep(250);
        onView(withId(R.id.updateCar)).perform(ViewActions.click());

    }



}