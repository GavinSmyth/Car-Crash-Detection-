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

public class updateDeleteTest {

    @Rule
    public ActivityTestRule<updateDelete> activity = new ActivityTestRule<>(updateDelete.class);
    @Test
    public void clickButton(){
        onView(withId(R.id.DeleteContact)).perform(ViewActions.click());

    }
    @Test
    public void clickButton1(){
        onView(withId(R.id.Update)).perform(ViewActions.click());

    }
    @Test
    public void clickButton2(){
        onView(withId(R.id.cancel)).perform(ViewActions.click());
    }
    @Test
    public void changeName(){
        onView(withId(R.id.updateName)).perform(typeText("Gavin"));
    }
    @Test
    public void chnageNumber(){
        onView(withId(R.id.updateNumber)).perform(typeText("0861326326"));
    }
    @Test
    public void checkText(){
        onView(withText("Update Car")).check(matches(isDisplayed()));
    }

    //failed as it need the key to be passed through
    @Test
    public void updateContact() throws InterruptedException {
        // Button button = (Button) fragmentTestRule.getFragment().getActivity().findViewById(R.id.uploadCar);
        onView(withId(R.id.updateName)).perform(typeText("Gavin"));
        onView(withId(R.id.updateNumber)).perform(typeText("0861326326"));
        Thread.sleep(250);
        onView(withId(R.id.Update)).perform(ViewActions.click());

    }

}