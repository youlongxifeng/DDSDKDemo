package com.dd.sdk.net.volley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.dd.sdk.DDSDK;
import com.dd.sdk.listener.DDListener;
import com.dd.sdk.listener.FileType;
import com.dd.sdk.net.NetworkHelp;
import com.dd.sdk.netbean.UpdoorconfigBean;
import com.dd.sdk.tools.LogUtils;

import org.json.JSONObject;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.volley
 * @class describe
 * @time 2018/6/13 20:58
 * @change
 * @class describe Volley网络请求工具类
 */

public class DDVolley {
    private final static String TAG = DDVolley.class.getSimpleName();
    private static RequestQueue mNetWorkRequest;

    public static RequestQueue addRequestQueue(Request request) {
        if (mNetWorkRequest == null) {
            LogUtils.i("==========" + (DDSDK.getinstance().getContext() != null));
            mNetWorkRequest = Volley.newRequestQueue(DDSDK.getinstance().getContext());
        }
        mNetWorkRequest.add(request);
        return mNetWorkRequest;
    }

    public static void stop() {
        if (mNetWorkRequest != null) {
            mNetWorkRequest.stop();
        }

    }

    /**
     * 获取token
     *
     * @param app_id
     * @param secret_key
     * @param ddListener
     */
    public static void accessToken(final String app_id, final String secret_key, final DDListener ddListener) {
        NetworkHelp.getInstance().getAccessToken(app_id, secret_key, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject object) {

                ddListener.onResponse(object);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ddListener.onErrorResponse(error);

            }
        });

    }

    /**
     * 设备注册检查,如果没有注册则注册设备
     *
     * @param context
     * @param macAddress
     * @param mobile
     */
    public static void RegisterDevice(final Context context, final String macAddress, final String mobile, final DDListener ddListener) {
        NetworkHelp.getRegisterDevice(context, macAddress, mobile, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ddListener.onResponse(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ddListener.onErrorResponse(error);
            }
        });
    }

    /**
     * 获取配置信息
     *
     * @param context
     * @param guid       设备唯一标识符
     * @param door_ver   5000 以下代表 door5 以下版本，5000-5999 代表 door5 版本，默认值：0
     * @param ddListener
     */
    public static void getConfig(Context context, String guid, String door_ver, final DDListener ddListener) {
        NetworkHelp.getConfig(context, guid, door_ver, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ddListener.onResponse(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ddListener.onErrorResponse(error);
                    }
                });
    }

    /**
     * 上报配置信息     *
     *
     * @param guid       设备唯一标识符
     * @param config     json对象	配置内容
     * @param context
     * @param guid
     * @param config
     * @param ddListener
     */
    public static void postConfig(Context context, String guid, UpdoorconfigBean config, final DDListener ddListener) {
        NetworkHelp.postConfig(context, guid, config, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ddListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ddListener.onErrorResponse(error);
            }
        });
    }

    /**
     * 拉取黑白名单,数据会分页拉取，约定每次最大返回500条，如果每次返回值是500则再拉取一次
     *
     * @param
     * @param guid  设备唯一标识符
     * @param curid 当前操作步数
     */
    public static void getCardInfo(final Context context, final String guid, final int curid, final DDListener ddListener) {
        NetworkHelp.getCardInfo(context, guid, curid, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ddListener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                ddListener.onErrorResponse(error);
            }
        });
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
    public static void uploadVideoOrPicture(Context context, FileType fileType, String fileName, String fileAddress, String guid, String device_type, int operate_type, String objectkey,
                                            long time, String content, String room_id, String reason, String open_time,  final DDListener ddListener) {
        NetworkHelp.uploadVideoOrPicture(DDSDK.getinstance().getContext(), fileType, fileName, fileAddress, guid, device_type, operate_type, objectkey, time,
                content, room_id, reason, open_time, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ddListener.onResponse(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        ddListener.onErrorResponse(error);
                    }
                });
    }
}
