package com.dd.sdk.manage;

import android.content.Context;
import android.os.Handler;

import com.dd.sdk.listener.ConfigurationListener;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.listener.OpenDoorListener;
import com.dd.sdk.netbean.OpenDoorPwd;
import com.dd.sdk.netbean.RequestOpenDoor;
import com.dd.sdk.netbean.SubGsonClass;
import com.dd.sdk.thread.ThreadManager;
import com.dd.sdk.tools.LogUtils;
import com.google.mgson.Gson;
import com.google.mgson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.manage
 * @class describe
 * @time 2018/6/14 16:25
 * @change
 * @class describe
 */

public class ServerCMD {
    private final static String TAG = ServerCMD.class.getSimpleName();
    private static Context mContext;
    private static String mGuid;
    private static InstructionListener mInstructionListener;
    private static ConfigurationListener sConfigurationListener;
    private Handler mHandler=new Handler();


    public static void setStringConect(String msg, Context context, String guid, InstructionListener listener,ConfigurationListener configurationListener) {
        mInstructionListener = listener;
        mContext = context;
        mGuid = guid;
        sConfigurationListener=configurationListener;
        try {
            JSONObject json = new JSONObject(msg);
            if (json != null) {
                String methodName = json.getString("cmd");
                String uuid = json.getString("request_id");

                //if(guid==uuid)
                if ("heart_beat".equalsIgnoreCase(methodName)) {

                } else if ("open_doort".equalsIgnoreCase(methodName)) {//开门指令
                    String content = json.getString("response_params");
                    open_doort(content);
                } else if ("get_config".equalsIgnoreCase(methodName)) {//配置拉取指令
                    if (mContext != null) {
                        sConfigurationListener.getConfigCmd(mContext, mGuid, "5000");
                    }
                } else if ("reboot".equalsIgnoreCase(methodName)) {// 重启指令
                    if (mInstructionListener != null) {
                        mInstructionListener.reBoot();
                    }
                } else if ("cardNew".equalsIgnoreCase(methodName)) {//拉取黑白名单指令
                    LogUtils.i(TAG, "onMessageResponse MSG=" + msg);
                    cardNew();

                } else if ("randomPwd".equalsIgnoreCase(methodName)) {//网络密码指令
                    String content = json.getString("response_params");
                    randomPwd(content);


                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开门指令
     * @param content
     */
    private static void open_doort(final String content) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    RequestOpenDoor openDoor = null;

                    JSONObject o = new JSONObject(content);
                    JSONArray a = o.getJSONArray("data");
                    int type = OpenDoorListener.TYPE_PHONE_OPEN_DOOR, floor = 0;

                    String roomId = "", attach = "", sn = "";
                    if (a.length() > 0) {
                        JSONObject tmp = a.getJSONObject(0);
                        roomId = tmp.getString("room_id");
                        if (tmp.has("content")) {
                            attach = tmp.getString("content");
                        } else if (tmp.has("device_guid")) {
                            attach = "-" + tmp.getString("device_guid");
                        }
                        if (tmp.has("operate_type")) {
                            type = tmp.getInt("operate_type");
                        }
                        if (tmp.has("floor")) {
                            floor = tmp.getInt("floor");
                        }
                        if (tmp.has("sn")) {
                            sn = tmp.getString("sn");
                        }
                        openDoor = new RequestOpenDoor();
                        openDoor.setOperateType(type);
                        openDoor.setAttach(attach);
                        openDoor.setRoomId(roomId);
                        openDoor.setSn(sn);
                        openDoor.setFloor(floor);
                        if (mInstructionListener != null) {
                            mInstructionListener.openDoor(openDoor);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //sendReponse("open_door", String.format("client error %s ,%s", content, e.getLocalizedMessage()), false);
                }
            }
        };
        ThreadManager.getThreadPollProxy().execute(runnable);
    }

    /**
     * 拉取黑白名单指令
     */
    private static void cardNew(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (mContext != null) {
                    int curid = 0;
                    if (mInstructionListener != null) {
                        curid = mInstructionListener.nameListCurid();
                    }
                    sConfigurationListener.getCardInfoCmd(mContext, mGuid, curid);
                }
            }
        };
        ThreadManager.getThreadPollProxy().execute(runnable);
    }

    /**
     * 网络密码指令
     * @param content
     */
    private static void randomPwd(final String content) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (content != null) {
                    try {
                        Type t = new TypeToken<SubGsonClass<OpenDoorPwd>>() {
                        }.getType();
                        Gson mGson = new Gson();
                        SubGsonClass<OpenDoorPwd> d = mGson.fromJson(content, t);
                        if (d.isSuccess() && d.hasData()) {
                            OpenDoorPwd pwd = d.getData().get(0);
                            if (mInstructionListener != null) {
                                mInstructionListener.getNetworkCipher(pwd);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                }
            }
        };
        ThreadManager.getThreadPollProxy().execute(runnable);
    }
}
