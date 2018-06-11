package com.dd.sdk.netbean;

import com.google.mgson.annotations.SerializedName;

import java.util.List;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/4 19:25
 * @change
 * @class describe
 */

public class CardInfo<T> {
    @SerializedName("isAll")
    private boolean isAll;
    @SerializedName("record")
    private List<T> mInfo;
}
