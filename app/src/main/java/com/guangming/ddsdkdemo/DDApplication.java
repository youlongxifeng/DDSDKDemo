package com.guangming.ddsdkdemo;

import android.app.Application;

import com.dd.sdk.DDSDK;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.listener.OpenDoorListener;
import com.dd.sdk.netbean.CardInfo;
import com.dd.sdk.netbean.DoorConfig;
import com.dd.sdk.netbean.Floor;
import com.dd.sdk.netbean.RandomPwd;
import com.dd.sdk.netbean.ResultBean;
import com.dd.sdk.netbean.UpdoorconfigBean;

import java.util.List;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.guangming.ddsdkdemo
 * @class describe
 * @time 2018/6/11 14:34
 * @change
 * @class describe
 */

public class DDApplication extends Application implements InstructionListener {
    @Override
    public void onCreate() {
        super.onCreate();

        DDSDK.init(this, "f2a9d153188d87e18adc233ca8ee30da", "564f939a8f8a5befa67d62bdf79e6fa5", "DDD4001708-05946", "10.0.2.152", 8888, this);

        DDSDK.bindService(this);
    }

    /**
     * 设备未绑定,设备要绑定到后台，绑定完成后需要重启APP或是重启设备（也可以重新初始化DoorDuSDK）
     * @return
     */
    @Override
    public ResultBean noBinding() {//
        return new ResultBean();
    }

    /**
     * 网络端下发给客户端的配置信息，要保存起来，下一步需要将配置信息校验整理后重新上报
     * @param doorConfig
     * @return
     */
    @Override
    public ResultBean getconfig(DoorConfig doorConfig) {
        //上报配置内容
        DDSDK.postDeviceConfig("guid", new UpdoorconfigBean());
        return new ResultBean();
    }
    /**
     * 重启设备
     * @return
     */
    @Override
    public ResultBean reBoot() {
        return new ResultBean();
    }

    /**
     * 开门指令，返回开门类型的不同，相关操作也会有差异，但是开门成功后，一样需要上报访客留影。
     * @param openDoorType 开门类型
     * @param describe 开门描述
     * @return
     */
    @Override
    public ResultBean openDoor(int openDoorType, String describe) {

        switch (openDoorType) {
            case OpenDoorListener.TYPE_CARD://刷卡开门

                break;
            case OpenDoorListener.TYPE_PHONE_OPEN_DOOR://手机开门 2-钥匙包开门

                break;

            case OpenDoorListener.TYPE_CALL_OPEN://被呼叫接通后开门

                break;
            case OpenDoorListener.TYPE_SECRET_OPEN_DOOR://密码开门

                break;

        }
        String fileType = null;//文件类型 Constant.VIDEO_TYPE Constant.PICTURE_TYPE，
        String fileName = null;//文件名称
        String fileAddress = null;//文件地址
        String guid = null;//设备唯一标识符;
        String device_type = null;//设备类型
        int operate_type = 0;//开门类型
        String objectkey = null;//访客留影地址
        long time = 0L;//门禁机时间
        String content = null;//透传字段，具体依据 operate_type 而定，值为urlencode后的字符串
        String room_id = null;//房间id
        String reason = null;//摄像头故障状态码
        long open_time = 0L;//13 位 Unix 时间戳，精确到毫秒级，一次开门的视频留影和图片留影应用同一个时间


        //开门操作完成后需要上报访客留影记录,上传成功返回true，失败返回false 请重传一次
        boolean isUpload = DDSDK.uploadVideoOrPicture(fileType, fileName, fileAddress, guid, device_type, operate_type, objectkey, time, content, room_id, reason, open_time);
        return new ResultBean();
    }

    /**
     * 后台下发重新获取黑白名单指令
     *
     * @return
     */
    @Override
    public  ResultBean getBlackAndWhiteList(List<CardInfo<Floor>> cardInfos ) {
        //获取卡信息 信息获取后保存导数据，刷卡卡门时用到，由于以后黑白名单会有很多，这里会做分页处理，


        return new ResultBean();
    }

    /**
     * 服务器下发的临时密码,将临时密码保存导数据库
     *
     * @param pwd
     * @return
     */
    @Override
    public ResultBean getNetworkCipher(RandomPwd pwd) {//

        return new ResultBean();
    }

    @Override
    public ResultBean tokenFile() {//重新获取token信息
        DDSDK.init(this, "f2a9d153188d87e18adc233ca8ee30da", "564f939a8f8a5befa67d62bdf79e6fa5", "DDD4001708-05946", "test.swoole.doordu.com", 9501, this);
        return new ResultBean();
    }

    /**
     * 手动输入开门密码 从数据库中对比当前密码，如果存在并且密码没有有过期就开门
     */
    public void setTemppassword(int password) {
        DDSDK.pWOpenDoor(password );//上报当前密码，判断当前密码是否过期，在后台做过期对比。
        //return PasswordState.getPasswordState(PasswordState.SUCCESS);
    }


}
