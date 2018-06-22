package com.guangming.ddsdkdemo;

import com.dd.sdk.thread.ThreadManager;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        ThreadManager.getThreadPollProxy().execute(new Runnable() {
            @Override
            public void run() {
                System.out.print(Thread.currentThread().getName());
            }
        });
    }
}