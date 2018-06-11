package com.dd.sdk.netbean;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/5 15:30
 * @change
 * @class describe
 */

import com.google.mgson.annotations.SerializedName;
/**
 * 服务器返回的设备配信息
 * @author Administrator
 *
 */
public class DoorConfig {
    /**
     * SecocdServerIP 服务器ip: 用于修改新的ip（未使用） SecocdServerPort 服务器端口:
     * 用于修改新的ip端口（未使用） OpenTime 开门延时(秒)： sip电话开门后几秒自动挂断 CallDuration 通话时长:
     * 限制sip电话通话时长 RoomLength 房号长度: 未使用 sipDomain sip域名 CallProfix 呼叫前缀:
     * 在所呼叫的sip号码前面加的前缀，可用于规则。 DeviceName 设备名称 sipNO uint sip账号 sipPwd uint
     * sip密码 ManageNo 管理中心电话 ServerNO 客服电话
     */
    // 用于修改新的ip
    @SerializedName("SecocdServerIP")
    private String SecocdServerIP;
    // 用于修改新的port
    @SerializedName("SecocdServerPort")
    private String SecocdServerPort;

    // 开门延时
    @SerializedName("OpenTime")
    private String OpenTime;

    // sip电话通话时长
    @SerializedName("CallDuration")
    private String CallDuration;
    // RoomLength
    @SerializedName("RoomLength")
    private String RoomLength;
    // sip域名
    @SerializedName("sipDomain")
    private String sipDomain;

    // 在所呼叫的sip号码前面加的前缀，可用于规则
    @SerializedName("CallProfix")
    private String CallProfix;
    // sip账号
    @SerializedName("sipNO")
    private String sipNO;
    // sip密码
    @SerializedName("sipPwd")
    private String sipPwd;

    // 显示名
    @SerializedName("DeviceName")
    private String DeviceName;

    // 开门键
    @SerializedName("openKey")
    private String openKey;

    // 管理中心号码
    @SerializedName("ManageNO")
    private String ManageNO;
    // 客服电话
    @SerializedName("ServerNO")
    private String ServerNO;
    @SerializedName("ManageCallNo")
    private String ManageCallNo;
    @SerializedName("mobile")
    private String SmsNumber;
    /**
     * 一个时间控制设备在没有网络的时间超过此设定后将自动重启
     */
    @SerializedName("time")
    private long time;

    /**
     * ap 名称
     */
    @SerializedName("ssid")
    public String ssid;
    /**
     * ap 密码
     */
    @SerializedName("pwd")
    public String pwd;
    /**
     * ap 通信秘钥
     */
    @SerializedName("secret")
    public String secret;
    @SerializedName("companyAdsPhone")
    public String companyAdsPhone;
    /**
     * 0 为单元机 1 为围墙机 2 为小围墙机
     */
    @SerializedName("localType")
    private int mWaller; //

    public boolean mUseing;


    public boolean isWaller() {
        return mWaller != 0;
    }

    public void setWaller(int waller) {
        mWaller = waller;
    }

    public String getSipNO() {
        return sipNO;
    }

    public long getTime() {
        return time;
    }

    public void setSipNO(String sipNO) {
        this.sipNO = sipNO;
    }

    public String getSipPwd() {
        return sipPwd;
    }

    public void setSipPwd(String sipPwd) {
        this.sipPwd = sipPwd;
    }

    public String getSipDomain() {
        return sipDomain;
    }

    public void setSipDomain(String sipDomain) {
        this.sipDomain = sipDomain;
    }

    public String getDeviceName() {
        return DeviceName;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public void setOpenTime(String openTime) {
        OpenTime = openTime;
    }

    public String getOpenKey() {
        return openKey;
    }

    public void setOpenKey(String openKey) {
        this.openKey = openKey;
    }

    public String getCallDuration() {
        return CallDuration;
    }

    public void setCallDuration(String callDuration) {
        CallDuration = callDuration;
    }

    public String getCallProfix() {
        return CallProfix;
    }

    public void setCallProfix(String callProfix) {
        CallProfix = callProfix;
    }

    public String getSecocdServerIP() {
        return SecocdServerIP;
    }

    public void setSecocdServerIP(String secocdServerIP) {
        SecocdServerIP = secocdServerIP;
    }

    public String getSecocdServerPort() {
        return SecocdServerPort;
    }

    public void setSecocdServerPort(String secocdServerPort) {
        SecocdServerPort = secocdServerPort;
    }

    public String getManageNO() {
        return ManageNO;
    }

    public void setManageNO(String manageNO) {
        ManageNO = manageNO;
    }

    public String getServerNO() {
        return ServerNO;
    }

    public void setServerNO(String serverNO) {
        ServerNO = serverNO;
    }

    public void setManagerCall(String managerCallNo) {
        ManageCallNo = managerCallNo;
    }

    protected String getManagerCall() {
        return ManageCallNo ;
    }

    public String getSmsNumber() {
        return SmsNumber;
    }

   /* @Override
    public String toString() {
        return new Gson().toJson(this);
    }*/

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoorConfig{");
        sb.append("SecocdServerIP='").append(SecocdServerIP).append('\'');
        sb.append(", SecocdServerPort='").append(SecocdServerPort).append('\'');
        sb.append(", OpenTime='").append(OpenTime).append('\'');
        sb.append(", CallDuration='").append(CallDuration).append('\'');
        sb.append(", RoomLength='").append(RoomLength).append('\'');
        sb.append(", sipDomain='").append(sipDomain).append('\'');
        sb.append(", CallProfix='").append(CallProfix).append('\'');
        sb.append(", sipNO='").append(sipNO).append('\'');
        sb.append(", sipPwd='").append(sipPwd).append('\'');
        sb.append(", DeviceName='").append(DeviceName).append('\'');
        sb.append(", openKey='").append(openKey).append('\'');
        sb.append(", ManageNO='").append(ManageNO).append('\'');
        sb.append(", ServerNO='").append(ServerNO).append('\'');
        sb.append(", ManageCallNo='").append(ManageCallNo).append('\'');
        sb.append(", SmsNumber='").append(SmsNumber).append('\'');
        sb.append(", time=").append(time);
        sb.append(", ssid='").append(ssid).append('\'');
        sb.append(", pwd='").append(pwd).append('\'');
        sb.append(", secret='").append(secret).append('\'');
        sb.append(", companyAdsPhone='").append(companyAdsPhone).append('\'');
        sb.append(", mWaller=").append(mWaller);
        sb.append(", mUseing=").append(mUseing);
        sb.append('}');
        return sb.toString();
    }
}
