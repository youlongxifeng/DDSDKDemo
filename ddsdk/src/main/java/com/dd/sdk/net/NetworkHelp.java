package com.dd.sdk.net;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dd.sdk.BuildConfig;
import com.dd.sdk.DDSDK;
import com.dd.sdk.common.DeviceInformation;
import com.dd.sdk.common.TokenPrefer;
import com.dd.sdk.config.NetConfig;
import com.dd.sdk.netbean.AccessToken;
import com.dd.sdk.netbean.UpdoorconfigBean;
import com.dd.sdk.tools.LogUtils;

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

        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appid);
        params.put("secret", secret);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.TOKEN), new JSONObject(params), listener, errlistener);
        DDSDK.addRequestQueue(request);
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
        DDSDK.addRequestQueue(request);

    }

    /**
     * 程序启动时获取新配置
     * https://door.api.doordu.com/api/index.php/door/config?guid=DDD4001708-05946&version=1.0&timetoken=3&vcode=1&signkey=886595bc32b5c0df21cc0df75cb2492ec514050b     type=com.dd.sdk.netbean.BaseGsonClass<com.dd.sdk.netbean.DoorConfig2>
     */
    public static void getConfig(Context context, String guid, String door_ver, Response.Listener<JSONObject> listener, Response.ErrorListener errlistener) {
        AccessToken accessToken = TokenPrefer.loadConfig(context);
        StringBuilder builder = new StringBuilder();
        builder.append("token").append(se).append(accessToken.getToken()).append(sc);
        builder.append("guid").append(se).append(guid).append(sc);
        builder.append("door_ver").append(se).append(door_ver);


        LogUtils.i(TAG, "getConfig     " + NetConfig.getDomainName(DDSDK.netConfig, NetConfig.CONFIG) + builder.toString());
        JsonObjectRequest request = new JsonObjectRequest(Method.GET, NetConfig.getDomainName(DDSDK.netConfig, NetConfig.CONFIG) + builder.toString(), listener, errlistener);
        DDSDK.addRequestQueue(request);
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
        LogUtils.i(TAG, "getConfig     " + NetConfig.getDomainName(DDSDK.netConfig, NetConfig.UPDATE_CONFIG));
        JsonObjectRequest request = new JsonObjectRequest(Method.GET, NetConfig.postDomainName(DDSDK.netConfig, NetConfig.UPDATE_CONFIG), new JSONObject(params), listener, errlistener);
        DDSDK.addRequestQueue(request);
    }


    private final static void builderGeneral(StringBuilder builder) {
        builder.append("device_type").append(se).append("1"/*Device.deviceType*/).append(sc);
        builder.append("sign").append(se).append("").append(sc);
        builder.append("expires").append(se).append("").append(sc);
        builder.append("v").append(se).append(BuildConfig.VERSION_NAME).append(sc);
        builder.append("vcode").append(se).append(BuildConfig.VERSION_CODE);

    }
}
