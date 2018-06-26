package com.dd.sdk;

import android.content.Context;

import com.dd.sdk.config.NetConfig;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.netbean.AccessToken;
import com.dd.sdk.service.ICommandManager;

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
    private final static String TAG = DDSDKBuilder.class.getSimpleName();
    private final static int TOKEN_REGISTER = 1;
    private final static int TOKEN_GET_CONFIG = 2;
    private final static String CURID_VALUE = "curid";//设置默认的保存黑白名单的下发值
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
        mGuid = deviceID;
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
        ddsdk.init(mContext, sdkaccessKey, sdksecretKey, mGuid, netConfig.getDomain(), netConfig.getdPort(),"13787138669", mInstructionListener);
        return ddsdk;
    }
}
