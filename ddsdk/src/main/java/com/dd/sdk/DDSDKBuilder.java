package com.dd.sdk;

import android.content.Context;

import com.dd.sdk.common.NetworkState;
import com.dd.sdk.config.NetConfig;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.netbean.AccessToken;
import com.dd.sdk.service.ICommandManager;
import com.dd.sdk.tools.LogUtils;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk
 * @class describe
 * @time 2018/6/21 15:12
 * @change
 * @class describe
 */

public class DDSDKBuilder {
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
    private String sdksecretKey, sdkaccessKey;

    /**
     * 端口号和域名
     */
    public static NetConfig netConfig;
    /**
     * 设备id
     */
    private static String mGuid;



    /**
     * 传递上下文
     */
    public DDSDKBuilder initContext(Context context) {
        mContext = context;
        LogUtils.init(null, true, true);
        mNetworkState = new NetworkState(context);
        bucket_name = "bucket_name";
        return this;
    }

    /**
     * 设置SDKkey
     *
     * @param accessKey
     * @return
     */
    public DDSDKBuilder sdkAccessKey(String accessKey) {
        this.sdksecretKey = accessKey;
        return this;
    }

    /**
     * 设置SDK秘钥
     *
     * @param secretKey
     * @return
     */
    public DDSDKBuilder sdkSecretKey(String secretKey) {
        this.sdkaccessKey = secretKey;
        return this;
    }

    /**
     * 设置设备id
     *
     * @param deviceID
     * @return
     */
    public DDSDKBuilder setdeviceID(String deviceID) {
       /* DeviceInformation.getInstance().setGuid(deviceID);
        DeviceInformation.getInstance().getGuid();*/
        mGuid =deviceID;
        return this;
    }

    /**
     * 设置域名和端口号
     *
     * @param config
     * @return
     */
    public DDSDKBuilder setNetConfig(NetConfig config) {
        netConfig = config;
     /*   netConfig.setdPort(port);
        netConfig.setDomain(domainName);*/
        return this;
    }

    /**
     * 回调监听
     *
     * @param listener
     * @return
     */
    public DDSDKBuilder setInstructionListener(InstructionListener listener) {
        mInstructionListener = listener;
        return this;
    }

    /**
     * 使用配置属性构建客户端
     *
     * @return
     */
    public DDSDK build() {
        DDSDK ddsdk = new DDSDK();
        /*if (sdkaccessKey != null && sdksecretKey != null) {
            ddsdk.accessToken();
        }*/
      //  ddsdk.init(mContext, "f2a9d153188d87e18adc233ca8ee30da", "564f939a8f8a5befa67d62bdf79e6fa5", "test20160822001", "10.0.2.152", 8888, this);

        return ddsdk;
    }
}
