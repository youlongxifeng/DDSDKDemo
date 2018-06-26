package com.dd.sdk.net;

import java.io.Serializable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net
 * @class describe
 * @time 2018/6/25 18:16
 * @change
 * @class describe
 */

public class RequestError implements Serializable {
    private String detailMessage;

    public RequestError(String detailMessage){
        this.detailMessage=detailMessage;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        this.detailMessage = detailMessage;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RequestError{");
        sb.append("detailMessage='").append(detailMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
