package com.dd.sdk.netbean;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/9 13:34
 * @change
 * @class describe
 */

public class UpdoorconfigBean {
    /**
     * 视频分辨率
     */
    private String videosize;
    /**
     * 显示屏幕分辨率 1920x1032 屏幕宽 x 高
     */
    private String size_wxh;
    /**
     * mode	string	设备类型	doordu-d133-v1（13.3吋）
     * Doordu-d101-v1(10吋)
     */
    private String mode;
    /**
     * 设备信息
     */
    private DeviceInfo device;
    /**
     * 支持人脸识别	1支持，其它不支持
     */
    private int face_detect;

    /**
     * 麦克风增益
     */
    int micphoneGain;
    /**
     * 禁用m1功能开发	1 m1卡已经禁止，其它未禁止
     */
    private int disable_m1_card;
    /**
     * 网络类型	1 移动网络，默认有线
     */
    private int netType;
    /**
     * 支持功能
     */
    private int openflag;
    /**
     * 通话音量
     */
    private int callvolume;
    /**
     * 喇叭增益
     */
    private int speakerGain;
    /**
     * 动态秘钥支持开发	1 支持，其它不支持
     */
    private int totp;
    /**
     * 广告音量
     */
    private int advolume;
    /**
     * 蓝牙开门功能支持	1 支持，其它不支持
     */
    private int bluetooth_open_door;


}
