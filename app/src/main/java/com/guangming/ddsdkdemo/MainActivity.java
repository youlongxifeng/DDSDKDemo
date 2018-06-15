package com.guangming.ddsdkdemo;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;

import com.dd.sdk.DDSDK;
import com.dd.sdk.listener.FileType;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.listener.OpenDoorListener;
import com.dd.sdk.netbean.CardInfo;
import com.dd.sdk.netbean.DeviceInfo;
import com.dd.sdk.netbean.DoorConfig;
import com.dd.sdk.netbean.Floor;
import com.dd.sdk.netbean.OpenDoorPwd;
import com.dd.sdk.netbean.RequestOpenDoor;
import com.dd.sdk.netbean.ResultBean;
import com.dd.sdk.netbean.UpdoorconfigBean;
import com.dd.sdk.tools.LogUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements InstructionListener {

    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = this;
        LogUtils.init(null, true, true);
        DDSDK.init(mContext, "f2a9d153188d87e18adc233ca8ee30da", "564f939a8f8a5befa67d62bdf79e6fa5", "test20160822001", "10.0.2.152", 8888, this);

        findViewById(R.id.unbindserver).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //DDApplication.unbindService();
            }
        });

    }

    /**
     * 获取黑白名单
     *
     * @param view
     */
    public void black_and_white_list(View view) {
        DDSDK.getCardInfo(mContext, "test20160822001", 0);
    }

    /**
     * 获取配置信息
     *
     * @param view
     */
    public void get_configuration_information(View view) {
        DDSDK.getConfig(mContext, "test20160822001", "5000");
    }

    /**
     * 上报配置信息
     */
    public void post_configuration_information(View view) {
        UpdoorconfigBean updoorconfigBean = new UpdoorconfigBean();
        final DisplayMetrics res = getResources().getDisplayMetrics();
        updoorconfigBean.setVideosize("1");
        updoorconfigBean.setSize_wxh(String.valueOf(res.widthPixels) + "*" + String.valueOf(res.heightPixels));
        updoorconfigBean.setMode("test20160822001");
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setName("雅新东路41号");
        deviceInfo.setType(1);
        deviceInfo.setUid("test20160822001");
        deviceInfo.setVer("5.8.395.0");
        deviceInfo.setVercode(583950);
        updoorconfigBean.setDevice(deviceInfo);
        updoorconfigBean.setFace_detect(1);
        updoorconfigBean.setMicphoneGain(0);
        updoorconfigBean.setDisable_m1_card(0);
        updoorconfigBean.setNetType(0);
        updoorconfigBean.setOpenflag(0);
        updoorconfigBean.setCallvolume(0);
        updoorconfigBean.setSpeakerGain(0);
        updoorconfigBean.setTotp(0);
        updoorconfigBean.setAdvolume(0);
        updoorconfigBean.setBluetooth_open_door(1);


        //上报配置内容
        DDSDK.postDeviceConfig("test20160822001", updoorconfigBean);
    }

    /**
     * 上报访客留影
     *
     * @param view
     */
    public void report_picture(View view) {
        FileType fileType = FileType.PICTURE_TYPE;//文件类型 FileType.VIDEO_TYPE FileType.PICTURE_TYPE，
        String fileName = "picture";//文件名称
        String fileAddress = Environment.getExternalStorageDirectory() + File.separator + "IMG.jpg";//文件地址
        String guid = "test20160822001";//设备唯一标识符;
        String device_type = "2";//设备类型 设备类型 1:门口机 2:Android 3:IOS设备 4:室内机
        int operate_type = 1;//开门类型
        String objectkey = "深圳大学城";//访客留影地址
        long time = System.currentTimeMillis();//门禁机时间
        String content = "content";//透传字段，具体依据 operate_type 而定，值为urlencode后的字符串
        String room_id = "01";//房间id
        String reason = "1";//摄像头故障状态码
        String open_time = getFileName();//13 位 Unix 时间戳，精确到毫秒级，一次开门的视频留影和图片留影应用同一个时间
        File file = new File(fileAddress);
        LogUtils.i("fileAddress =" + fileAddress + " file=" + file + "  file=" + file.exists());
        //开门操作完成后需要上报访客留影记录,上传成功返回true，失败返回false 请重传一次
       boolean isUpload = DDSDK.uploadVideoOrPicture(fileType, fileName, fileAddress, guid, device_type, operate_type, objectkey, time, content, room_id, reason, open_time);

    }

    public static String getFileName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd_HH_mm");
        String date = format.format(new Date(System.currentTimeMillis()));
        return date;// 2012年10月03日 23:41:31
    }

    /**
     * 上报视频留影
     *
     * @param view
     */
    public void report_video(View view) {
        FileType fileType = FileType.VIDEO_TYPE;//文件类型 FileType.VIDEO_TYPE FileType.PICTURE_TYPE，
        String fileName = "picture";//文件名称
        String fileAddress = Environment.getExternalStorageDirectory() + File.separator + "IMG.jpg";//文件地址
        String guid = "test20160822001";//设备唯一标识符;
        String device_type = "2";//设备类型 设备类型 1:门口机 2:Android 3:IOS设备 4:室内机
        int operate_type = 1;//开门类型
        String objectkey = "深圳大学城";//访客留影地址
        long time = System.currentTimeMillis();//门禁机时间
        String content = "content";//透传字段，具体依据 operate_type 而定，值为urlencode后的字符串
        String room_id = "01";//房间id
        String reason = "1";//摄像头故障状态码
        String open_time = getFileName();//13 位 Unix 时间戳，精确到毫秒级，一次开门的视频留影和图片留影应用同一个时间
        File file = new File(fileAddress);
        LogUtils.i("fileAddress =" + fileAddress + " file=" + file + "  file=" + file.exists());
        //开门操作完成后需要上报访客留影记录,上传成功返回true，失败返回false 请重传一次
        boolean isUpload = DDSDK.uploadVideoOrPicture(fileType, fileName, fileAddress, guid, device_type, operate_type, objectkey, time, content, room_id, reason, open_time);

    }


    @Override
    protected void onStop() {
        super.onStop();
        LogUtils.i("unbindService==onStop=");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.i("unbindService==Destroy=");
        DDSDK.release(this.getApplication());
    }


    @Override
    public ResultBean noBinding() {
        return null;
    }

    @Override
    public ResultBean getconfig(DoorConfig doorConfig) {
        UpdoorconfigBean updoorconfigBean = new UpdoorconfigBean();
        final DisplayMetrics res = getResources().getDisplayMetrics();
        updoorconfigBean.setVideosize("1");
        updoorconfigBean.setSize_wxh(String.valueOf(res.widthPixels) + "*" + String.valueOf(res.heightPixels));
        updoorconfigBean.setMode("test20160822001");
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setName("雅新东路41号");
        deviceInfo.setType(1);
        deviceInfo.setUid("test20160822001");
        deviceInfo.setVer("5.8.395.0");
        deviceInfo.setVercode(583950);
        updoorconfigBean.setDevice(deviceInfo);
        updoorconfigBean.setFace_detect(1);
        updoorconfigBean.setMicphoneGain(0);
        updoorconfigBean.setDisable_m1_card(0);
        updoorconfigBean.setNetType(0);
        updoorconfigBean.setOpenflag(0);
        updoorconfigBean.setCallvolume(0);
        updoorconfigBean.setSpeakerGain(0);
        updoorconfigBean.setTotp(0);
        updoorconfigBean.setAdvolume(0);
        updoorconfigBean.setBluetooth_open_door(1);


        //上报配置内容
        DDSDK.postDeviceConfig("test20160822001", updoorconfigBean);
        return new ResultBean();
    }

    @Override
    public ResultBean reBoot() {
        return new ResultBean();
    }

    /**
     * 开门指令，返回开门类型的不同，相关操作也会有差异，但是开门成功后，一样需要上报访客留影。
     * @param openDoor  开门数据
     * @return
     */
    @Override
    public ResultBean openDoor(RequestOpenDoor openDoor) {

        switch (openDoor.getOperateType()) {
            case OpenDoorListener.TYPE_CARD://刷卡开门

                break;
            case OpenDoorListener.TYPE_PHONE_OPEN_DOOR://手机开门 2-钥匙包开门

                break;

            case OpenDoorListener.TYPE_CALL_OPEN://被呼叫接通后开门

                break;
            case OpenDoorListener.TYPE_SECRET_OPEN_DOOR://密码开门

                break;

        }
        FileType fileType = null;//文件类型 FileType.VIDEO_TYPE FileType.PICTURE_TYPE，
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
        String open_time = "";//13 位 Unix 时间戳，精确到毫秒级，一次开门的视频留影和图片留影应用同一个时间


        //开门操作完成后需要上报访客留影记录,上传成功返回true，失败返回false 请重传一次
        boolean isUpload = DDSDK.uploadVideoOrPicture(fileType, fileName, fileAddress, guid, device_type, operate_type, objectkey, time, content, room_id, reason, open_time);
        return new ResultBean();
    }


    @Override
    public ResultBean getBlackAndWhiteList(List<CardInfo<Floor>> cardInfos) {
        ResultBean resultBean = new ResultBean();
        if (cardInfos != null) {
            resultBean.setErrCode(1);
            resultBean.setErrMsg("成功");
            LogUtils.i("getBlackAndWhiteList   cardInfos=" + cardInfos.size());
        }
        return resultBean;
    }

    @Override
    public ResultBean getNetworkCipher(OpenDoorPwd pwd) {
        return new ResultBean();
    }

    @Override
    public ResultBean tokenFile() {
        return new ResultBean();
    }

    /**
     * 获取当前黑白名单curid当前操作步数，也就是本地数据库存储的黑白名单位数
     * @return
     */
    @Override
    public int nameListCurid() {
        //这里需要操作数据库，
        return 0;
    }
}
/***
 *
 */