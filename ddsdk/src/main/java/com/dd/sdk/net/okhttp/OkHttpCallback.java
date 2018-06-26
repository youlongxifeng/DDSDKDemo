package com.dd.sdk.net.okhttp;

import java.io.IOException;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.okhttp
 * @class describe
 * @time 2018/6/26 13:55
 * @change
 * @class describe
 */

public interface OkHttpCallback {
    void onOkFailure(IOException e);
    void onOkResponse(String s);
}
