package com.dd.sdk.net.volley;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dd.sdk.DDSDK;
import com.dd.sdk.tools.LogUtils;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.volley
 * @class describe
 * @time 2018/6/13 20:58
 * @change
 * @class describe
 */

public class DDVolley {
    private static RequestQueue mNetWorkRequest;

    public static RequestQueue addRequestQueue(Request request) {
        if (mNetWorkRequest == null) {
            LogUtils.i("=========="+(DDSDK.getinstance().getContext()!=null));
            mNetWorkRequest = Volley.newRequestQueue(DDSDK.getinstance().getContext());
        }
        mNetWorkRequest.add(request);
        return mNetWorkRequest;
    }
    public static void stop() {
        if (mNetWorkRequest != null) {
            mNetWorkRequest.stop();
        }

    }
}
