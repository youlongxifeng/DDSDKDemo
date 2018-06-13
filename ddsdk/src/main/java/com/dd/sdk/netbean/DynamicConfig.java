package com.dd.sdk.netbean;

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
    private String push_domain;
    private String heart_time;
    private String heart_domain;
    private String voip_domain;
    private String door_api_domain;
    private String temp_password;
    private int face_detect_debug;
    private int face_open_door;
    private int face_open_door_similarity;
    private int face_capture;
    private int disable_ad_apk;
}
