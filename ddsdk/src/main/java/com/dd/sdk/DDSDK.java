package com.dd.sdk;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
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
import com.dd.sdk.netbean.ResultBean;
import com.dd.sdk.netbean.UpdoorconfigBean;

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
    private final static int TOKEN_REGISTER = 1;
    //private final static int TOKEN_GET_CONFIG = 2;
    private static NetworkState mNetworkState;
    private static RequestQueue mNetWorkRequest;
    private static InstructionListener mInstructionListener;
    private static Context mContext;
    private static AccessToken accessToken;

    public static RequestQueue addRequestQueue(Request request) {
      /*  if (mNetWorkRequest == null) {
            VolleyBuilder b = new VolleyBuilder(mContext);
            b.setThreadPoolSize(3);
            mNetWorkRequest = Volley.newRequestQueue(b);
        }*/
        mNetWorkRequest.add(request);
        return mNetWorkRequest;
    }

    /**
     * 先写到一块儿，等会儿要分开
     *
     * @param application 应用
     * @param app_id      APP秘钥
     * @param deviceID    设备id
     * @param listener    回调监听
     */
    public static void init(final Application application, String app_id, String secret_key, final String deviceID, String domainName, int port, final InstructionListener listener) {
        mContext = application;
        mInstructionListener = listener;
        mNetWorkRequest = Volley.newRequestQueue(application);
        mNetworkState = new NetworkState(application);
        TokenPrefer.loadConfig(application, accessToken);
        DeviceInformation.getInstance().setGuid(deviceID);
        NetConfig netConfig = new NetConfig();
        netConfig.setdPort(port);
        netConfig.setDomain(domainName);
        NetworkHelp.getAccessToken(app_id, secret_key, new Listener<BaseResponse<AccessToken>>() {
            @Override
            public void onResponse(BaseResponse<AccessToken> response) {
                accessToken = response.data;
                TokenPrefer.saveConfig(mContext, accessToken);
                RegisterDevice(application);

                Log.i("EEE", "init  response==" + response);
                if (response.isSuccess()) {
                    getConfig(deviceID, null);
                } else {
                    if (mInstructionListener != null) {
                        mInstructionListener.noBinding();
                    }
                }

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error ) {
                mInstructionListener.tokenFile();
                mNetWorkRequest = null;
                mContext = null;
                mNetWorkRequest.stop();
            }
        });


    }

    /**
     * 设备注册检查
     */
    private static void RegisterDevice(Application application) {
        NetworkHelp.getRegisterDevice(mNetWorkRequest, "15018500000", "", "", application, new Listener<BaseResponse<DoorConfig>>() {
            @Override
            public void onResponse(BaseResponse<DoorConfig> response ) {

            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error ) {
                Log.i("EEE", "init error==" + error);
            }
        }, accessToken.getToken());
    }


    /**
     * @param guid     设备唯一标识符
     * @param door_ver 5000 以下代表 door5 以下版本，5000-5999 代表 door5 版本，默认值：0
     */
    public static void getConfig(String guid, String door_ver) {
        NetworkHelp.getConfig(DeviceInformation.getInstance().getGuid(), "0", new Listener<BaseResponse<DoorConfig>>() {
                    @Override
                    public void onResponse(BaseResponse<DoorConfig> response ) {
                        Log.i("EEE", "getConfig  response==" + response);
                        if (response != null && response.isSuccess()) {
                            DoorConfig doorConfig2s = response.getData();
                            new Thread(mNetworkState).start();
                        }
                    }
                },
                new ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error ) {
                        Log.i("EEE", "getConfig error==" + error);
                    }
                }, accessToken.getToken());

    }


    /**
     * 上报配置信息
     *
     * @param guid   设备唯一标识符
     * @param config json对象	配置内容
     */
    public static void postDeviceConfig(String guid, UpdoorconfigBean config) {

    }

    /**
     * 获取网络操作指令
     */
    public static DDSDK getServerInstruction(InstructionListener instructionListener) {
        DDSDK DDSDK = new DDSDK();
        return DDSDK;
    }

    /**
     * 释放相关资源
     */
    public static void release(Application application) {
        if (null != mNetworkState) {
            mNetworkState.release();
            mNetworkState = null;
        }
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



/*    *//**
     * 请求开门
     * @param openDoorType 开门类型
     * @param listener  开门结果回调
     *//*
    public static void openDoor(OpenDoorType openDoorType , OpenDoorResultListener listener){

    }

    */

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
