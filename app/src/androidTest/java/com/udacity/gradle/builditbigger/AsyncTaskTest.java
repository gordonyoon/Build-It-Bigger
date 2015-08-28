package com.udacity.gradle.builditbigger;

import android.test.UiThreadTest;

import junit.framework.TestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import interfaces.EndpointResultListener;


public class AsyncTaskTest extends TestCase implements EndpointResultListener {
    AsyncJokeDisplayer asyncJokeDisplayer;
    CountDownLatch signal;
    String joke;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        asyncJokeDisplayer = new AsyncJokeDisplayer(this);
        signal = new CountDownLatch(1);
    }

    @UiThreadTest
    public void testRetrieveJoke() throws InterruptedException {
        asyncJokeDisplayer.showJoke();
        signal.await(30, TimeUnit.SECONDS);

        assertTrue("The joke was not retrieved", !joke.isEmpty());
    }

    @Override
    public void onJokeReceived(String joke) {
        signal.countDown();
        this.joke = joke;
    }
}


