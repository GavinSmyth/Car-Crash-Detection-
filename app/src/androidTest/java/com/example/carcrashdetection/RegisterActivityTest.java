package com.example.carcrashdetection;

import androidx.test.espresso.action.ViewActions;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class RegisterActivityTest {
    @Rule
    public ActivityTestRule<RegisterActivity> activity = new ActivityTestRule<>(RegisterActivity.class);

    @Test
    public void clickButton(){
        onView(withId(R.id.buttonReg)).perform(ViewActions.click());
    }
    @Test
    public void Cancel(){
        onView(withId(R.id.textReg)).perform(ViewActions.click());
    }
    @Test
    public void typeData(){
        onView(withId(R.id.emailReg)).perform(typeText("gavinsmyth1998@gmail.com"));
    }
    @Test
    public void typeData1(){
        onView(withId(R.id.nameReg)).perform(typeText("Gavin"));
    }
    @Test
    public void typeData2(){
        onView(withId(R.id.passwordReg)).perform(typeText("jwnfjf"));
    }
    @Test
    public void typeData3(){
        onView(withId(R.id.phoneReg)).perform(typeText("0861216574"));
    }
    @Test
    public void checkText(){
        onView(withText("Create New Account")).check(matches(isDisplayed()));
    }
    @Test
    public void Register() throws IOException, InterruptedException {
        // Button button = (Button) fragmentTestRule.getFragment().getActivity().findViewById(R.id.uploadCar);
        onView(withId(R.id.nameReg)).perform(typeText("jake"));
        onView(withId(R.id.emailReg)).perform(typeText("jake@gmail.com"));
        onView(withId(R.id.passwordReg)).perform(typeText("jwnfjf7kj"));
        onView(withId(R.id.phoneReg)).perform(typeText("08684738"));
        Thread.sleep(250);
        onView(withId(R.id.buttonReg)).perform(ViewActions.click());

    }


}