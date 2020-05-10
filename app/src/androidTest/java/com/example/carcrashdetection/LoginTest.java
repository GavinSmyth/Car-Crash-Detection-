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

public class LoginTest {

    @Rule
    public ActivityTestRule<Login> activity = new ActivityTestRule<>(Login.class);

    @Test
    public void clickButton(){
        onView(withId(R.id.button)).perform(ViewActions.click());
    }
    @Test
    public void typeData(){
        onView(withId(R.id.email)).perform(typeText("gavinsmyth1998@gmail.com"));
    }
    @Test
    public void checkText(){
        onView(withText("Login")).check(matches(isDisplayed()));
    }
    @Test
    public void createAccount(){
        onView(withId(R.id.create)).perform(ViewActions.click());
    }
    @Test
    public void changeText(){
        onView(withId(R.id.password)).perform(typeText("Steve"));
    }
    //place keyboard down
    @Test
    public void LoginUser() throws InterruptedException {
        // Button button = (Button) fragmentTestRule.getFragment().getActivity().findViewById(R.id.uploadCar);
        onView(withId(R.id.email)).perform(typeText("gavinsmyth1998@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("Steve"));
        Thread.sleep(250);
        onView(withId(R.id.button)).perform(ViewActions.click());
    }



}