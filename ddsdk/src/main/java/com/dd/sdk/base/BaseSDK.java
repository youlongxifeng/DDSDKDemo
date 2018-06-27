package com.dd.sdk.base;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dd.sdk.listener.FileType;
import com.dd.sdk.listener.InstructionListener;
import com.dd.sdk.netbean.AccessToken;
import com.dd.sdk.netbean.CardInfo;
import com.dd.sdk.netbean.Floor;
import com.dd.sdk.netbean.ResultBean;
import com.dd.sdk.netbean.UpdoorconfigBean;

import java.util.List;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.base
 * @class describe
 * @time 2018/6/26 14:52
 * @change
 * @class describe
 */

public interface BaseSDK {
    void init(@NonNull Context context, @NonNull String access_key, @NonNull String secret_key, @NonNull String guid, @NonNull String domainName, @NonNull int port, String mobile, @NonNull InstructionListener listener);

    void amazonCloudinit(@NonNull String mendpoint, @NonNull String maccessKey, @NonNull String msecretKey);

    AccessToken accessToken();

    void RegisterDevice(Context context, String mobile);

    void getConfig(Context context, String guid, String door_ver);

    ResultBean postDeviceConfig(String guid, UpdoorconfigBean config);

    List<CardInfo<Floor>> getCardInfo(Context context, String guid, int curid);

    void checkFinishData(Context context, String guid, int curid, CardInfo deviceConfig);

    boolean uploadVideoOrPicture(FileType fileType, String fileName, String fileAddress, String guid, String device_type, int operate_type, String objectkey, long time,
                                 String content, String room_id, String reason, String open_time);

    ResultBean pWOpenDoor(int password);
}
