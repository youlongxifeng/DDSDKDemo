package com.dd.sdk.netbean;

import java.io.Serializable;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/9 13:34
 * @change
 * @class describe
 */

public class UpdoorconfigBean implements Serializable{
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

    public String getVideosize() {
        return videosize;
    }

    public void setVideosize(String videosize) {
        this.videosize = videosize;
    }

    public String getSize_wxh() {
        return size_wxh;
    }

    public void setSize_wxh(String size_wxh) {
        this.size_wxh = size_wxh;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public DeviceInfo getDevice() {
        return device;
    }

    public void setDevice(DeviceInfo device) {
        this.device = device;
    }

    public int getFace_detect() {
        return face_detect;
    }

    public void setFace_detect(int face_detect) {
        this.face_detect = face_detect;
    }

    public int getMicphoneGain() {
        return micphoneGain;
    }

    public void setMicphoneGain(int micphoneGain) {
        this.micphoneGain = micphoneGain;
    }

    public int getDisable_m1_card() {
        return disable_m1_card;
    }

    public void setDisable_m1_card(int disable_m1_card) {
        this.disable_m1_card = disable_m1_card;
    }

    public int getNetType() {
        return netType;
    }

    public void setNetType(int netType) {
        this.netType = netType;
    }

    public int getOpenflag() {
        return openflag;
    }

    public void setOpenflag(int openflag) {
        this.openflag = openflag;
    }

    public int getCallvolume() {
        return callvolume;
    }

    public void setCallvolume(int callvolume) {
        this.callvolume = callvolume;
    }

    public int getSpeakerGain() {
        return speakerGain;
    }

    public void setSpeakerGain(int speakerGain) {
        this.speakerGain = speakerGain;
    }

    public int getTotp() {
        return totp;
    }

    public void setTotp(int totp) {
        this.totp = totp;
    }

    public int getAdvolume() {
        return advolume;
    }

    public void setAdvolume(int advolume) {
        this.advolume = advolume;
    }

    public int getBluetooth_open_door() {
        return bluetooth_open_door;
    }

    public void setBluetooth_open_door(int bluetooth_open_door) {
        this.bluetooth_open_door = bluetooth_open_door;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UpdoorconfigBean{");
        sb.append("videosize='").append(videosize).append('\'');
        sb.append(", size_wxh='").append(size_wxh).append('\'');
        sb.append(", mode='").append(mode).append('\'');
        sb.append(", device=").append(device);
        sb.append(", face_detect=").append(face_detect);
        sb.append(", micphoneGain=").append(micphoneGain);
        sb.append(", disable_m1_card=").append(disable_m1_card);
        sb.append(", netType=").append(netType);
        sb.append(", openflag=").append(openflag);
        sb.append(", callvolume=").append(callvolume);
        sb.append(", speakerGain=").append(speakerGain);
        sb.append(", totp=").append(totp);
        sb.append(", advolume=").append(advolume);
        sb.append(", bluetooth_open_door=").append(bluetooth_open_door);
        sb.append('}');
        return sb.toString();
    }
}
