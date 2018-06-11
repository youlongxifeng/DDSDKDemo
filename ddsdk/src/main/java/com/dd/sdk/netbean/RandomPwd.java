package com.dd.sdk.netbean;

import android.content.Context;
import android.util.Log;

import com.google.mgson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/5 11:09
 * @change
 * @class describe 密码开门处理
 */

public class RandomPwd {
    private static RandomPwd instance;
    private Context ctx;
    private static boolean isRun = false;
    /** 密码开门时查找到的电话号码 **/
    @SerializedName("telphone")
    private String telphone = "";
    /** 房号ID **/
    @SerializedName("roomId")
    private String roomId = "";

    private RandomPwd() {
    }

    public static RandomPwd getInstance(Context context) {
        if (instance == null) {
            instance = new RandomPwd();
        }
        instance.ctx = context;
        return instance;
    }

    /** 密码开门成功时查找到的电话号码 **/
    public String getTelphone() {
        return telphone;
    }

    /** 房号ID **/
    public String getRoomId() {
        return roomId;
    }

    /**
     * 增加密码时间
     * @param pwd
     * @return
     */
    public boolean add(OpenDoorPwd pwd) {
      /*  DBMananger sqlHelper = new DBMananger(ctx);
        if(TextUtils.isEmpty(pwd.getExpire())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DoorduApplication app = (DoorduApplication) ctx.getApplicationContext();
            Date expire = new Date(app.getMillis() + Constant.passwdOpenExpire);
            pwd.setExpire(sdf.format(expire));
        }

        Log.d("passwdOpen", "phone:" + pwd.mobile_no + " 创建密码" + pwd.password + "写入链表过期时间:" + pwd.getExpire());

        sqlHelper.insertPwd(pwd);
        sendToServer(pwd.mobile_no, pwd.password, 1); // 发给服务器
        sqlHelper.realse();*/
        return true;
    }

    /**
     *  时间比较
     * @param pwd
     * @param curTime
     * @param oldDate
     * @return
     */

    public static boolean dateCompare(String pwd,long curTime,String oldDate) {
        // 设定时间的模板
        boolean r = false;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date historyDate = format.parse(oldDate);
            // 比较
            r = historyDate.getTime() > curTime;
            Log.i("TAG",String.format("pwd %s,system time %d, expire %s, valid %s ",pwd,curTime,oldDate,r));
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG",String.format("compare time exception,pwd %s, expire %s ",pwd,oldDate));
        }
        return r;
    }

    /**
     * 添加，删除后发送给服务器
     *
     * @param phone
     * @param password
     * @param flag
     *            是添加还是删除 1 or 0
     */
    private void sendToServer(final String phone, final String password, final int flag) {

    }



}
