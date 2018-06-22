package com.dd.sdk.netbean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/13 17:21
 * @change
 * @class describe
 */

public class DynamicConfig implements Serializable {
    /**
     *  "push_domain": "xq1.mqtt.doordu.com",
     "heart_time": "30",
     "heart_domain": "xq1.test.swheart.mdoordu.cn"
     "voip_domain": "xq1.test.hj.mdoordu.cn",
     "door_api_domain": "http://xq1.test.door.mdoordu.cn",
     // 开门密码@开门密码过期时间
     "temp_password": "123456@1513850075",
     // 人脸识别功能调试模式开关，枚举值：1(开), 0(关，默认)
     "face_detect_debug": 0,
     // 人脸开门功能开关，枚举值：1(开), 0(关)
     "face_open_door": 0,
     // 人脸开门的相似度，人脸相似度大于或等于这个值的开门成功，默认值：75
     "face_open_door_similarity": 75,
     // 人脸抓拍开关，枚举值：1(开), 0(关，默认值)
     "face_capture": 1,
     // 禁用广告 apk，枚举值：1(禁用), 0(启用，默认值)
     "disable_ad_apk": 1,
     */
    @SerializedName("floor_no")
    private String push_domain;
    @SerializedName("heart_time")
    private String heart_time;
    @SerializedName("heart_domain")
    private String heart_domain;
    @SerializedName("voip_domain")
    private String voip_domain;
    @SerializedName("door_api_domain")
    private String door_api_domain;
    @SerializedName("temp_password")
    private String temp_password;
    @SerializedName("face_detect_debug")
    private int face_detect_debug;
    @SerializedName("face_open_door")
    private int face_open_door;
    @SerializedName("face_open_door_similarity")
    private int face_open_door_similarity;
    @SerializedName("face_capture")
    private int face_capture;
    @SerializedName("disable_ad_apk")
    private int disable_ad_apk;

    public String getPush_domain() {
        return push_domain;
    }

    public void setPush_domain(String push_domain) {
        this.push_domain = push_domain;
    }

    public String getHeart_time() {
        return heart_time;
    }

    public void setHeart_time(String heart_time) {
        this.heart_time = heart_time;
    }

    public String getHeart_domain() {
        return heart_domain;
    }

    public void setHeart_domain(String heart_domain) {
        this.heart_domain = heart_domain;
    }

    public String getVoip_domain() {
        return voip_domain;
    }

    public void setVoip_domain(String voip_domain) {
        this.voip_domain = voip_domain;
    }

    public String getDoor_api_domain() {
        return door_api_domain;
    }

    public void setDoor_api_domain(String door_api_domain) {
        this.door_api_domain = door_api_domain;
    }

    public String getTemp_password() {
        return temp_password;
    }

    public void setTemp_password(String temp_password) {
        this.temp_password = temp_password;
    }

    public int getFace_detect_debug() {
        return face_detect_debug;
    }

    public void setFace_detect_debug(int face_detect_debug) {
        this.face_detect_debug = face_detect_debug;
    }

    public int getFace_open_door() {
        return face_open_door;
    }

    public void setFace_open_door(int face_open_door) {
        this.face_open_door = face_open_door;
    }

    public int getFace_open_door_similarity() {
        return face_open_door_similarity;
    }

    public void setFace_open_door_similarity(int face_open_door_similarity) {
        this.face_open_door_similarity = face_open_door_similarity;
    }

    public int getFace_capture() {
        return face_capture;
    }

    public void setFace_capture(int face_capture) {
        this.face_capture = face_capture;
    }

    public int getDisable_ad_apk() {
        return disable_ad_apk;
    }

    public void setDisable_ad_apk(int disable_ad_apk) {
        this.disable_ad_apk = disable_ad_apk;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DynamicConfig{");
        sb.append("push_domain='").append(push_domain).append('\'');
        sb.append(", heart_time='").append(heart_time).append('\'');
        sb.append(", heart_domain='").append(heart_domain).append('\'');
        sb.append(", voip_domain='").append(voip_domain).append('\'');
        sb.append(", door_api_domain='").append(door_api_domain).append('\'');
        sb.append(", temp_password='").append(temp_password).append('\'');
        sb.append(", face_detect_debug=").append(face_detect_debug);
        sb.append(", face_open_door=").append(face_open_door);
        sb.append(", face_open_door_similarity=").append(face_open_door_similarity);
        sb.append(", face_capture=").append(face_capture);
        sb.append(", disable_ad_apk=").append(disable_ad_apk);
        sb.append('}');
        return sb.toString();
    }
}
