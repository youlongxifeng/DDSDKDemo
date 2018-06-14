package com.dd.sdk.listener;

import android.content.Context;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.listener
 * @class describe
 * @time 2018/6/14 16:43
 * @change
 * @class describe
 */

public interface ConfigurationListener {
    /**

     * 获取配置信息
     * @param context
     * @param guid 设备唯一标识符
     * @param door_ver 5000 以下代表 door5 以下版本，5000-5999 代表 door5 版本，默认值：0
     */
    void getConfigCmd(Context context, String guid, String door_ver);

    /**
     * 获取黑白名单
     * @param context
     * @param gruid 设备唯一标识符
     * @param curid 当前操作步数,当前数据库最后一位
     */
    void getCardInfoCmd(Context context, String gruid,int curid);
}
