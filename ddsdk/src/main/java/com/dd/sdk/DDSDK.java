package com.dd.sdk;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.dd.sdk.common.DeviceInformation;
import com.dd.sdk.common.NetworkState;
import com.dd.sdk.common.TokenPrefer;
import com.dd.sdk.config.NetConfig;
import com.dd.sdk.listener.ConfigurationListener;
import com.dd.sdk.listener.FileType;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.manage.ServerCMD;
import com.dd.sdk.net.NetworkHelp;
import com.dd.sdk.net.okhttp.OkHttpHelp;
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
import com.dd.sdk.thread.ThreadManager;
import com.dd.sdk.tools.AppUtils;
import com.dd.sdk.tools.GsonUtils;
import com.dd.sdk.tools.LogUtils;
import com.dd.sdk.tools.SPUtils;
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
    private final static int TOKEN_GET_CONFIG = 2;
    private final static String CURID_VALUE = "curid";//设置默认的保存黑白名单的下发值
    private static NetworkState mNetworkState;
    private static InstructionListener mInstructionListener;
    private static Context mContext;
    private static AccessToken accessToken;
    private static ICommandManager mICommandManager;
    public static String accessKey, secretKey, endpoint, bucket_name;
    //  private static CompositeDisposable compositeDisposable = new CompositeDisposable();
    /**
     * 端口号和域名
     */
    public static NetConfig netConfig;
    /**
     * 设备id
     */
    private static String mGuid;

    /**
     * 获取全局上下文
     */
    public Context getContext() {
        checkInitialize();
        return mContext;
    }

    private static DDSDK mDDSDK = new DDSDK();

    public static DDSDK getinstance() {
        return mDDSDK;
    }

    /**
     * 检测是否调用初始化方法
     */
    private void checkInitialize() {
        if (mContext == null) {
            throw new ExceptionInInitializerError("请先在全局MainActivity中调用 DDSDK.init() 初始化！");
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

    private IOnCommandListener mIOnCommandListener = new IOnCommandListener.Stub() {
        @Override
        public void onMessageResponse(String msg) throws RemoteException {

            ServerCMD.setStringConect(msg, mContext, mGuid, mInstructionListener, mConfigurationListener);

        }

        @Override
        public void onServiceStatusConnectChanged(int statusCode) throws RemoteException {
            // LogUtils.i(TAG, "onServiceStatusConnectChanged  statusCode=" + statusCode);
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


    /**
     * 先写到一块儿，等会儿要分开
     *
     * @param context  应用
     * @param app_id   APP秘钥
     * @param deviceID 设备id
     * @param listener 回调监听
     */
    public void init(Context context, String app_id, String secret_key, String deviceID, String domainName, int port, InstructionListener listener) {
        mContext = context;
        LogUtils.init(null, true, true);
        mInstructionListener = listener;
        mNetworkState = new NetworkState(context);
        DeviceInformation.getInstance().setGuid(deviceID);
        mGuid = DeviceInformation.getInstance().getGuid();
        netConfig = new NetConfig();
        netConfig.setdPort(port);
        netConfig.setDomain(domainName);
        accessToken(app_id, secret_key);
        bindService(mContext);

        bucket_name = "bucket_name";
    }

    /**
     * 初始化亚马逊密码和域名以及key
     *
     * @param mendpoint
     * @param maccessKey
     * @param msecretKey
     */
    public void amazonCloudinit(String mendpoint, String maccessKey, String msecretKey) {
        endpoint = mendpoint;
        secretKey = maccessKey;
        accessKey = msecretKey;
    }


    /**
     * 获取token
     *
     * @param app_id
     * @param secret_key
     */
    private void accessToken(final String app_id, final String secret_key) {
        NetworkHelp.getInstance().getAccessToken(app_id, secret_key, new Response.Listener<JSONObject>() {
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
                        RegisterDevice(mContext, AppUtils.getIpAddress(mContext), "13787138669");
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
                mInstructionListener.tokenFile();
                mContext = null;

            }
        });
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


    /**
     * 设备注册检查,如果没有注册则注册设备
     */
    private void RegisterDevice(final Context context, final String macAddress, final String mobile) {
        NetworkHelp.getRegisterDevice(context, macAddress, mobile, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.i(TAG, "RegisterDevice    response=" + response);
                RegisterResponse baseResponse = GsonUtils.getObject(response.toString(), RegisterResponse.class);
                switch (baseResponse.code) {
                    case RegisterResponse.ALREADY_BOUND://20010	设备已绑定
                        getConfig(context, DeviceInformation.getInstance().getGuid(), "5000");
                        return;
                    case RegisterResponse.REGISTER_FAIL://20011	设备注册失败
                        RegisterDevice(context, macAddress, mobile);//注册失败重新注册
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
     * 获取配置信息
     *
     * @param guid     设备唯一标识符
     * @param door_ver 5000 以下代表 door5 以下版本，5000-5999 代表 door5 版本，默认值：0
     */
    public void getConfig(Context context, String guid, String door_ver) {
        NetworkHelp.getConfig(context, guid, door_ver, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        LogUtils.i("getConfig========response=" + response);
                        BaseResponse<DoorConfig> baseResponse = new BaseResponse<>();
                        Type t = new TypeToken<BaseResponse<DoorConfig>>() {
                        }.getType();
                        BaseResponse<DoorConfig> response1 = GsonUtils.getObject(response.toString(), t, baseResponse);
                        LogUtils.i("getConfig========response1=" + response1);
                        if (response1 != null) {
                            switch (baseResponse.code) {
                                case RegisterResponse.RESPONSE_SUCCESS://返回成功
                                    DoorConfig deviceConfig = response1.getData();
                                    bucket_name = deviceConfig.getBucket_name();
                                    mInstructionListener.getconfig(deviceConfig); //将获取设备配置的内容发送到上层App
                                    return;
                                case RegisterResponse.NO_REGISTER://20011	设备未注册
                                    if (mInstructionListener != null) {
                                        mInstructionListener.noRegister();//设备未绑定，需要重新注册
                                    }
                                    break;
                                case RegisterResponse.NO_BOUND://20012设备w未绑定
                                    if (mInstructionListener != null) {
                                        mInstructionListener.noBinding();//设备未绑定，需要重新绑定
                                    }
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
    public ResultBean postDeviceConfig(String guid, UpdoorconfigBean config) {
        final ResultBean resultBean=new ResultBean();
        NetworkHelp.postConfig(mContext, guid, config, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                LogUtils.i("postDeviceConfig   response=" + response);
                resultBean.setErrCode(200);
                resultBean.setErrMsg("信息上报成功");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                LogUtils.i("postDeviceConfig   error=" + error.getMessage());
                resultBean.setErrCode(-1);
                resultBean.setErrMsg("信息上报失败");
            }
        });

        return resultBean;
    }


    /**
     * 释放相关资源
     */
    public void release(Context context) {
        if (null != mNetworkState) {
            mNetworkState.release();
            mNetworkState = null;
        }
        unbindService();
        // compositeDisposable.clear();
    }

    /**
     * 拉取黑白名单,数据会分页拉取，约定每次最大返回500条，如果每次返回值是500则再拉取一次
     *
     * @param
     * @param guid  设备唯一标识符
     * @param curid 当前操作步数
     */
    public List<CardInfo<Floor>> getCardInfo(final Context context, final String guid, final int curid) {
        NetworkHelp.getCardInfo(context, guid, curid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                BaseResponse<CardInfo<Floor>> cardinfo = new BaseResponse<>();
                Type t = new TypeToken<BaseResponse<CardInfo<Floor>>>() {
                }.getType();
                BaseResponse<CardInfo<Floor>> response1 = GsonUtils.getObject(response.toString(), t, cardinfo);
                if (response1 != null) {
                    switch (response1.code) {
                        case RegisterResponse.RESPONSE_SUCCESS://返回成功
                            CardInfo deviceConfig = response1.getData();
                            checkDeviceConfig(context, guid, curid, deviceConfig);
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return null;
    }

    /**
     * 检测当前数据是否已经发完结
     *
     * @param context
     * @param guid
     * @param curid
     * @param deviceConfig
     */
    private void checkDeviceConfig(final Context context, final String guid, final int curid, final CardInfo deviceConfig) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<CardInfo<Floor>> cardInfos = deviceConfig.getInfo();
                ResultBean resultBean = mInstructionListener.getBlackAndWhiteList(cardInfos);//上报到上层应用处理数据
                if (resultBean.isSuccess()) {//上层APP数据处理成功
                    if (deviceConfig != null && deviceConfig.count() == 500) {//约定每次最大返回500条
                        getCardInfo(context, guid, curid);
                        int oldguid = SPUtils.get(CURID_VALUE, curid);
                        SPUtils.put(CURID_VALUE, curid + oldguid);
                    } else {
                        SPUtils.put(CURID_VALUE, 0);
                    }
                }

            }
        };
        ThreadManager.getThreadPollProxy().execute(runnable);
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
    static boolean mRequestState;

    public boolean uploadVideoOrPicture(FileType fileType, String fileName, String fileAddress, String guid, String device_type, int operate_type, String objectkey, long time,
                                        String content, String room_id, String reason, String open_time) {
        mRequestState = false;
        try {
            OkHttpHelp.putBucketObject(bucket_name, fileName, fileAddress);//提交文件到亚马逊云

            NetworkHelp.uploadVideoOrPicture(DDSDK.getinstance().getContext(), fileType, fileName, fileAddress, guid, device_type, operate_type, objectkey, time,
                    content, room_id, reason, open_time, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            BaseResponse cardinfo = new BaseResponse();
                            Type t = new TypeToken<BaseResponse>() {
                            }.getType();
                            BaseResponse response1 = GsonUtils.getObject(response.toString(), t, cardinfo);
                            if (response1 != null && response1.isSuccess()) {
                                mRequestState = true;
                            } else {
                                mRequestState = false;
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            mRequestState = false;
                        }
                    });

        } catch (Exception e) {
            LogUtils.i("putBucketObject e=" + e);
        }
        return mRequestState;
    }


    /**
     * 密码开门
     *
     * @param password 开门密码
     * @param //       开门状态回调
     */
    public ResultBean pWOpenDoor(int password/*, OpenDoorListener listener*/) {
        return new ResultBean();
    }

    /**
     * 处理服务器下发下来的命令
     */
    private ConfigurationListener mConfigurationListener = new ConfigurationListener() {
        @Override
        public void getConfigCmd(Context context, String guid, String door_ver) {
            getConfig(context, guid, door_ver);
        }

        @Override
        public void getCardInfoCmd(Context context, String gruid, int curid) {
            getCardInfo(context, gruid, curid);
        }
    };

}
