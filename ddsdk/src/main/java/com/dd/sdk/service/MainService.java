package com.dd.sdk.service;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.service
 * @class describe
 * @time 2018/6/11 14:10
 * @change
 * @class describe
 */

public class MainService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
