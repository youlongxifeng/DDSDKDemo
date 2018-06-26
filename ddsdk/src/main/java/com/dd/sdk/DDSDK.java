package com.dd.sdk;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.dd.sdk.common.NetworkState;
import com.dd.sdk.common.TokenPrefer;
import com.dd.sdk.config.NetConfig;
import com.dd.sdk.listener.ConfigurationListener;
import com.dd.sdk.listener.DDListener;
import com.dd.sdk.listener.FileType;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.manage.ServerCMD;
import com.dd.sdk.net.RequestError;
import com.dd.sdk.net.volley.DDVolley;
import com.dd.sdk.netbean.AccessToken;
import com.dd.sdk.netbean.BaseResponse;
import com.dd.sdk.netbean.CardInfo;
import com.dd.sdk.netbean.DoorConfig;
import com.dd.sdk.netbean.Floor;
import com.dd.sdk.netbean.OpenDoorPwd;
import com.dd.sdk.netbean.RegisterResponse;
import com.dd.sdk.netbean.RequestOpenDoor;
import com.dd.sdk.netbean.ResultBean;
import com.dd.sdk.netbean.UpdoorconfigBean;
import com.dd.sdk.service.BindServiceOperation;
import com.dd.sdk.service.IOnCommandListener;
import com.dd.sdk.thread.ThreadManager;
import com.dd.sdk.tools.LogUtils;
import com.dd.sdk.tools.SPUtils;

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
    /**
     * 上下文
     */
    private static Context mContext;
    /**
     * token信息
     */
    private static AccessToken accessToken;
    public static String accessKey, secretKey, endpoint, bucket_name;
    /**
     * 秘钥和key
     */
    private String sdkSecretKey, sdkAccessKey;
    /**
     * 电话号码
     */
    private String mMobile;
    /**
     * 端口号和域名
     */
    public static NetConfig netConfig;
    /**
     * 设备id
     */
    private static String mGuid;
    private Handler mHandler = null;


    private BindServiceOperation mBindServiceOperation;

    /**
     * 获取全局上下文
     */
    public Context getContext() {
        checkInitialize();
        LogUtils.i("getContext======" + (mContext != null));
        return mContext;
    }

    private static class LazyHolder {
        private static final DDSDK INSTANCE = new DDSDK();
    }

    public DDSDK() {
        Looper looper = Looper.getMainLooper(); //主线程的Looper对象
        mHandler = new Handler(looper);
    }

    public static final DDSDK getInstance() {
        return LazyHolder.INSTANCE;
    }


    /**
     * 检测是否调用初始化方法
     */
    private void checkInitialize() {
        if (mContext == null) {
            throw new ExceptionInInitializerError("请先在全局MainActivity中调用 DDSDK.init() 初始化！");
        }
    }


    /**
     * 接收来自服务端下发的指令
     */
    private IOnCommandListener mIOnCommandListener = new IOnCommandListener.Stub() {
        @Override
        public void onMessageResponse(String msg) throws RemoteException {//
            ServerCMD.setStringConect(msg, mContext, mGuid, mHandler, /*mInstructionListener,*/ mConfigurationListener);
        }

        @Override
        public void onServiceStatusConnectChanged(int statusCode) throws RemoteException {
            // LogUtils.i(TAG, "onServiceStatusConnectChanged  statusCode=" + statusCode);
        }
    };

    /**
     * 先写到一块儿，等会儿要分开
     *
     * @param context  应用
     * @param app_id   APP秘钥
     * @param guid     设备id
     * @param listener 回调监听
     */
    public void init(@NonNull Context context, @NonNull String app_id, @NonNull String secret_key, @NonNull String guid, @NonNull String domainName, @NonNull int port, String mobile, @NonNull InstructionListener listener) {
        this.mContext = context;
        LogUtils.init(null, true, true);
        this.mInstructionListener = listener;
        this.netConfig = new NetConfig();
        this.netConfig.setdPort(port);
        this.netConfig.setDomain(domainName);
        this.sdkSecretKey = secret_key;
        this.sdkAccessKey = app_id;
        this.mGuid = guid;
        this.mNetworkState = new NetworkState(context, sdkAccessKey, sdkSecretKey);
        this.mNetworkState.tokenCheck();
        this.mMobile = mobile;
        accessToken();
        this.bucket_name = "bucket_name";
        LogUtils.i("=======init=====");
    }

    /**
     * 初始化亚马逊密码和域名以及key
     *
     * @param mendpoint
     * @param maccessKey
     * @param msecretKey
     */
    public void amazonCloudinit(@NonNull String mendpoint, @NonNull String maccessKey, @NonNull String msecretKey) {
        this.endpoint = mendpoint;
        this.secretKey = msecretKey;
        this.accessKey = maccessKey ;
    }


    /**
     * 获取token
     */
    public AccessToken accessToken() {
        DDVolley.accessToken(sdkAccessKey, sdkSecretKey, new DDListener<BaseResponse<AccessToken>, RequestError>() {
            @Override
            public void onResponse(BaseResponse<AccessToken> response) {
                try {
                    LogUtils.i(TAG, "init onResponse  response==" + response);
                    if (response.isSuccess()) {
                        mBindServiceOperation = new BindServiceOperation(mContext, netConfig, mGuid, mIOnCommandListener);
                        mBindServiceOperation.bindService();
                        accessToken = response.data;
                        TokenPrefer.saveConfig(mContext, accessToken);
                        RegisterDevice(mContext, TextUtils.isEmpty(mMobile) ? "13787138669" : mMobile);
                    } else {
                        tokenFile();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.i(TAG, "init onResponse  response=e=" + e);
                }
            }

            @Override
            public void onErrorResponse(RequestError error) {
                tokenFile();
            }
        });

        return accessToken;
    }


    /**
     * 设备注册检查,如果没有注册则注册设备
     *
     * @param context
     * @param mobile  电话号码
     */
    private void RegisterDevice(final Context context, final String mobile) {
        DDVolley.RegisterDevice(context, mGuid, mobile, new DDListener<BaseResponse, RequestError>() {
            @Override
            public void onResponse(final BaseResponse baseResponse) {
                LogUtils.i(TAG, "RegisterDevice    response=" + baseResponse);
                switch (baseResponse.code) {
                    case RegisterResponse.RESPONSE_SUCCESS:
                        getConfig(context, mGuid, "5000");
                        break;
                    case RegisterResponse.ALREADY_BOUND://20010	设备已绑定
                        getConfig(context, mGuid, "5000");
                        break;
                    case RegisterResponse.REGISTER_FAIL://20011	设备注册失败
                        RegisterDevice(context, mobile);//注册失败重新注册
                        break;
                    case RegisterResponse.ALREADY_REGISTERED://20012设备已注册,但是设备未绑定
                        noBinding();
                        break;
                    case RegisterResponse.TOKEN_EXPIRED://token过期
                        AccessToken accessToken = accessToken();
                        if (accessToken != null) {
                            RegisterDevice(context, mobile);
                        }
                        break;
                    default:
                        break;
                }
                onResponseListener(baseResponse);
            }

            @Override
            public void onErrorResponse(final RequestError volleyError) {
                LogUtils.i(TAG, "RegisterDevice    response=" + volleyError);
                onErrorResponseListener(volleyError);
            }
        });

    }


    /**
     * 获取配置信息
     *
     * @param guid     设备唯一标识符
     * @param door_ver 5000 以下代表 door5 以下版本，5000-5999 代表 door5 版本，默认值：0
     */
    public void getConfig(final Context context, final String guid, final String door_ver) {
        DDVolley.getConfig(context, guid, door_ver, new DDListener<BaseResponse<DoorConfig>, RequestError>() {
            @Override
            public void onResponse(final BaseResponse<DoorConfig> response) {
                LogUtils.i("getConfig   response=" + response + "  =" + (response != null));
                if (response != null) {
                    switch (response.code) {
                        case RegisterResponse.RESPONSE_SUCCESS://返回成功
                            DoorConfig deviceConfig = response.getData();
                            bucket_name = deviceConfig.getBucket_name();
                            SPUtils.put("bucket_name", bucket_name);
                            getconfig(deviceConfig); //将获取设备配置的内容发送到上层App
                            break;
                        case RegisterResponse.NO_REGISTER://20011	设备未注册
                            noRegister();//设备未绑定，需要重新注册
                            break;
                        case RegisterResponse.NO_BOUND://20012设备w未绑定
                            noBinding();//设备未绑定，需要重新绑定
                            break;
                        case RegisterResponse.TOKEN_EXPIRED://token过期
                            AccessToken accessToken = accessToken();
                            if (accessToken != null) {
                                getConfig(context, guid, door_ver);
                            }
                            break;
                        default:
                            return;
                    }
                }
                onResponseListener(response);//设备未绑定，需要重新绑定
            }

            @Override
            public void onErrorResponse(final RequestError volleyError) {
                LogUtils.i("EEE", "getConfig error==" + volleyError);
                onErrorResponseListener(volleyError);//设备未绑定，需要重新绑定
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
        final ResultBean resultBean = new ResultBean();
        DDVolley.postConfig(mContext, guid, config, new DDListener<BaseResponse, RequestError>() {
            @Override
            public void onResponse(final BaseResponse response) {
                LogUtils.i("postDeviceConfig   response=" + response);
                resultBean.setErrCode(200);
                resultBean.setErrMsg("信息上报成功");
                onResponseListener(response);

            }

            @Override
            public void onErrorResponse(final RequestError volleyError) {
                resultBean.setErrCode(-1);
                resultBean.setErrMsg("信息上报失败");
                onErrorResponseListener(volleyError);
            }
        });
        return resultBean;
    }


    /**
     * 拉取黑白名单,数据会分页拉取，约定每次最大返回500条，如果每次返回值是500则再拉取一次
     *
     * @param
     * @param guid  设备唯一标识符
     * @param curid 当前操作步数
     */
    public List<CardInfo<Floor>> getCardInfo(final Context context, final String guid, final int curid) {
        DDVolley.getCardInfo(context, guid, curid, new DDListener<BaseResponse<CardInfo<Floor>>, RequestError>() {
            @Override
            public void onResponse(BaseResponse<CardInfo<Floor>> response) {
                switch (response.code) {
                    case RegisterResponse.RESPONSE_SUCCESS://返回成功
                        CardInfo deviceConfig = response.getData();
                        checkFinishData(context, guid, curid, deviceConfig);
                        break;
                    case RegisterResponse.NO_REGISTER://20011	设备未注册
                        noRegister();
                        break;
                    case RegisterResponse.NO_BOUND://20012设备w未绑定
                        noBinding();
                        break;
                    case RegisterResponse.TOKEN_EXPIRED://token过期
                        AccessToken accessToken = accessToken();
                        if (accessToken != null) {
                            getCardInfo(context, guid, curid);
                        }
                        break;
                }
                onResponseListener(response);
            }

            @Override
            public void onErrorResponse(RequestError volleyError) {
                onErrorResponseListener(volleyError);
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
    private void checkFinishData(final Context context, final String guid, final int curid, final CardInfo deviceConfig) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                List<CardInfo<Floor>> cardInfos = deviceConfig.getInfo();
                deviceConfig.isAll();//是否全量获取
                ResultBean resultBean = mInstructionListener.getBlackAndWhiteList(cardInfos);//上报到上层应用处理数据
                if (resultBean.isSuccess()) {//上层APP数据处理成功
                    if (deviceConfig != null && deviceConfig.count() == 500) {//约定每次最大返回500条
                        int oldguid = SPUtils.get(CURID_VALUE, curid);
                        getCardInfo(context, guid, oldguid);
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

    public boolean uploadVideoOrPicture(final FileType fileType, final String fileName, final String fileAddress, final String guid, final String device_type, final int operate_type, final String objectkey, final long time,
                                        final String content, final String room_id, final String reason, final String open_time) {
        mRequestState = false;
       /* bucket_name ="bucket_name";// SPUtils.get("bucket_name", bucket_name);
        OkHttpHelp.putBucketObject(accessKey, secretKey,bucket_name, fileName, fileAddress);//提交文件到亚马逊云*/
        DDVolley.uploadVideoOrPicture(DDSDK.getInstance().getContext(), fileType, fileName, fileAddress, guid, device_type, operate_type, objectkey, time,
                content, room_id, reason, open_time, new DDListener<BaseResponse, RequestError>() {
                    @Override
                    public void onResponse(final BaseResponse response) {
                        LogUtils.i("uploadVideoOrPicture  response=" + response);
                        if (response != null) {
                            switch (response.code) {
                                case RegisterResponse.RESPONSE_SUCCESS://返回成功
                                    mRequestState = true;
                                    break;
                                case RegisterResponse.NO_REGISTER://20011	设备未注册
                                    noRegister();//设备未绑定，需要重新注册
                                    mRequestState = false;
                                    break;
                                case RegisterResponse.NO_BOUND://20012设备w未绑定
                                    noBinding();//设备未绑定，需要重新绑定
                                    mRequestState = false;
                                    break;
                                case RegisterResponse.TOKEN_EXPIRED://token过期
                                    AccessToken accessToken = accessToken();
                                    mRequestState = false;
                                    uploadVideoOrPicture(fileType, fileName, fileAddress, guid, device_type, operate_type, objectkey, time,
                                            content, room_id, reason, open_time);
                                    break;
                            }
                        }
                        onResponseListener(response);
                    }

                    @Override
                    public void onErrorResponse(final RequestError volleyError) {
                        mRequestState = false;
                        onErrorResponseListener(volleyError);
                    }
                });

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
     * 没注册子线程转UI线程
     */
    private void noRegister() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.noRegister();
                }
            }
        });
    }


    /**
     * 没绑定子线程转UI线程
     */
    private void noBinding() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.noBinding();
                }
            }
        });
    }


    /**
     * 获取配置信息子线程转UI线程
     *
     * @param doorConfig
     */
    private void getconfig(final DoorConfig doorConfig) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.getconfig(doorConfig);
                }
            }
        });
    }


    /**
     * 重启信息子线程转UI线程
     */
    private void reBoot() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.reBoot();
                }
            }
        });
    }

    /**
     * 开门子线程转UI线程
     */
    private void openDoor(final RequestOpenDoor openDoor) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.openDoor(openDoor);
                }
            }
        });
    }


    /**
     * 拉取黑白名单指令子线程转UI线程
     */
    private void getBlackAndWhiteList(final List<CardInfo<Floor>> cardInfos) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.getBlackAndWhiteList(cardInfos);
                }
            }
        });
    }

    /**
     * 网络密码指令子线程转UI线程
     */
    private void getNetworkCipher(final OpenDoorPwd pwd) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.getNetworkCipher(pwd);
                }
            }
        });
    }

    /**
     * token失败重新初始化子线程转UI线程
     */
    private void tokenFile() {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.tokenFile();
                }
            }
        });
    }

    /**
     * 请求成功子线程转UI线程
     */
    private void onResponseListener(final BaseResponse response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.onResponseListener(response);
                }
            }
        });

    }

    /**
     * 请求失败响应子线程转UI线程
     *
     * @param volleyError
     */
    private void onErrorResponseListener(final RequestError volleyError) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (mInstructionListener != null) {
                    mInstructionListener.onErrorResponseListener(volleyError);
                }
            }
        });

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

        @Override
        public void getNetworkCipher(OpenDoorPwd pwd) {
            if (mInstructionListener != null) {
                mInstructionListener.getNetworkCipher(pwd);
            }
        }

        @Override
        public void openDoor(RequestOpenDoor openDoor) {
            if (mInstructionListener != null) {
                mInstructionListener.openDoor(openDoor);
            }
        }

        @Override
        public ResultBean reBoot() {
            if (mInstructionListener != null) {
                return mInstructionListener.reBoot();
            }
            return null;
        }

        @Override
        public int nameListCurid() {
            if (mInstructionListener != null) {
                return mInstructionListener.nameListCurid();
            }
            return 0;
        }
    };

    /**
     * 释放相关资源
     */
    public void release(Context context) {
        if (null != mNetworkState) {
            mNetworkState.release();
            mNetworkState = null;
        }
        if (mBindServiceOperation != null) {
            mBindServiceOperation.unbindService();
        }
        DDVolley.stop();
        if (mHandler != null) {

            mHandler.removeCallbacksAndMessages(null);
        }
        // compositeDisposable.clear();
    }

}
