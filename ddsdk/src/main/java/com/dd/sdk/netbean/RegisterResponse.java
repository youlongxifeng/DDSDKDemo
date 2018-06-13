package com.dd.sdk.netbean;

import java.io.Serializable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/13 14:59
 * @change
 * @class describe
 */

public class RegisterResponse implements Serializable{
    /**
     * 20010	设备已绑定
     20011	设备注册失败
     20012	设备已注册
     20013	设备未注册
     20014	设备未绑定
     */
    public final static int RESPONSE_SUCCESS=200;
    public final static int ALREADY_BOUND=20010;
    public final static int REGISTER_FAIL=20011;
    public final static int ALREADY_REGISTERED=20012;
    public final static int NO_REGISTER=20013;
    public final static int NO_BOUND=20014;
    public int code;
    public String msg;
}