package com.dd.sdk.listener;

import com.dd.sdk.net.RequestError;
import com.dd.sdk.netbean.BaseResponse;
import com.dd.sdk.netbean.CardInfo;
import com.dd.sdk.netbean.DoorConfig;
import com.dd.sdk.netbean.Floor;
import com.dd.sdk.netbean.OpenDoorPwd;
import com.dd.sdk.netbean.RegisterResponse;
import com.dd.sdk.netbean.RequestOpenDoor;
import com.dd.sdk.netbean.ResultBean;

import org.json.JSONObject;

import java.util.List;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.listener
 * @class describe
 * @time 2018/6/4 17:00
 * @change
 * @class describe
 * 操作指令监听
 */

public interface InstructionListener {
    /**
     * 注册成功
     * @return
     */
    ResultBean successRegister();
    /**
     * 设备未注册
     */
    ResultBean noRegister();
    ResultBean responseRegister(RegisterResponse response);
    /**
     * 注册失败
     * @return
     */
    ResultBean failRegister(RequestError response);
    /**
     *  上报应用层，当前设备未绑定，需要绑定设备
     */
    ResultBean noBinding( );
    /**
     * 获取配置信息
     */
    ResultBean getconfig(DoorConfig doorConfig);

    /**
     * 获取配置信息成功
     * @param response
     * @return
     */
    ResultBean getconfigResponse(BaseResponse<DoorConfig> response);

    /**
     * 获取配置信息失败
     * @param doorConfig
     * @return
     */
    ResultBean getconfigFail(RequestError doorConfig);

    /**
     * 上报配置内容成功
     */
    ResultBean postDeviceConfigResponse(JSONObject response);

    /**
     * 上报配置内容成功失败
     * @return
     */
    ResultBean postDeviceConfigFail(RequestError volleyError);

    /**
     * 重启指令 处理配置更新指令，上报给应用层，告知可重启
     */
    ResultBean reBoot();

    /**
     * 开门指令   处理开门指令，并上报给应用层，等待应用层返回处理结果，回包给ddconnector
     */
    ResultBean openDoor(RequestOpenDoor openDoor);
    /**
     * 拉取黑白名单指令  处理配置更新指令，上报给应用层，告知可更新本地刷卡数据
     * @param cardInfos 当前
     * @return
     */
    ResultBean getBlackAndWhiteList(List<CardInfo<Floor>> cardInfos);

    /**
     * 拉取黑白名单成功
     * @param response
     * @return
     */
    ResultBean getBlackAndWhiteSuccess(BaseResponse<CardInfo<Floor>> response);

    /**
     * 拉取黑白名单失败
     * @param requestError
     * @return
     */
    ResultBean getBlackAndWhiteListFail(RequestError requestError);

    /**
     * 网络密码指令    处理密码指令，并上报给应用层，等待应用层返回接收结果，回包给ddconnector
     */
    ResultBean getNetworkCipher(OpenDoorPwd pwd);

    /**
     * token失败重新初始化
     */
    ResultBean tokenFile();


    /**
     * 获取当前黑白名单curid当前操作步数，也就是本地数据库存储的黑白名单位数
     */
    int nameListCurid();
}
