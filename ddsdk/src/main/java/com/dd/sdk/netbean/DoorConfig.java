package com.dd.sdk.netbean;

import java.io.Serializable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/13 16:09
 * @change
 * @class describe 设备信息
 */

public class DoorConfig implements Serializable {
    /**
     *
     */
    /**
     * string	服务器ip: 用于修改新的ip（未使用）
     */
    private String secocdServerIP;
    /**
     * string	服务器端口: 用于修改新的ip端口（未使用）
     */
    private String secocdServerPort;
    /**
     * string	开门延时(秒)：sip电话开门后几秒自动挂断
     */
    private String openTime;
    /**
     * string	开门号
     */
    private String openKey;
    /**
     * string	呼叫前缀:在所呼叫的sip号码前面加的前缀，可用于规则。
     */
    private String callProfix;
    /**
     * string	通话时长: 限制sip电话通话时长
     */
    private String callDuration;
    /**
     * string	AP 热点名称
     */
    private String ssid;
    /**
     * string	AP 热点密码
     */
    private String pwd;
    /**
     * string	AP 热点密钥
     */
    private String secret;
    /**
     * string	二维码图片地址
     */
    private String qrcode;
    /**
     * string	二维码描述文字
     */
    private String qrcode_hint;
    /**
     * string	代理商电话
     */
    private String agent_mobile;
    /**
     * string	设备名
     */
    private String door_name;
    private String dial_hint;
    private String background;
    private String hint;
    /**
     * string	设备 ID
     */
    private String device_id;
    /**
     * string	门禁机 sip 帐号
     */
    private String sipNO;
    /**
     * string	门禁机 sip 密码
     */
    private String sipPwd;
    /**
     * string	sip 服务域名
     */
    private String sipDomain;
    /**
     * string	管理处电话
     */
    private String manageNO;
    /**
     * string	管理处房号（用于门禁机按管理处键呼叫）
     */
    private String manageCallNO;
    /**
     * string	客户电话
     */
    private String ServerNO;
    /**
     * string	安装电话
     */
    private String SetupNO;
    /**
     * string	广告投放热线
     */
    private String companyAdsPhone;
    /**
     * string	单元机或围墙机.0:单元机，1：围墙机
     */
    private String localType;
    /**
     * string	新的 FreeSwitch 配置信息
     */
    private WebrtcConfig webrtcconfig;
    /**
     * string	系统卡密钥
     */
    private String init_code;
    /**
     * string	小区管理密钥
     */
    private String sys_code;
    /**
     * string	门磁报警开关 0：关，1：开
     */
    private String door_alert;
    /**
     * string	门常开报警检查间隔时间
     */
    private String door_alert_interval;
    /**
     * string	设备绑定状态 1：已绑定，0：未绑定
     */
    private String status;
    /**
     * string	door v5.9 新增参数，M1 卡动态密钥防复制开关，枚举值：1(开), 0(关)
     */
    private String ic_card_copy;
    /**
     * string	是否打开监控摄像头，枚举值：0(不允许), 1(允许)
     */
    private String open_monitor;
    /**
     * door v5.9 新增参数，是否禁用 M1 卡，枚举值：0(不禁用), 1(禁用)
     */
    private String disable_m1_card;
    /**
     * 动态配置，如果没有配置则为空对象
     */
    private DynamicConfig dynamic_config;
    /**
     * string	门禁一体机新增参数，小区 ID
     */
    private String dep_id;
    /**
     * 门禁一体机新增参数，小区类型，1住宅小区 2企事业单位 3 公园 4写字楼5学校 6商场
     */
    private String dep_type;
    /**
     * string	考勤是否开启拔号功能 1:开启 0：关闭
     */
    private String is_dial;
    /**
     * string	门禁机是否支持动态令牌的下发
     */
    private String is_support_token;
    /**
     * string	门禁机动态令牌的下发
     */
    private String dynamic_token;
    private String bluetooth_open_door;
    /** 配置指纹，门禁机根据这个来判断配置是否有变动*/
    private String hash;

    /**
     * 获取桶信息
     * @return
     */
    private String bucket_name;


    public String getSecocdServerIP() {
        return secocdServerIP;
    }

    public void setSecocdServerIP(String secocdServerIP) {
        this.secocdServerIP = secocdServerIP;
    }

    public String getSecocdServerPort() {
        return secocdServerPort;
    }

    public void setSecocdServerPort(String secocdServerPort) {
        this.secocdServerPort = secocdServerPort;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenKey() {
        return openKey;
    }

    public void setOpenKey(String openKey) {
        this.openKey = openKey;
    }

    public String getCallProfix() {
        return callProfix;
    }

    public void setCallProfix(String callProfix) {
        this.callProfix = callProfix;
    }

    public String getCallDuration() {
        return callDuration;
    }

    public void setCallDuration(String callDuration) {
        this.callDuration = callDuration;
    }

    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getQrcode_hint() {
        return qrcode_hint;
    }

    public void setQrcode_hint(String qrcode_hint) {
        this.qrcode_hint = qrcode_hint;
    }

    public String getAgent_mobile() {
        return agent_mobile;
    }

    public void setAgent_mobile(String agent_mobile) {
        this.agent_mobile = agent_mobile;
    }

    public String getDoor_name() {
        return door_name;
    }

    public void setDoor_name(String door_name) {
        this.door_name = door_name;
    }

    public String getDial_hint() {
        return dial_hint;
    }

    public void setDial_hint(String dial_hint) {
        this.dial_hint = dial_hint;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getSipNO() {
        return sipNO;
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

    public String getManageNO() {
        return manageNO;
    }

    public void setManageNO(String manageNO) {
        this.manageNO = manageNO;
    }

    public String getManageCallNO() {
        return manageCallNO;
    }

    public void setManageCallNO(String manageCallNO) {
        this.manageCallNO = manageCallNO;
    }

    public String getServerNO() {
        return ServerNO;
    }

    public void setServerNO(String serverNO) {
        ServerNO = serverNO;
    }

    public String getSetupNO() {
        return SetupNO;
    }

    public void setSetupNO(String setupNO) {
        SetupNO = setupNO;
    }

    public String getCompanyAdsPhone() {
        return companyAdsPhone;
    }

    public void setCompanyAdsPhone(String companyAdsPhone) {
        this.companyAdsPhone = companyAdsPhone;
    }

    public String getLocalType() {
        return localType;
    }

    public void setLocalType(String localType) {
        this.localType = localType;
    }

    public WebrtcConfig getWebrtcconfig() {
        return webrtcconfig;
    }

    public void setWebrtcconfig(WebrtcConfig webrtcconfig) {
        this.webrtcconfig = webrtcconfig;
    }

    public String getInit_code() {
        return init_code;
    }

    public void setInit_code(String init_code) {
        this.init_code = init_code;
    }

    public String getSys_code() {
        return sys_code;
    }

    public void setSys_code(String sys_code) {
        this.sys_code = sys_code;
    }

    public String getDoor_alert() {
        return door_alert;
    }

    public void setDoor_alert(String door_alert) {
        this.door_alert = door_alert;
    }

    public String getDoor_alert_interval() {
        return door_alert_interval;
    }

    public void setDoor_alert_interval(String door_alert_interval) {
        this.door_alert_interval = door_alert_interval;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIc_card_copy() {
        return ic_card_copy;
    }

    public void setIc_card_copy(String ic_card_copy) {
        this.ic_card_copy = ic_card_copy;
    }

    public String getOpen_monitor() {
        return open_monitor;
    }

    public void setOpen_monitor(String open_monitor) {
        this.open_monitor = open_monitor;
    }

    public String getDisable_m1_card() {
        return disable_m1_card;
    }

    public void setDisable_m1_card(String disable_m1_card) {
        this.disable_m1_card = disable_m1_card;
    }

    public DynamicConfig getDynamic_config() {
        return dynamic_config;
    }

    public void setDynamic_config(DynamicConfig dynamic_config) {
        this.dynamic_config = dynamic_config;
    }

    public String getDep_id() {
        return dep_id;
    }

    public void setDep_id(String dep_id) {
        this.dep_id = dep_id;
    }

    public String getDep_type() {
        return dep_type;
    }

    public void setDep_type(String dep_type) {
        this.dep_type = dep_type;
    }

    public String getIs_dial() {
        return is_dial;
    }

    public void setIs_dial(String is_dial) {
        this.is_dial = is_dial;
    }

    public String getIs_support_token() {
        return is_support_token;
    }

    public void setIs_support_token(String is_support_token) {
        this.is_support_token = is_support_token;
    }

    public String getDynamic_token() {
        return dynamic_token;
    }

    public void setDynamic_token(String dynamic_token) {
        this.dynamic_token = dynamic_token;
    }

    public String getBluetooth_open_door() {
        return bluetooth_open_door;
    }

    public void setBluetooth_open_door(String bluetooth_open_door) {
        this.bluetooth_open_door = bluetooth_open_door;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }


    public String getBucket_name() {
        return bucket_name;
    }

    public void setBucket_name(String bucket_name) {
        this.bucket_name = bucket_name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DoorConfig{");
        sb.append("secocdServerIP='").append(secocdServerIP).append('\'');
        sb.append(", secocdServerPort='").append(secocdServerPort).append('\'');
        sb.append(", openTime='").append(openTime).append('\'');
        sb.append(", openKey='").append(openKey).append('\'');
        sb.append(", callProfix='").append(callProfix).append('\'');
        sb.append(", callDuration='").append(callDuration).append('\'');
        sb.append(", ssid='").append(ssid).append('\'');
        sb.append(", pwd='").append(pwd).append('\'');
        sb.append(", secret='").append(secret).append('\'');
        sb.append(", qrcode='").append(qrcode).append('\'');
        sb.append(", qrcode_hint='").append(qrcode_hint).append('\'');
        sb.append(", agent_mobile='").append(agent_mobile).append('\'');
        sb.append(", door_name='").append(door_name).append('\'');
        sb.append(", dial_hint='").append(dial_hint).append('\'');
        sb.append(", background='").append(background).append('\'');
        sb.append(", hint='").append(hint).append('\'');
        sb.append(", device_id='").append(device_id).append('\'');
        sb.append(", sipNO='").append(sipNO).append('\'');
        sb.append(", sipPwd='").append(sipPwd).append('\'');
        sb.append(", sipDomain='").append(sipDomain).append('\'');
        sb.append(", manageNO='").append(manageNO).append('\'');
        sb.append(", manageCallNO='").append(manageCallNO).append('\'');
        sb.append(", ServerNO='").append(ServerNO).append('\'');
        sb.append(", SetupNO='").append(SetupNO).append('\'');
        sb.append(", companyAdsPhone='").append(companyAdsPhone).append('\'');
        sb.append(", localType='").append(localType).append('\'');
        sb.append(", webrtcconfig=").append(webrtcconfig);
        sb.append(", init_code='").append(init_code).append('\'');
        sb.append(", sys_code='").append(sys_code).append('\'');
        sb.append(", door_alert='").append(door_alert).append('\'');
        sb.append(", door_alert_interval='").append(door_alert_interval).append('\'');
        sb.append(", status='").append(status).append('\'');
        sb.append(", ic_card_copy='").append(ic_card_copy).append('\'');
        sb.append(", open_monitor='").append(open_monitor).append('\'');
        sb.append(", disable_m1_card='").append(disable_m1_card).append('\'');
        sb.append(", dynamic_config=").append(dynamic_config);
        sb.append(", dep_id='").append(dep_id).append('\'');
        sb.append(", dep_type='").append(dep_type).append('\'');
        sb.append(", is_dial='").append(is_dial).append('\'');
        sb.append(", is_support_token='").append(is_support_token).append('\'');
        sb.append(", dynamic_token='").append(dynamic_token).append('\'');
        sb.append(", bluetooth_open_door='").append(bluetooth_open_door).append('\'');
        sb.append(", hash='").append(hash).append('\'');
        sb.append(", bucket_name='").append(bucket_name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
