package com.dd.sdk.net.rgw.impl;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.rgw.impl
 * @class describe
 * @time 2018/6/19 17:17
 * @change
 * @class describe
 */


@SuppressWarnings("SameParameterValue")
public class RgwAdminException extends RuntimeException {
    private final int statusCode;

    public RgwAdminException(int statusCode) {
        this.statusCode = statusCode;
    }

    public RgwAdminException(int statusCode, String messageCode) {
        super(messageCode);
        this.statusCode = statusCode;
    }

    public RgwAdminException(int statusCode, String messageCode, Throwable cause) {
        super(messageCode, cause);
        this.statusCode = statusCode;
    }

    public int status() {
        return statusCode;
    }
}
