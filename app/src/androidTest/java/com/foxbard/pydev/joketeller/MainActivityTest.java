package com.foxbard.pydev.joketeller;


import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.foxbard.pydev.joketeller.main.EndpointsAsyncTask;
import com.foxbard.pydev.joketeller.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by PY-DEV on 8/29/2016.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkList() {
        onView(withText("Hello")).check(ViewAssertions.matches(isDisplayed()));
    }



    @Test
    public void isStringNull() {

        onView(ViewMatchers.withId(R.id.bLaunchIntent)).perform(ViewActions.click());
        String data = "";

        try {
            EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask();
            endpointsAsyncTask.execute();
            data = endpointsAsyncTask.get(30, TimeUnit.SECONDS).getJQuestion();

        } catch (Exception e) {

        }
        assertNull("Results: Null", data);
    }


}