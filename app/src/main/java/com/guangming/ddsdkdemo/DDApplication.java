package com.guangming.ddsdkdemo;

import android.app.Application;
import android.content.Context;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.guangming.ddsdkdemo
 * @class describe
 * @time 2018/6/11 14:34
 * @change
 * @class describe
 */

public class DDApplication extends Application  {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        //DDSDK.init(mContext, "f2a9d153188d87e18adc233ca8ee30da", "564f939a8f8a5befa67d62bdf79e6fa5", "test20160822001", "10.0.2.152", 8888, this);


    }

}
