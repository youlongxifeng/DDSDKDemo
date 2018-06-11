package com.dd.sdk.netbean;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/11 11:52
 * @change
 * @class describe
 */

public class BaseResponse<T> {
    public int code;
    public T data;

    public boolean isSuccess() {
        return  code==200;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
