package com.dd.sdk.service;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.service
 * @class describe
 * @time 2018/6/11 16:35
 * @change
 * @class describe
 */

public interface ListenerManager {

    void registerListener(IOnCommandListener listener);

    void unRegisterListener(IOnCommandListener listener);

    void onMessageResponse(  String msg);

    void onServiceStatusConnectChanged(int statusCode);
}
