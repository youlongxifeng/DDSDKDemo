package com.dd.sdk.common;

import android.content.Context;
import android.content.Intent;

import com.dd.sdk.listener.DDListener;
import com.dd.sdk.net.RequestError;
import com.dd.sdk.net.volley.DDVolley;
import com.dd.sdk.netbean.AccessToken;
import com.dd.sdk.netbean.BaseResponse;
import com.dd.sdk.service.MainService;
import com.dd.sdk.tools.GsonUtils;
import com.dd.sdk.tools.LogUtils;
import com.google.mgson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Timer;
import java.util.TimerTask;

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
    private final static String TAG=NetworkState.class.getSimpleName();
    private static TimerTask clockTask;
    private static Timer clockTimer;
    private static final byte[] mLock = new byte[] {};
    private Context mContext;
    private String mSdkAccessKey, mSdkSecretKey;

    public NetworkState(Context context,String sdkAccessKey, String sdkSecretKey) {
        this.mContext = context;
        this.mSdkAccessKey=sdkAccessKey;
        this.mSdkSecretKey=sdkSecretKey;

    }

    @Override
    public void run() {

    }

    /**
     * tokencheck检查
     */
    public  void tokenCheck(){
        stopClock();
        LogUtils.i(TAG, "开始轮训");
        clockTask = new TimerTask(){

            @Override
            public void run() {
                LogUtils.i(TAG, "init onResponse  mSdkAccessKey==" + mSdkAccessKey+"  mSdkSecretKey="+mSdkSecretKey);
                DDVolley.accessToken(mSdkAccessKey, mSdkSecretKey, new DDListener<BaseResponse<AccessToken>, RequestError>() {
                    @Override
                    public void onResponse(BaseResponse<AccessToken> object) {
                        LogUtils.i(TAG, "init onResponse  response==" + object);
                        BaseResponse<AccessToken> response = new BaseResponse<AccessToken>();
                        try {
                            Type t = new TypeToken<BaseResponse<AccessToken>>() {
                            }.getType();
                            response = GsonUtils.getObject(object.toString(), t, response);
                            LogUtils.i(TAG, "init onResponse  response==" + response);
                            if (response.isSuccess()) {
                                AccessToken     accessToken = response.data;// GsonUtils.getObject(response.data.toString(), AccessToken.class);
                                TokenPrefer.saveConfig(mContext, accessToken);
                            }
                            LogUtils.i(TAG, "baseResponse==" + response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtils.i(TAG, "init onResponse  response=e=" + e);
                        }
                    }

                    @Override
                    public void onErrorResponse(RequestError error) {
                        LogUtils.i(TAG, "init  onErrorResponse =error=" + error);

                    }
                });
            }

        };
        clockTimer = new Timer();
        //delay为long,period为long：从现在起过delay毫秒以后，每隔period毫秒执行一次。
        clockTimer.schedule(clockTask, 0, 60*60*1000);
    }

    /**
     * 停止时钟
     */
    public void stopClock(){
        LogUtils.i(TAG, "停止轮训");
        if(clockTask != null){
            clockTask.cancel();
            clockTask = null;
        }
        if(clockTimer != null){
            clockTimer.cancel();
            clockTimer = null;
        }
    }

    /**
     * 释放资源
     */
    public void release() {
        if (mContext != null) {
            Intent intent = new Intent(mContext, MainService.class);
            intent.putExtra("data", true);
            mContext.stopService(intent);
        }

        stopClock();
    }
}
