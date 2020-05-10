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

public class contactsTest {

    @Rule
    public FragmentTestRule<?, contacts> fragmentTestRule =
            FragmentTestRule.create(contacts.class);
    @Test
    public void clickButton(){
        onView(withId(R.id.uploadContact)).perform(ViewActions.click());

    }

    @Test
    public void typeData(){
        onView(withId(R.id.phoneContact)).perform(typeText("0861216448"));
    }
    @Test
    public void checkText(){
        onView(withText("Create New Contact")).check(matches(isDisplayed()));
    }
    @Test
    public void changeText(){
        // Button button = (Button) fragmentTestRule.getFragment().getActivity().findViewById(R.id.uploadCar);
        onView(withId(R.id.nameContact)).perform(typeText("Steve"));
    }
    @Test
    public void addContact() throws InterruptedException {
        // Button button = (Button) fragmentTestRule.getFragment().getActivity().findViewById(R.id.uploadCar);
        onView(withId(R.id.nameContact)).perform(typeText("Steve"));
        onView(withId(R.id.phoneContact)).perform(typeText("0861216448"));
        Thread.sleep(250);
        onView(withId(R.id.uploadContact)).perform(ViewActions.click());
    }


    }