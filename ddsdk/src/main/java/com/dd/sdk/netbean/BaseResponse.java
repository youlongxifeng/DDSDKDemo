package com.dd.sdk.netbean;

import com.google.gson.annotations.SerializedName;

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
    /**
     * 20010	设备已绑定
     20011	设备注册失败
     20012	设备已注册
     */
    public final static int ALREADY_BOUND=20010;
    public final static int REGISTER_FAIL=20011;
    public final static int ALREADY_REGISTERED=20012;
    @SerializedName("code")
    public int code;
    @SerializedName("data")
    public T data;
    @SerializedName("msg")
    public String msg;

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

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BaseResponse{");
        sb.append("code=").append(code);
        sb.append(", data=").append(data);
        sb.append(", msg='").append(msg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
