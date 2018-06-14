package com.dd.sdk.netbean;

import android.support.annotation.NonNull;
import android.text.TextUtils;

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
    /**
     * id	4160056
     card_type	5
     status	1
     door_card_no	"3966544372"
     device_id	547990
     card_id	908705
     expire	"2018-06-02 00:00:00"
     floor_no	"03"
     */
    private int id;
    private int card_type;
    private int status;
    private String door_card_no;
    private int device_id;
    private int card_id;
    private String expire;
    private String floor_no;



  /*  @SerializedName("door_card_no")
    private String mCard;
    @SerializedName("floor_no")
    private String mFloor;
    @SerializedName("status")
    private int mStatus;
    @SerializedName("id")
    private long id;
    @SerializedName("expire")
    private String mExpire;
    @SerializedName("card_type")
    private int cardType;*/

    @Override
    public int compareTo(@NonNull Floor another) {
        if(null != another && !TextUtils.isEmpty(door_card_no))
            return door_card_no.compareTo(another.door_card_no);
        return 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Floor{");
        sb.append("id=").append(id);
        sb.append(", card_type=").append(card_type);
        sb.append(", status=").append(status);
        sb.append(", door_card_no='").append(door_card_no).append('\'');
        sb.append(", device_id=").append(device_id);
        sb.append(", card_id=").append(card_id);
        sb.append(", expire='").append(expire).append('\'');
        sb.append(", floor_no='").append(floor_no).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
