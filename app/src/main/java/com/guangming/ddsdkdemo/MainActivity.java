package com.guangming.ddsdkdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.dd.sdk.DDSDK;
import com.dd.sdk.config.NetConfig;
import com.dd.sdk.service.ICommandManager;
import com.dd.sdk.service.IOnCommandListener;
import com.dd.sdk.service.MainService;
import com.dd.sdk.tools.LogUtils;

public class MainActivity extends AppCompatActivity {
    private ICommandManager mICommandManager;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = this;
         LogUtils.init(null,true,true);
         findViewById(R.id.unbindserver).setOnClickListener(new OnClickListener() {
             @Override
             public void onClick(View v) {
                 DDApplication.unbindService();
             }
         });

    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i("unbindService==onStop=");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("unbindService==Destroy=");
        DDSDK.release(this.getApplication());
    }

    /**
     * 绑定服务
     */
    private void bindService() {

        Intent intent = new Intent(mContext, MainService.class);
        Bundle bundle = new Bundle();
        NetConfig netConfig = new NetConfig();
        netConfig.setdPort(9501);
        netConfig.setDomain("test.swoole.doordu.com");
        bundle.putSerializable(NetConfig.CONFIG_BEAN, netConfig);
        intent.putExtras(bundle);
        mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            try {
                mICommandManager = ICommandManager.Stub.asInterface(service);
                //通过linkToDeath，可以给Binder设置一个死亡代理，当Binder死亡时，就会收到通知
                service.linkToDeath(deathRecipient, 0);
                mICommandManager.registerListener(mIOnCommandListener);
                LogUtils.i("onServiceConnected============");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            LogUtils.i("onServiceDisconnected============");
            try {
                mICommandManager.unRegisterListener(mIOnCommandListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onBindingDied(ComponentName name) {

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

    private static IOnCommandListener mIOnCommandListener = new IOnCommandListener.Stub() {
        @Override
        public void onMessageResponse(String msg) throws RemoteException {
            LogUtils.i("onMessageResponse 处理来自服务器端下发给客户端的指令 MSG=" + msg);
        }

        @Override
        public void onServiceStatusConnectChanged(int statusCode) throws RemoteException {
            LogUtils.i("onServiceStatusConnectChanged  statusCode=" + statusCode);
        }
    };
}
