package com.example.carcrashdetection;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class HomeTest {
    @Rule
    public FragmentTestRule<?, Home> fragmentTestRule =
            FragmentTestRule.create(Home.class);
    @Test
    public void recyclerview2() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.contactsView)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
    }
    @Test
    public void recyclerview3() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.contactsView)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
    }
    //fail as item doesnt exist
    @Test
    public void recyclerview4() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.eventsPlace)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));
    }
    @Test
    public void recyclerview() throws InterruptedException {
        Thread.sleep(2000);
        onView(withId(R.id.eventsPlace)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
    }
    public static ViewAction clickChildViewWithId(final int id) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return null;
            }

            @Override
            public String getDescription() {
                return "Click on a child view with specified id.";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View v = view.findViewById(id);
                v.performClick();
            }
        };
    }
    @Test
    public void checkItem() throws InterruptedException {
        Thread.sleep(2000);
        Espresso.onView(withId(R.id.contactsView)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.contactsView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.myCheckBox)));
    }
    @Test
    public void recyclervie5() throws InterruptedException {
        Thread.sleep(2000);
        Espresso.onView(withId(R.id.eventsPlace)).perform(RecyclerViewActions.scrollToPosition(0));
        onView(withId(R.id.eventsPlace)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickChildViewWithId(R.id.startsCar)));
    }
    @Test
    public void clickButton() throws Exception {
        Thread.sleep(2000);
        RecyclerView recyclerView = fragmentTestRule.getFragment().getActivity().findViewById(R.id.eventsPlace);
        int itemcount = recyclerView.getAdapter().getItemCount();
        Espresso.onView(withId(R.id.eventsPlace)).perform(RecyclerViewActions.actionOnItemAtPosition(itemcount-1, click()));
    }
    @Test
    public void recyclerview1() throws InterruptedException {
        Thread.sleep(2000);
        RecyclerView recyclerView = fragmentTestRule.getFragment().getActivity().findViewById(R.id.contactsView);
        int itemcount = recyclerView.getAdapter().getItemCount();
        Espresso.onView(withId(R.id.contactsView)).perform(RecyclerViewActions.scrollToPosition(itemcount-1));
        onView(withId(R.id.contactsView)).perform(RecyclerViewActions.actionOnItemAtPosition(itemcount-1,click()));

    }
    @Test
    public void findItem() throws InterruptedException {
        Thread.sleep(2000);
        onView(ViewMatchers.withId(R.id.eventsPlace)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        String  itemval  = "Mercedes";
        onView(withText(itemval)).check(matches(isDisplayed()));
    }

}