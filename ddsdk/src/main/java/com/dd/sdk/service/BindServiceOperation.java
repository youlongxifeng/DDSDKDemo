package com.dd.sdk.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.dd.sdk.config.NetConfig;
import com.dd.sdk.tools.AppUtils;
import com.dd.sdk.tools.LogUtils;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.service
 * @class describe
 * @time 2018/6/21 16:21
 * @change
 * @class describe
 */

public class BindServiceOperation {
    /**
     * 端口号和域名
     */
    public NetConfig netConfig;
    /**
     * 设备id
     */
    private String mGuid;
    /**
     * 上下文
     */
    private Context mContext;
    private ICommandManager mICommandManager;
    IOnCommandListener mIOnCommandListener;

    public BindServiceOperation(Context context, NetConfig config, String guid, IOnCommandListener listener) {
        this.mContext = context;
        this.netConfig = config;
        this.mGuid = guid;
        this.mIOnCommandListener = listener;
    }

    /**
     * 绑定服务,新开一个进程，用于与服务器保持长连接，接收服务器下发的内容指令
     *
     * @param mContext
     */
    public void bindService(Context mContext) {
        Intent intent = new Intent(mContext, MainService.class);
        String pagename = AppUtils.getPackageName(mContext);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MainService.CONFIG_BEAN, netConfig);
        bundle.putString(MainService.PACKAGE_NAME, pagename);
        bundle.putString(MainService.GUID_NAME, mGuid);
        intent.putExtras(bundle);
        boolean isbind = mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (isbind) {
            LogUtils.i("bindService  mContext=" + mContext.getClass().getName());
        } else {
            LogUtils.i("bindService ");
            if (mContext != null) {
                bindService(mContext);
            }
        }

    }


    /**
     * 解绑服务
     */
    public void unbindService() {
        try {
            if (mICommandManager != null) {
                mICommandManager.unRegisterListener(mIOnCommandListener);
            }
            mContext.unbindService(serviceConnection);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                mICommandManager = ICommandManager.Stub.asInterface(service);
                //通过linkToDeath，可以给Binder设置一个死亡代理，当Binder死亡时，就会收到通知
                service.linkToDeath(deathRecipient, 0);
                mICommandManager.registerListener(mIOnCommandListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            LogUtils.i("onServiceConnected===" + (mContext != null) + "  name=" + name);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i("unbindService===" + (mContext != null) + "  name=" + name);

        }
    };

    /**
     * Binder死亡代理
     */
    private IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mICommandManager == null) {
                return;
            }
            mICommandManager.asBinder().unlinkToDeath(deathRecipient, 0);
            mICommandManager = null;
            // TODO 重新绑定
        }
    };

}
