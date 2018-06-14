package com.dd.sdk.netbean;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/9 11:56
 * @change
 * @class describe apk端返回执行结果
 */

public class ResultBean {
    private int errCode; //0:失败，1：成功
    private String errMsg;//errCode=0时，设置对应错误信息。

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public boolean isSuccess() {
        return errCode == 1 ? true : false;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ResultBean{");
        sb.append("errCode=").append(errCode);
        sb.append(", errMsg='").append(errMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
