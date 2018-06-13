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


}
