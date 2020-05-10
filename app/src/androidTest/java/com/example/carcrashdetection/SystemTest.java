package com.example.carcrashdetection;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class SystemTest {

    @Rule
    public ActivityTestRule<Login> mActivityTestRule = new ActivityTestRule<>(Login.class);

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.SEND_SMS",
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION",
                    "android.permission.FOREGROUND_SERVICE",
                    "android.permission.SYSTEM_ALERT_WINDOW");

    @Test
    public void systemTest() {
        pressBack();

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("gavinsmyth19@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText(""), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatTextView = onView(
                allOf(withId(R.id.create), withText("New Here? Create Account"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                6),
                        isDisplayed()));
        appCompatTextView.perform(click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.nameReg),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("ai"), closeSoftKeyboard());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.emailReg),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("a@gmail.com"), closeSoftKeyboard());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.passwordReg),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatEditText8.perform(replaceText("1234567"), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.phoneReg),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                6),
                        isDisplayed()));
        appCompatEditText9.perform(replaceText("08612132"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatEditText10 = onView(
                allOf(withId(R.id.phoneReg), withText("08612132"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                6),
                        isDisplayed()));
        appCompatEditText10.perform(replaceText("0861213246"));

        ViewInteraction appCompatEditText11 = onView(
                allOf(withId(R.id.phoneReg), withText("0861213246"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                6),
                        isDisplayed()));
        appCompatEditText11.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.buttonReg), withText("Register"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                7),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        2),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction appCompatEditText12 = onView(
                allOf(withId(R.id.makeOfCar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText12.perform(replaceText("HonDa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText13 = onView(
                allOf(withId(R.id.typeOfCar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText13.perform(replaceText("Saloon"), closeSoftKeyboard());

        ViewInteraction appCompatEditText14 = onView(
                allOf(withId(R.id.yearOfCar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText14.perform(replaceText("2007"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.addCar), withText("Add Car"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction navigationMenuItemView2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        1),
                        isDisplayed()));
        navigationMenuItemView2.perform(click());

        ViewInteraction appCompatEditText15 = onView(
                allOf(withId(R.id.nameContact),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText15.perform(replaceText("Gavin"), closeSoftKeyboard());

        ViewInteraction appCompatEditText16 = onView(
                allOf(withId(R.id.phoneContact),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText16.perform(replaceText("0861213246"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.uploadContact), withText("Add Contact"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction navigationMenuItemView3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        3),
                        isDisplayed()));
        navigationMenuItemView3.perform(click());

        ViewInteraction linearLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.contactsView),
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        6)),
                        0),
                        isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatEditText17 = onView(
                allOf(withId(R.id.updateName), withText("Gavin"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText17.perform(replaceText("Gavin Smyth"));

        ViewInteraction appCompatEditText18 = onView(
                allOf(withId(R.id.updateName), withText("Gavin Smyth"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText18.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.Update), withText("Update"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatTextView2 = onView(
                allOf(withId(R.id.year), withText("2007"),
                        childAtPosition(
                                allOf(withId(R.id.eventParent),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                3),
                        isDisplayed()));
        appCompatTextView2.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.eventsPlace),
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        2)),
                        0),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction appCompatEditText19 = onView(
                allOf(withId(R.id.updateMake), withText("HonDa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText19.perform(replaceText("Hondaa"));

        ViewInteraction appCompatEditText20 = onView(
                allOf(withId(R.id.updateMake), withText("Hondaa"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText20.perform(closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.updateCar), withText("Update Car"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatCheckBox = onView(
                allOf(withId(R.id.myCheckBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.outer),
                                        2),
                                0),
                        isDisplayed()));
        appCompatCheckBox.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withId(R.id.startsCar), withText("Stop Car"),
                        childAtPosition(
                                allOf(withId(R.id.eventParent),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withId(R.id.startsCar), withText("Start Car"),
                        childAtPosition(
                                allOf(withId(R.id.eventParent),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withId(R.id.startsCar), withText("Stop Car"),
                        childAtPosition(
                                allOf(withId(R.id.eventParent),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton8.perform(click());

        ViewInteraction linearLayout3 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.contactsView),
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        6)),
                        0),
                        isDisplayed()));
        linearLayout3.perform(click());

        ViewInteraction appCompatButton9 = onView(
                allOf(withId(R.id.DeleteContact), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton9.perform(click());

        ViewInteraction linearLayout4 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.eventsPlace),
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        2)),
                        0),
                        isDisplayed()));
        linearLayout4.perform(click());

        ViewInteraction appCompatButton10 = onView(
                allOf(withId(R.id.deleteCar), withText("Delete Car"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                6),
                        isDisplayed()));
        appCompatButton10.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.profilepic),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.navigation_header_container),
                                        0),
                                0),
                        isDisplayed()));
        appCompatImageView.perform(click());

        ViewInteraction appCompatImageButton5 = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton5.perform(click());

        ViewInteraction navigationMenuItemView4 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        6),
                        isDisplayed()));
        navigationMenuItemView4.perform(click());

        ViewInteraction appCompatEditText21 = onView(
                allOf(withId(R.id.email),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText21.perform(replaceText("a@"), closeSoftKeyboard());

        ViewInteraction appCompatEditText22 = onView(
                allOf(withId(R.id.email), withText("a@"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText22.perform(click());

        ViewInteraction appCompatEditText23 = onView(
                allOf(withId(R.id.email), withText("a@"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText23.perform(replaceText("a@gmail.com"));

        ViewInteraction appCompatEditText24 = onView(
                allOf(withId(R.id.email), withText("a@gmail.com"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText24.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText25 = onView(
                allOf(withId(R.id.password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText25.perform(replaceText("1234567"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton11 = onView(
                allOf(withId(R.id.button), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton11.perform(click());

        ViewInteraction appCompatImageButton6 = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton6.perform(click());

        ViewInteraction navigationMenuItemView5 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        2),
                        isDisplayed()));
        navigationMenuItemView5.perform(click());

        ViewInteraction appCompatEditText26 = onView(
                allOf(withId(R.id.makeOfCar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText26.perform(replaceText("h"), closeSoftKeyboard());

        ViewInteraction appCompatEditText27 = onView(
                allOf(withId(R.id.typeOfCar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText27.perform(replaceText("t"), closeSoftKeyboard());

        ViewInteraction appCompatEditText28 = onView(
                allOf(withId(R.id.yearOfCar),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                4),
                        isDisplayed()));
        appCompatEditText28.perform(replaceText("7"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton12 = onView(
                allOf(withId(R.id.addCar), withText("Add Car"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                5),
                        isDisplayed()));
        appCompatButton12.perform(click());

        ViewInteraction appCompatImageButton7 = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton7.perform(click());

        ViewInteraction navigationMenuItemView6 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        1),
                        isDisplayed()));
        navigationMenuItemView6.perform(click());

        ViewInteraction appCompatEditText29 = onView(
                allOf(withId(R.id.nameContact),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText29.perform(replaceText("g"), closeSoftKeyboard());

        ViewInteraction appCompatEditText30 = onView(
                allOf(withId(R.id.phoneContact),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText30.perform(replaceText("0861213246"), closeSoftKeyboard());

        pressBack();

        ViewInteraction appCompatButton13 = onView(
                allOf(withId(R.id.uploadContact), withText("Add Contact"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_Container),
                                        0),
                                4),
                        isDisplayed()));
        appCompatButton13.perform(click());

        ViewInteraction appCompatImageButton8 = onView(
                allOf(withContentDescription("Open Navigation Drawer"),
                        childAtPosition(
                                allOf(withId(R.id.toolbar),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton8.perform(click());

        ViewInteraction navigationMenuItemView7 = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.design_navigation_view),
                                childAtPosition(
                                        withId(R.id.nav_view),
                                        0)),
                        3),
                        isDisplayed()));
        navigationMenuItemView7.perform(click());

        ViewInteraction appCompatCheckBox2 = onView(
                allOf(withId(R.id.myCheckBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.outer),
                                        2),
                                0),
                        isDisplayed()));
        appCompatCheckBox2.perform(click());

        ViewInteraction appCompatCheckBox3 = onView(
                allOf(withId(R.id.myCheckBox),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.outer),
                                        2),
                                0),
                        isDisplayed()));
        appCompatCheckBox3.perform(click());

        ViewInteraction appCompatButton14 = onView(
                allOf(withId(R.id.startsCar), withText("Start Car"),
                        childAtPosition(
                                allOf(withId(R.id.eventParent),
                                        childAtPosition(
                                                withClassName(is("android.widget.LinearLayout")),
                                                0)),
                                4),
                        isDisplayed()));
        appCompatButton14.perform(click());

        ViewInteraction appCompatButton15 = onView(
                allOf(withId(R.id.stop), withText("Turn Off"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.alert),
                                        0),
                                3),
                        isDisplayed()));
        appCompatButton15.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
