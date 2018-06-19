package com.dd.sdk.net;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dd.sdk.DDSDK;
import com.dd.sdk.common.DeviceInformation;
import com.dd.sdk.common.TokenPrefer;
import com.dd.sdk.config.NetConfig;
import com.dd.sdk.listener.FileType;
import com.dd.sdk.net.volley.DDVolley;
import com.dd.sdk.netbean.AccessToken;
import com.dd.sdk.netbean.UpdoorconfigBean;
import com.dd.sdk.tools.LogUtils;
import com.google.mgson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net
 * @class describe
 * @time 2018/6/11 13:55
 * @change
 * @class describe
 */

public class NetworkHelp {
    final static String TAG = NetworkHelp.class.getSimpleName();
    final static char se = 0x3d; // "=";
    final static char sc = 0x26; // "&";
    private static NetworkHelp sNetworkHelp = new NetworkHelp();


    public static NetworkHelp getInstance() {
        synchronized (NetworkHelp.class) {
            if (sNetworkHelp == null) {
                sNetworkHelp = new NetworkHelp();
            }
        }
        return sNetworkHelp;
    }

    /**
     * 获取接口访问凭证
     * 访问接口时需调用此接口获取访问凭证token。为避免额外消耗，请访问者在本地保存返回的token值
     *
     * @param appid  授权应用ID
     * @param secret 授权密码
     */
    public static void getAccessToken(String appid, String secret, Response.Listener<JSONObject> listener, Response.ErrorListener errlistener) {
        try {
            Map<String, String> params = new HashMap<>();
            params.put("appid", appid);
            params.put("secret", secret);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.TOKEN), new JSONObject(params), listener, errlistener);
            DDVolley.addRequestQueue(request);
        } catch (Exception e) {
        LogUtils.i("getAccessToken   e===="+e);
        }

    }
    /**
     *
     * 注册设备检测
     * @param registerResponseListener
     */
    /**
     * 注册设备 http://114.119.7.101/doordu/api/index.php/device/DeviceReg?device_guid
     * =1866e0df795fb7e&device_type=1&mobile_no=13410309446&v=3.0.6
     */

    public static void getRegisterDevice(Context context, String macAddress, String mobile,
                                         Response.Listener<JSONObject> listener, Response.ErrorListener errlistener) {
        Map<String, String> params = new HashMap<>();
        final DisplayMetrics res = context.getResources().getDisplayMetrics();
        AccessToken accessToken = TokenPrefer.loadConfig(context);
        params.put("token", accessToken.getToken());
        params.put("guid", DeviceInformation.getInstance().getGuid());
        params.put("width", String.valueOf(res.widthPixels));
        params.put("height", String.valueOf(res.heightPixels));
        params.put("cpu", Build.DEVICE);
        params.put("mac", macAddress);
        params.put("mobile", mobile);
        LogUtils.i(TAG, "getRegisterDevice" + new JSONObject(params).toString());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.REGISTER), new JSONObject(params), listener, errlistener);
        DDVolley.addRequestQueue(request);

    }

    /**
     * 程序启动时获取新配置
     * https://door.api.doordu.com/api/index.php/door/config?guid=DDD4001708-05946&version=1.0&timetoken=3&vcode=1&signkey=886595bc32b5c0df21cc0df75cb2492ec514050b     type=com.dd.sdk.netbean.BaseGsonClass<com.dd.sdk.netbean.DoorConfig2>
     */
    public static void getConfig(Context context, String guid, String door_ver, Response.Listener<JSONObject> listener, Response.ErrorListener errlistener) {
        AccessToken accessToken = TokenPrefer.loadConfig(context);
        /*StringBuilder builder = new StringBuilder();
        builder.append("token").append(se).append(accessToken.getToken()).append(sc);
        builder.append("guid").append(se).append(guid).append(sc);
        builder.append("door_ver").append(se).append(door_ver);*/

        Map<String, String> params = new HashMap<>();
        params.put("token", accessToken.getToken());
        params.put("guid", guid);
        params.put("door_ver", door_ver);

        LogUtils.i(TAG, "getConfig     " + NetConfig.getDomainName(DDSDK.netConfig, NetConfig.CONFIG) + new JSONObject(params));
        JsonObjectRequest request = new JsonObjectRequest(Method.GET, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.CONFIG), new JSONObject(params), listener, errlistener);
        DDVolley.addRequestQueue(request);
    }

    /**
     * 上报配置信息
     *
     * @param context
     * @param guid
     * @param config
     * @param listener
     * @param errlistener
     */
    public static void postConfig(Context context, String guid, UpdoorconfigBean config, Response.Listener<JSONObject> listener, Response.ErrorListener errlistener) {
        AccessToken accessToken = TokenPrefer.loadConfig(context);
        Map<String, String> params = new HashMap<>();
        params.put("token", accessToken.getToken());
        params.put("guid", guid);
        Gson gson = new Gson();
        String content = gson.toJson(config);
        params.put("config", content);
        LogUtils.i(TAG, "getConfig     " + NetConfig.getDomainName(DDSDK.netConfig, NetConfig.UPDATE_CONFIG) + " content=" + new JSONObject(params).toString());
        JsonObjectRequest request = new JsonObjectRequest(Method.POST, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.UPDATE_CONFIG), new JSONObject(params), listener, errlistener);
        DDVolley.addRequestQueue(request);
    }

    /**
     * 获取黑白名单信息
     *
     * @param context
     * @param guid
     * @param curid
     * @param listener
     * @param errorListener
     */
    public static void getCardInfo(Context context, String guid, int curid, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        AccessToken accessToken = TokenPrefer.loadConfig(context);
        StringBuilder builder = new StringBuilder();
        builder.append("token").append(se).append(accessToken.getToken()).append(sc);
        builder.append("guid").append(se).append(guid).append(sc);
        builder.append("curid").append(se).append(curid);
        LogUtils.i(TAG, "getCardInfo     " + NetConfig.getDomainName(DDSDK.netConfig, NetConfig.GET_CARD) + builder.toString());
        JsonObjectRequest request = new JsonObjectRequest(Method.GET, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.GET_CARD) + builder.toString(), listener, errorListener);
        DDVolley.addRequestQueue(request);

    }

    /**
     * 上传视频图片访客留影
     *
     * @param context
     * @param guid
     * @param device_type
     * @param operate_type
     * @param objectkey
     * @param time
     * @param content
     * @param room_id
     * @param reason
     * @param open_time
     * @param listener
     * @param errlistener
     */
    public static void uploadVideoOrPicture(Context context, FileType fileType, String fileName, String fileAddress, String guid, String device_type, int operate_type, String objectkey,
                                            long time, String content, String room_id, String reason, String open_time, Response.Listener<JSONObject> listener, Response.ErrorListener errlistener) {
        Map<String, String> params = new HashMap<>();
        AccessToken accessToken = TokenPrefer.loadConfig(context);
        params.put("token", accessToken.getToken());
        params.put("guid", guid);
        params.put("device_type", device_type);//设备类型
        params.put("operate_type", String.valueOf(operate_type));//开门类型
        params.put("objectkey", objectkey);//留影图片地址
        params.put("time", String.valueOf(time));//门禁机时间
        params.put("content", content);//透传字段，具体依据 operate_type 而定，值为urlencode后的字符串
        params.put("room_id", room_id);//房间 ID
        params.put("reason", reason);//摄像头故障状态码
        params.put("open_time", open_time);//13 位 Unix 时间戳，精确到毫秒级，一次开门的视频留影和图片留影应用同一个时间
        LogUtils.i(TAG, "getRegisterDevice" + new JSONObject(params).toString());
        JsonObjectRequest request = null;
        //Constant.VIDEO_TYPE Constant.PICTURE_TYPE
        if (fileType == FileType.PICTURE_TYPE) {//上报图片信息
            request = new JsonObjectRequest(Request.Method.POST, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.VISITLOG), new JSONObject(params), listener, errlistener);
        } else if (fileType == FileType.VIDEO_TYPE) {//上报视频信息
            request = new JsonObjectRequest(Request.Method.POST, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.VIDEOLOG), new JSONObject(params), listener, errlistener);
        }
        DDVolley.addRequestQueue(request);
    }


}
/**
 * private final static void builderGeneral(StringBuilder builder) {
 * builder.append("device_type").append(se).append("1").append(sc);
 * builder.append("sign").append(se).append("").append(sc);
 * builder.append("expires").append(se).append("").append(sc);
 * builder.append("v").append(se).append(BuildConfig.VERSION_NAME).append(sc);
 * builder.append("vcode").append(se).append(BuildConfig.VERSION_CODE);
 * <p>
 * }
 */