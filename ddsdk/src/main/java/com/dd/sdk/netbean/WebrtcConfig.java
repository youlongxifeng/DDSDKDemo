package com.dd.sdk.netbean;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/13 17:18
 * @change
 * @class describe
 */

public class WebrtcConfig {
    /**
     *  "sip_server": "hj.doordu.com",
     "tls_port": 5061,
     "tcp_port": 5060,
     "udp_port": 5060,
     "ice": 0,
     "coturn_server": null,
     "coturn_port": null,
     "coturn_user": null,
     "coturn_pass": null,
     "rtcp_fb": 0,
     "scheme": 2 // 0:tls 1:tcp 2:udp
     */
    private String sip_server;
    private int tls_port;
    private int tcp_port;
    private int udp_port;
    private int ice;
    private String coturn_server;
    private String coturn_port;
    private String coturn_user;
    private String coturn_pass;
    private int rtcp_fb;
    private int scheme;

    public String getSip_server() {
        return sip_server;
    }

    public void setSip_server(String sip_server) {
        this.sip_server = sip_server;
    }

    public int getTls_port() {
        return tls_port;
    }

    public void setTls_port(int tls_port) {
        this.tls_port = tls_port;
    }

    public int getTcp_port() {
        return tcp_port;
    }

    public void setTcp_port(int tcp_port) {
        this.tcp_port = tcp_port;
    }

    public int getUdp_port() {
        return udp_port;
    }

    public void setUdp_port(int udp_port) {
        this.udp_port = udp_port;
    }

    public int getIce() {
        return ice;
    }

    public void setIce(int ice) {
        this.ice = ice;
    }

    public String getCoturn_server() {
        return coturn_server;
    }

    public void setCoturn_server(String coturn_server) {
        this.coturn_server = coturn_server;
    }

    public String getCoturn_port() {
        return coturn_port;
    }

    public void setCoturn_port(String coturn_port) {
        this.coturn_port = coturn_port;
    }

    public String getCoturn_user() {
        return coturn_user;
    }

    public void setCoturn_user(String coturn_user) {
        this.coturn_user = coturn_user;
    }

    public String getCoturn_pass() {
        return coturn_pass;
    }

    public void setCoturn_pass(String coturn_pass) {
        this.coturn_pass = coturn_pass;
    }

    public int getRtcp_fb() {
        return rtcp_fb;
    }

    public void setRtcp_fb(int rtcp_fb) {
        this.rtcp_fb = rtcp_fb;
    }

    public int getScheme() {
        return scheme;
    }

    public void setScheme(int scheme) {
        this.scheme = scheme;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("WebrtcConfig{");
        sb.append("sip_server='").append(sip_server).append('\'');
        sb.append(", tls_port=").append(tls_port);
        sb.append(", tcp_port=").append(tcp_port);
        sb.append(", udp_port=").append(udp_port);
        sb.append(", ice=").append(ice);
        sb.append(", coturn_server='").append(coturn_server).append('\'');
        sb.append(", coturn_port='").append(coturn_port).append('\'');
        sb.append(", coturn_user='").append(coturn_user).append('\'');
        sb.append(", coturn_pass='").append(coturn_pass).append('\'');
        sb.append(", rtcp_fb=").append(rtcp_fb);
        sb.append(", scheme=").append(scheme);
        sb.append('}');
        return sb.toString();
    }
}
