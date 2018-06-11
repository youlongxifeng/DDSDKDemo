package com.dd.sdk.common;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.dd.sdk.service.MainService;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.common
 * @class describe
 * @time 2018/6/11 11:46
 * @change
 * @class describe
 */

public class NetworkState implements Runnable {
    private Context mContext;

    public NetworkState(Application context) {
        mContext = context;
        /*   mNetState = NETWORK_UNKNOW;
        SipConnTime = SystemClock.elapsedRealtime();
        mDiscount = 1;*/
    }

    @Override
    public void run() {

    }

    /**
     * 释放资源
     */
    public void release() {
        Intent intent = new Intent(mContext, MainService.class);
        intent.putExtra("data", true);
        mContext.stopService(intent);
    }
}
