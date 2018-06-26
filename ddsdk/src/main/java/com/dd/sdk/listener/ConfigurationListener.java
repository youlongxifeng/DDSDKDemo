package com.dd.sdk.listener;

import android.content.Context;

import com.dd.sdk.netbean.OpenDoorPwd;
import com.dd.sdk.netbean.RequestOpenDoor;
import com.dd.sdk.netbean.ResultBean;

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


    /**
     * 网络密码指令    处理密码指令，并上报给应用层，等待应用层返回接收结果，回包给ddconnector
     */
    void getNetworkCipher(OpenDoorPwd pwd);


    /**
     * 开门指令   处理开门指令，并上报给应用层，等待应用层返回处理结果，回包给ddconnector
     */
    void openDoor(RequestOpenDoor openDoor);

    /**
     * 重启指令 处理配置更新指令，上报给应用层，告知可重启
     */
    ResultBean reBoot();

    /**
     * 获取当前黑白名单curid当前操作步数，也就是本地数据库存储的黑白名单位数
     */
    int nameListCurid();
}
