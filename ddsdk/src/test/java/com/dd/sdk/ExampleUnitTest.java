package com.dd.sdk;


import android.Manifest;

import com.dd.sdk.tools.PermissionUtil;

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
        String str = "eyJyZXF1ZXN0X2lkIjoidGVzdDIwMTYwODIyMDAxIiwiY21kIjoiaGVhcnRfYmVhdCJ9*";
        System.out.println(str.endsWith("*"));


    }
}