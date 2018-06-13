package com.dd.sdk.service;


import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dd.sdk.config.NetConfig;
import com.dd.sdk.netty.NettyClient;
import com.dd.sdk.netty.NettyListener;
import com.dd.sdk.tools.LogUtils;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.service
 * @class describe
 * @time 2018/6/11 14:10
 * @change
 * @class describe
 */

public class MainService extends Service implements NettyListener {
    private final static String TAG = MainService.class.getSimpleName();
    CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    ListenerManagerImpl mListenerManager;
    NettyClient mNettyClient;

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.init(null,true,true);
        mListenerManager = new ListenerManagerImpl();
        Log.i(TAG, "======onCreate=====");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (mNettyClient != null) {
            mNettyClient.close();
            mNettyClient = null;
        }
        Bundle bundle = intent.getExtras();
        NetConfig netConfig = (NetConfig) bundle.getSerializable(NetConfig.CONFIG_BEAN);
        if (netConfig != null) {
            mNettyClient = new NettyClient(this, netConfig.getdPort(), netConfig.getDomain());
            mNettyClient.connect();
        }else{

        }
        Log.i(TAG, "======onBind=====netConfig="+netConfig);
        return (IBinder) mBundle;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "======onStartCommand======");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onMessageResponse(Object msg) {
        try {

            mBundle.onMessageResponse((String) msg);

        } catch (RemoteException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void onServiceStatusConnectChanged(int statusCode) {

        try {
            Log.i(TAG, "======onMessageResponse=====statusCode="+statusCode);
            mBundle.onServiceStatusConnectChanged(statusCode);
            mNettyClient.reconnect();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNettyClient.close();
        Log.i(TAG, "======onDestroy======");

    }

    private ICommandManager mBundle = new ICommandManager.Stub() {

        @Override
        public void onMessageResponse(String msg) throws RemoteException {
            mListenerManager.onMessageResponse(msg);
        }

        @Override
        public void onServiceStatusConnectChanged(int statusCode) throws RemoteException {
            mListenerManager.onServiceStatusConnectChanged(statusCode);
        }

        @Override
        public void registerListener(IOnCommandListener listener) throws RemoteException {
            LogUtils.i(TAG,"registerListener================");
         mListenerManager.registerListener(listener);
        }

        @Override
        public void unRegisterListener(IOnCommandListener listener) throws RemoteException {
           mListenerManager.unRegisterListener(listener);
        }
    };
}
