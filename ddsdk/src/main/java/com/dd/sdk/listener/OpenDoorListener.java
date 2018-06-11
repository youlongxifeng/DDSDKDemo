package com.dd.sdk.listener;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.listener
 * @class describe
 * @time 2018/6/11 14:38
 * @change
 * @class describe
 */


public interface OpenDoorListener {
    /**
     * 操作类型 1-IC卡开门 2-钥匙包开门 3-被呼叫接通后开门 4-密码开门
     */
    /**
     * 刷卡开门 1-IC卡开门
     */
    int TYPE_CARD = 1;
    /**
     * 手机开门 2-钥匙包开门
     */
    int TYPE_PHONE_OPEN_DOOR = 2;
    /**
     * 拨号3-被呼叫接通后开门
     */
    int TYPE_CALL_OPEN = 3;
    /**
     * 密码开门
     */
    int TYPE_SECRET_OPEN_DOOR = 4;
    /**
     * 开门结果回调
     * @param openDoorType  开门类型
     * @param isOpenSuccess 是否开门成功
     * @param msg 返回消息
     */
    void onResponse(String openDoorType,boolean isOpenSuccess,String msg);


}
