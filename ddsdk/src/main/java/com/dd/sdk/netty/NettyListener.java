package com.dd.sdk.netty;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netty
 * @class describe
 * @time 2018/6/11 14:41
 * @change
 * @class
 * @class describe netty事件监听
 */

public interface NettyListener {
    byte STATUS_CONNECT_SUCCESS = 1;

    byte STATUS_CONNECT_CLOSED = 0;

    byte STATUS_CONNECT_ERROR = 0;


    /**
     * 当接收到系统消息
     */
    void onMessageResponse(Object msg);

    /**
     * 当服务状态发生变化时触发
     */
    void onServiceStatusConnectChanged(int statusCode);
}
