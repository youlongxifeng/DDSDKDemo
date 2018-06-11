package com.dd.sdk.config;

import com.dd.sdk.common.DeviceInformation;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.config
 * @class describe
 * @time 2018/6/11 11:54
 * @change
 * @class describe
 */

public class NetConfig {
    private String ip = "10.0.0.243";                                      // 测试服务器的IP
    private String domain = "10.0.0.243";
    private String httpUrl = "https://" + domain + "/doordu-beta/api/index.php"; // 测试服务器通信的url
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

    public boolean isLocalNetwork() {
        return mMode == 1;
    }

    public boolean isRelease() {
        return mMode == 0;
    }

    public String getLikeName() {
        String r = "发布版";
        if (isLocalNetwork()) {
            r = "内测版";
        } else if (mMode == 2) {
            r = "预发布";
        }
        return String.format("%s Guid: %s", r, DeviceInformation.getInstance().getGuid());
    }

    @Override
    public String toString() {
        return String.format("ip %s, url %s", ip, httpUrl);
    }

    /**
     * 获取测试地址
     *
     * @return
     */
    public static NetConfig getTestAddress() {
        NetConfig c = new NetConfig();
        return c;
    }

    /**
     * 内部测试使用 局域网
     *
     * @return
     */
    public static NetConfig getInnerTestAddress() {
        NetConfig c = new NetConfig();
        return c;
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
}
