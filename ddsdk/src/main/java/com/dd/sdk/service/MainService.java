package com.dd.sdk.service;


import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dd.sdk.config.NetConfig;
import com.dd.sdk.netty.NettyClient;
import com.dd.sdk.netty.NettyListener;
import com.dd.sdk.tools.LogUtils;

import java.util.List;
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

public class MainService extends Service implements NettyListener, Callback {
    private final static String TAG = MainService.class.getSimpleName();
    private final static int QUERY_PROCESS_SURVIVING = 1;
    public final static String CONFIG_BEAN = "config_bean";
    public final static String PACKAGE_NAME = "package_name";
    public final static String GUID_NAME = "guid_name";
    private boolean isbind = false;
    CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();
    ListenerManagerImpl mListenerManager;
    NettyClient mNettyClient;
    private String mPacageName = null;
    private String mGuid;
    HandlerThread mHandlerThread;
    Handler mMainHandler;

    @Override
    public void onCreate() {
        super.onCreate();
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
        mPacageName = bundle.getString(MainService.PACKAGE_NAME);
        mGuid = bundle.getString(MainService.GUID_NAME);
        if (netConfig != null) {
            mNettyClient = new NettyClient(this, netConfig.getdPort(), netConfig.getDomain(), mGuid);
            mNettyClient.connect();
        } else {

        }
        mHandlerThread = new HandlerThread("DDService");
        mHandlerThread.setDaemon(true);
        mHandlerThread.start();
        mMainHandler = new Handler(mHandlerThread.getLooper(), this);
        mMainHandler.sendEmptyMessage(QUERY_PROCESS_SURVIVING);
        isbind = true;
        Log.i(TAG, "======onBind=====netConfig=");
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
            Log.i(TAG, "======onMessageResponse=====statusCode=" + statusCode);
            if (isbind) {
                mBundle.onServiceStatusConnectChanged(statusCode);
                mNettyClient.reconnect();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "======ononUnbind======");
        isbind = false;
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "======onDestroy======");
        if (mNettyClient != null) {
            mNettyClient.close();
        }
    }

    private ICommandManager mBundle = new ICommandManager.Stub() {

        @Override
        public void onMessageResponse(String msg) throws RemoteException {
            mListenerManager.onMessageResponse(msg);
            LogUtils.i(TAG, "onMessageResponse================" + msg);
        }

        @Override
        public void onServiceStatusConnectChanged(int statusCode) throws RemoteException {
            mListenerManager.onServiceStatusConnectChanged(statusCode);
            LogUtils.i(TAG, "onServiceStatusConnectChanged================" + statusCode);
        }

        @Override
        public void registerListener(IOnCommandListener listener) throws RemoteException {
            LogUtils.i(TAG, "registerListener================");
            mListenerManager.registerListener(listener);
        }

        @Override
        public void unRegisterListener(IOnCommandListener listener) throws RemoteException {
            LogUtils.i(TAG, "unRegisterListener================");
            mListenerManager.unRegisterListener(listener);
        }
    };

    /**
     * 轮训当前线程是否还活着
     */
    private void querySpecailPIDRunningAppInfo() {
        //获得ActivityManager服务的对象
        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> listOfProcesses = mActivityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : listOfProcesses) {
            LogUtils.e("Process Running :" + process.processName + "  listOfProcesses=" + listOfProcesses.size());
            if (mPacageName != null && !process.processName.contains(mPacageName)) {

            } else {

            }
        }
        //mMainHandler.sendEmptyMessageAtTime(QUERY_PROCESS_SURVIVING, 500);
    }


    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case QUERY_PROCESS_SURVIVING:
                // querySpecailPIDRunningAppInfo();
                break;
            default:
                break;
        }
        return false;
    }
}
