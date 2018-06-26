package com.dd.sdk.listener;

import com.dd.sdk.net.RequestError;
import com.dd.sdk.netbean.BaseResponse;
import com.dd.sdk.netbean.CardInfo;
import com.dd.sdk.netbean.DoorConfig;
import com.dd.sdk.netbean.Floor;
import com.dd.sdk.netbean.ResultBean;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.listener
 * @class describe
 * @time 2018/6/26 9:07
 * @change
 * @class describe
 */

public interface InteractiveResponseListener {
    /**
     * 注册响应
     * @param response
     * @return
     */
    ResultBean registerResponse(BaseResponse response);
    /**
     * 注册失败
     * @return
     */
    ResultBean failRegister(RequestError response);
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
    ResultBean postDeviceConfigResponse(BaseResponse response);

    /**
     * 上报配置内容成功失败
     * @return
     */
    ResultBean postDeviceConfigFail(RequestError volleyError);


    /**
     * 拉取黑白名单成功
     * @param response
     * @return
     */
    ResultBean getBlackAndWhiteResponse(BaseResponse<CardInfo<Floor>> response);

    /**
     * 拉取黑白名单失败
     * @param requestError
     * @return
     */
    ResultBean getBlackAndWhiteListFail(RequestError requestError);

}
