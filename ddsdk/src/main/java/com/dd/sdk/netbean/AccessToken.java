package com.dd.sdk.netbean;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/11 11:49
 * @change
 * @class describe
 */


public class AccessToken implements Serializable {
    /**access_token**/
    @SerializedName("token")
    public String token;
    /**有效期**/
    @SerializedName("expires_in")
    public String expires_in;

    public String getToken() {
        if(TextUtils.isEmpty(token)){
            return "0";
        }
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AccessToken{");
        sb.append("token='").append(token).append('\'');
        sb.append(", expires_in='").append(expires_in).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
