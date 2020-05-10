package com.example.carcrashdetection;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class alertTest {
    @Rule
    public ActivityTestRule<alert> activity = new ActivityTestRule<>(alert.class);

    @Test
    public void clickButton(){
        onView(withId(R.id.stop)).perform(ViewActions.click());
    }

}