package com.example.islam.gameoflife.gamemain.view;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;

import com.example.islam.gameoflife.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.AnyOf.anyOf;

/**
 * Created by islam on 7/2/16.
 */
public class GameActivityTest {
    @Rule
    public ActivityTestRule<GameActivity> activityRule = new ActivityTestRule<>(
            GameActivity.class,
            true,     // initialTouchMode
            false);   // launchActivity. False so we can customize the intent per test method

    @Before
    public void setUp(){

    }
    @Test
    public void testGameActivityViewsAreVisible() {
    activityRule.launchActivity(new Intent());
        onView(
                anyOf(withId(R.id.game_view))
        ).check(matches(isDisplayed()));
        onView(
                anyOf(withId(R.id.start_stop_button))
        ).check(matches(isDisplayed()));
    }
    @Test
    public void checkStartStopButtonTextISDisplayedCorrectlywhenClickIsPerformed(){
        onView(
                anyOf(withId(R.id.start_stop_button))
        ).perform(ViewActions.click());
        onView(
                anyOf(withId(R.id.start_stop_button))
        ).check(matches(withText(InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext().getString(R.string.stop_button_text))));
        onView(
                anyOf(withId(R.id.start_stop_button))
        ).perform(ViewActions.click());
        onView(
                anyOf(withId(R.id.start_stop_button))
        ).check(matches(withText(InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext().getString(R.string.start_button_txt))));
    }
}