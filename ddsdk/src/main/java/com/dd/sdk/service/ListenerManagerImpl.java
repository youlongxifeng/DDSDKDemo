package com.dd.sdk.service;

import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.dd.sdk.tools.LogUtils;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.service
 * @class describe
 * @time 2018/6/11 16:49
 * @change
 * @class describe
 */

public class ListenerManagerImpl implements ListenerManager {
    private final static String TAG = ListenerManagerImpl.class.getSimpleName();
    private RemoteCallbackList<IOnCommandListener> mOnPlayChangedListener;

    public ListenerManagerImpl() {
        mOnPlayChangedListener = new RemoteCallbackList<>();
    }


    @Override
    public void registerListener(IOnCommandListener listener) {
        LogUtils.i(TAG, "registerListener==");
        mOnPlayChangedListener.register(listener);
    }

    @Override
    public void unRegisterListener(IOnCommandListener listener) {
        mOnPlayChangedListener.unregister(listener);
    }

    @Override
    public void onMessageResponse(String msg) {
        int count = 0;
        try {
            count = mOnPlayChangedListener.beginBroadcast();

            for (int i = 0; i < count; i++) {
                LogUtils.i(TAG, "\n onMessageResponse==count=" + count+"  i="+i+" msg="+msg);
                mOnPlayChangedListener.getBroadcastItem(i).onMessageResponse(msg+"  i="+i);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            if (count > 0) {
                mOnPlayChangedListener.finishBroadcast();
            }
        }
    }

    @Override
    public void onServiceStatusConnectChanged(int statusCode) {
        int count = 0;
        try {
            count = mOnPlayChangedListener.beginBroadcast();
            LogUtils.i(TAG, "onServiceStatusConnectChanged==count=" + count);
            for (int i = 0; i < count; i++) {
                mOnPlayChangedListener.getBroadcastItem(i).onServiceStatusConnectChanged(statusCode);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } finally {
            if (count > 0) {
                mOnPlayChangedListener.finishBroadcast();
            }
        }
    }


}
