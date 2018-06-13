package com.dd.sdk;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dd.sdk.common.DeviceInformation;
import com.dd.sdk.common.NetworkState;
import com.dd.sdk.common.TokenPrefer;
import com.dd.sdk.config.NetConfig;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.net.NetworkHelp;
import com.dd.sdk.netbean.AccessToken;
import com.dd.sdk.netbean.BaseResponse;
import com.dd.sdk.netbean.CardInfo;
import com.dd.sdk.netbean.DoorConfig;
import com.dd.sdk.netbean.Floor;
import com.dd.sdk.netbean.RegisterResponse;
import com.dd.sdk.netbean.ResultBean;
import com.dd.sdk.netbean.UpdoorconfigBean;
import com.dd.sdk.service.ICommandManager;
import com.dd.sdk.service.IOnCommandListener;
import com.dd.sdk.service.MainService;
import com.dd.sdk.tools.AppUtils;
import com.dd.sdk.tools.GsonUtils;
import com.dd.sdk.tools.LogUtils;
import com.google.mgson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.doordu.doorsdk
 * @class describe
 * @time 2018/6/4 16:28
 * @change
 * @class describe
 */

public class DDSDK {
    private final static String TAG = DDSDK.class.getSimpleName();
    private final static int TOKEN_REGISTER = 1;
    //private final static int TOKEN_GET_CONFIG = 2;
    private static NetworkState mNetworkState;
    private static RequestQueue mNetWorkRequest;
    private static InstructionListener mInstructionListener;
    private static Context mContext;
    private static AccessToken accessToken;
    private static ICommandManager mICommandManager;
    /**
     * 端口号和域名
     */
    public static NetConfig netConfig;


    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        checkInitialize();
        return mContext;
    }

    /**
     * 检测是否调用初始化方法
     */
    private static void checkInitialize() {
        if (mContext == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 DDSDK.init() 初始化！");
        }
    }


    public static RequestQueue addRequestQueue(Request request) {
        if (mNetWorkRequest == null) {

            mNetWorkRequest = Volley.newRequestQueue(mContext);
        }
        mNetWorkRequest.add(request);
        return mNetWorkRequest;
    }

    private static ServiceConnection serviceConnection = new ServiceConnection() {

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

        @Override
        public void onBindingDied(ComponentName name) {

        }
    };

    private static IOnCommandListener mIOnCommandListener = new IOnCommandListener.Stub() {
        @Override
        public void onMessageResponse(String msg) throws RemoteException {
            LogUtils.i(TAG, "onMessageResponse MSG=" + msg);
        }

        @Override
        public void onServiceStatusConnectChanged(int statusCode) throws RemoteException {
            LogUtils.i(TAG, "onServiceStatusConnectChanged  statusCode=" + statusCode);
        }
    };

    /**
     * Binder死亡代理
     */
    private static IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() {
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


    /**
     * 先写到一块儿，等会儿要分开
     *
     * @param application 应用
     * @param app_id      APP秘钥
     * @param deviceID    设备id
     * @param listener    回调监听
     */
    public static void init(final Context application, final String app_id, final String secret_key, final String deviceID, String domainName, int port, final InstructionListener listener) {
       mContext = application;
        LogUtils.init(null, true, true);
        mInstructionListener = listener;
        mNetWorkRequest = Volley.newRequestQueue(application);
        mNetworkState = new NetworkState(application);

        TokenPrefer.loadConfig(application);
        DeviceInformation.getInstance().setGuid(deviceID);
        netConfig = new NetConfig();
        netConfig.setdPort(port);
        netConfig.setDomain(domainName);
        LogUtils.i(TAG, "init   app_id=" + app_id + "  secret_key=" + secret_key + " deviceID=" + deviceID + "  domainName=" + domainName);

        NetworkHelp.getAccessToken(app_id, secret_key, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {

                LogUtils.i(TAG, "init onResponse  response==" + object);
                BaseResponse<AccessToken> response = new BaseResponse<AccessToken>();
                try {
                    Type t = new TypeToken<BaseResponse<AccessToken>>() {
                    }.getType();
                    response = GsonUtils.getObject(object.toString(), t, response);
                    LogUtils.i(TAG, "init onResponse  response==" + response);
                    if (response.isSuccess()) {
                        accessToken = response.data;// GsonUtils.getObject(response.data.toString(), AccessToken.class);
                        TokenPrefer.saveConfig(mContext, accessToken);
                        RegisterDevice(application, AppUtils.getIpAddress(application), "13787138669");
                    } else {
                        if (mInstructionListener != null) {
                            mInstructionListener.noBinding();
                        }
                    }
                    LogUtils.i(TAG, "baseResponse==" + response);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.i(TAG, "init onResponse  response=e=" + e);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.i(TAG, "init  onErrorResponse =error=" + error);
                //mInstructionListener.tokenFile();
                mContext = null;
                if (mNetWorkRequest != null) {
                    mNetWorkRequest.stop();
                    mNetWorkRequest = null;
                }
            }
        });

         bindService(mContext);
    }

    /**
     * 绑定服务
     */
    public static void bindService(Context mContext) {
        Intent intent = new Intent(mContext, MainService.class);
       String processAppName = AppUtils.getAppName(mContext);
        String pagename = AppUtils.getPackageName(mContext);

        Bundle bundle = new Bundle();
        bundle.putSerializable(MainService.CONFIG_BEAN, netConfig);
        bundle.putSerializable(MainService.PACKAGE_NAME, pagename);
        intent.putExtras(bundle);
        mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        if (mContext != null) {
            LogUtils.i("bindService  mContext=" + mContext.getClass().getName()  );
        } else {
            LogUtils.i("bindService ");
        }

    }

    /**
     * 解绑服务
     */
    public static void unbindService(Context context) {
        LogUtils.i("unbindService===" + (mContext != null) + "  =" + (mICommandManager != null));
        try {
            if (mICommandManager != null) {
                mICommandManager.unRegisterListener(mIOnCommandListener);
            }
            if (mContext != null) {
                mContext.unbindService(serviceConnection);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }


    /**
     * 设备注册检查,如果没有注册则注册设备
     */
    private static void RegisterDevice(final Context context, String macAddress, String mobile) {
        NetworkHelp.getRegisterDevice(context, macAddress, mobile, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.i(TAG, "RegisterDevice    response=" + response);
                Type t = new TypeToken<BaseResponse>() {
                }.getType();
                RegisterResponse baseResponse = GsonUtils.getObject(response.toString(), RegisterResponse.class);

                switch (baseResponse.code) {
                    case RegisterResponse.ALREADY_BOUND://20010	设备已绑定
                        getConfig(context, DeviceInformation.getInstance().getGuid(), "5000");
                        return;
                    case RegisterResponse.REGISTER_FAIL://20011	设备注册失败
                        break;
                    case RegisterResponse.ALREADY_REGISTERED://20012设备已注册
                        break;
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.i(TAG, "RegisterDevice    response=" + error);
            }
        });
    }


    /**
     * @param guid     设备唯一标识符
     * @param door_ver 5000 以下代表 door5 以下版本，5000-5999 代表 door5 版本，默认值：0
     */
    public static void getConfig(Context context, String guid, String door_ver) {
        NetworkHelp.getConfig(context, DeviceInformation.getInstance().getGuid(), "5000", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtils.i(TAG, "getConfig  response==" + response);
                        BaseResponse<DoorConfig> baseResponse = new BaseResponse<>();
                        Type t = new TypeToken<BaseResponse<DoorConfig>>() {
                        }.getType();
                        BaseResponse<DoorConfig> response1 = GsonUtils.getObject(response.toString(), t, baseResponse);
                        LogUtils.i(TAG, "getConfig  response1==" + response1);
                        if (response1 != null) {
                            switch (baseResponse.code) {
                                case RegisterResponse.RESPONSE_SUCCESS://返回成功
                                    DoorConfig deviceConfig = response1.getData();
                                    mInstructionListener.getconfig(deviceConfig);
                                    LogUtils.i(TAG, "getConfig  deviceConfig==" + deviceConfig);
                                    return;
                                case RegisterResponse.NO_REGISTER://20011	设备未注册
                                    break;
                                case RegisterResponse.NO_BOUND://20012设备w未绑定
                                    break;
                            }
                        } else {
                            //需要做后续处理
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        LogUtils.i("EEE", "getConfig error==" + error);
                    }
                });

    }


    /**
     * 上报配置信息
     *
     * @param guid   设备唯一标识符
     * @param config json对象	配置内容
     */
    public static void postDeviceConfig(String guid, UpdoorconfigBean config) {
        NetworkHelp.postConfig(mContext, guid, config, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }


    /**
     * 释放相关资源
     */
    public static void release(Application application) {
        if (null != mNetworkState) {
            //mNetworkState.release();
            mNetworkState = null;
        }
        unbindService(mContext);
    }

    /**
     * 拉取黑白名单,数据会分页拉取，约定每次最大返回500条，如果每次返回值是500则再拉取一次
     *
     * @param
     * @param guid     设备唯一标识符
     * @param curid    当前操作步数
     * @param listener
     */
    public static List<CardInfo<Floor>> getCardInfo(String guid, int curid, Listener<BaseResponse<CardInfo<Floor>>> listener) {
        return null;
    }


    /**
     * 上传访客留影
     *
     * @param fileType     必选 文件类型 Constant.VIDEO_TYPE Constant.PICTURE_TYPE，
     * @param fileName     必选 文件名称
     * @param fileAddress  必选  文件地址
     * @param guid         必选 设备唯一标识符
     * @param device_type  必选  设备类型
     * @param operate_type 必选 开门类型
     * @param objectkey    必选 留影图片地址
     * @param time         必选 门禁机时间
     * @param content      可选 透传字段，具体依据 operate_type 而定，值为urlencode后的字符串
     * @param room_id      可选 房间 ID
     * @param reason       可选 摄像头故障状态码
     * @param open_time    可选 13 位 Unix 时间戳，精确到毫秒级，一次开门的视频留影和图片留影应用同一个时间
     * @return 上传成功返回true，失败返回false 请重传一次
     */
    public static boolean uploadVideoOrPicture(String fileType, String fileName, String fileAddress, String guid, String device_type, int operate_type, String objectkey, long time,
                                               String content, String room_id, String reason, long open_time) {


        return false;
    }

    /**
     * 密码开门
     *
     * @param password 开门密码
     * @param //       开门状态回调
     */
    public static ResultBean pWOpenDoor(int password/*, OpenDoorListener listener*/) {
        return new ResultBean();
    }

}
