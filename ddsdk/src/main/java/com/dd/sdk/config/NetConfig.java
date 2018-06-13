package com.dd.sdk.config;

import java.io.Serializable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.config
 * @class describe
 * @time 2018/6/11 11:54
 * @change
 * @class describe
 */

public class NetConfig implements Serializable {

    /**
     * 访问接口时需调用此接口获取访问凭证token。为避免额外消耗，请访问者在本地保存返回的token值。
     */
    public final static String TOKEN = "/v1/token";
    /**
     * 设置注册接口。如果设备已注册，直接返回成功。
     */
    public final static String REGISTER = "/v1/device/register";
    /**
     * 获取配置信息，门禁机每次启动时应当请求一次
     */
    public final static String CONFIG = "/v1/config?";
    /**
     * 门禁机上传配置
     */
    public final static String UPDATE_CONFIG = "/v1/config/update";
    /**
     * 获取门禁卡列表
     */
    public final static String GET_CARD = "/v1/device/card?";
    /**
     * 上报访客留影
     */
    public final static String VISITLOG = "/v1/device/visitlog";
    /**
     * 上报视频留影
     */
    public final static String VIDEOLOG = "/v1/device/videolog";
    public final static String CONFIG_BEAN = "config_bean";
    private String ip = "10.0.0.243";                                      // 测试服务器的IP
    private String domain = "10.0.0.243";
    private String httpUrl = "http://10.0.2.152:8888?"; // 测试服务器通信的url
    private int dPort = 9501;                                              // 门口端端口
    private int mMode;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getHttpUrl() {
        return httpUrl;
    }

    public void setHttpUrl(String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public int getdPort() {
        return dPort;
    }

    public void setdPort(int dPort) {
        this.dPort = dPort;
    }


    @Override
    public String toString() {
        return String.format("ip %s, url %s", ip, httpUrl);
    }


    /**
     * 默认正式地址
     *
     * @return
     */
    public static NetConfig getAddress() {
        NetConfig c = new NetConfig();

        return c;
    }


    public static String postDomainName(NetConfig c, String catalog) {
        return "http://" + c.getDomain() + ":" + c.getdPort() + catalog;
    }

    public static String getDomainName(NetConfig c, String config) {
        if (c!=null&&c.getDomain().startsWith("http://")) {//如果前缀包含
            return   c.getDomain() + ":" + c.getdPort() + config;
        } else {
            return "http://" + c.getDomain() + ":" + c.getdPort() + config;
        }
    }
}
