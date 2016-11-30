package com.rzagorski.callbackconverterfactory.gson.retrofit;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.mockito.stubbing.Stubber;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Robert Zagórski on 2016-11-30.
 */

public class TestUtil {

    public static Stubber countDownLatch(CountDownLatch latch) {
        return Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                latch.countDown();
                return null;
            }
        });
    }
}
