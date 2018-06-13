package com.dd.sdk.common;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.dd.sdk.netbean.AccessToken;

/** Token信息
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.common
 * @class describe
 * @time 2018/6/7 18:05
 * @change
 * @class describe
 *
 */
public class TokenPrefer {

    /**
     * 读取配置
     * @param context
     * @param info
     */
    public static void loadConfig(Context context, AccessToken info) {
        SharedPreferences share = context.getSharedPreferences("token_prefer", 0);
        if(info!=null){
            info.token   = share.getString("access_token", "");
            info.expires_in   = share.getString("access_token", "");
        }

    }

    /**
     * 保存配置
     * @param context
     * @param info
     */
    public static void saveConfig(Context context, final AccessToken info) {
        if(info!=null){
        SharedPreferences share = context.getSharedPreferences("token_prefer", 0);
        Editor editor = share.edit();
        editor.putString("access_token", info.getToken());
        editor.putString("expires_in", info.getExpires_in());
        editor.commit();}
    }

}