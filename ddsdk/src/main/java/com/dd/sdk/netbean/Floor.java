package com.dd.sdk.netbean;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.mgson.annotations.SerializedName;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/4 19:17
 * @change
 * @class describe
 * 楼层
 */

public class Floor implements Comparable<Floor> {
    @SerializedName("door_card_no")
    private String mCard;
    @SerializedName("floor_no")
    private String mFloor;
    @SerializedName("status")
    private int mStatus;
    @SerializedName("id")
    private long mId;
    @SerializedName("expire")
    private String mExpire;
    @SerializedName("card_type")
    private int cardType;

    @Override
    public int compareTo(@NonNull Floor another) {
        if(null != another && !TextUtils.isEmpty(mCard))
            return mCard.compareTo(another.mCard);
        return 0;
    }
}
