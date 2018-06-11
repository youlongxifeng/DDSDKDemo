package com.dd.sdk.netbean;

import com.google.mgson.annotations.SerializedName;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/5 11:05
 * @change
 * @class describe 开门密码
 */

public class OpenDoorPwd {
    @SerializedName("password")
    public String password;
    @SerializedName("mobile_no")
    public String mobile_no;
    @SerializedName("expire")
    private String expire;
    @SerializedName("room_id")
    public String room_id;
    @SerializedName("mid")
    private long mid;
    @SerializedName("floor")
    private int mFloor;
    /**
     * content
     */
    @SerializedName("content")
    public String content;

    public OpenDoorPwd(){}

    public OpenDoorPwd(long id, String pwd,String mobile,String rid,String expire)
    {
        this.mid = id;
        room_id = rid;
        this.password = pwd;
        this.mobile_no = mobile;
        this.expire = expire;
    }
    public String getExpire() {
        return expire;
    }
    public void setExpire(String expire) {
        this.expire = expire;
    }
    public long getMid() {
        return mid;
    }
    public void setMid(long mid) {
        this.mid = mid;
    }

    public int getFloor()
    {
        return mFloor;
    }


}
