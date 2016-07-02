package com.example.islam.gameoflife.surfaceviewgame.bussiness;

import android.support.test.InstrumentationRegistry;

import com.example.islam.gameoflife.application.GameContext;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.*;

/**
 * Created by islam on 7/3/16.
 */
public class LogicTest {
    Logic logic;

    @Before
    public void setUp() {
        logic = new Logic((GameContext) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext());
    }
    @Test
    public void testTickObservableNotNull(){
        Observable observable = logic.tick();
        assertNotNull(observable);
    }
    @Test
    public void testTickObservableNoErrors(){
        TestSubscriber testSubscriber = new TestSubscriber<>();
        Observable observable = logic.tick();
        observable.subscribe(testSubscriber);
        testSubscriber.assertNoErrors();
    }

}