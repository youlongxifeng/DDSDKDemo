package com.dd.sdk.netbean;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("id")
    private int id;
    @SerializedName("card_type")
    private int card_type;
    @SerializedName("status")
    private int status;
    @SerializedName("door_card_no")
    private String door_card_no;
    @SerializedName("device_id")
    private int device_id;
    @SerializedName("card_id")
    private int card_id;
    @SerializedName("expire")
    private String expire;
    @SerializedName("floor_no")
    private String floor_no;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCard_type() {
        return card_type;
    }

    public void setCard_type(int card_type) {
        this.card_type = card_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDoor_card_no() {
        return door_card_no;
    }

    public void setDoor_card_no(String door_card_no) {
        this.door_card_no = door_card_no;
    }

    public int getDevice_id() {
        return device_id;
    }

    public void setDevice_id(int device_id) {
        this.device_id = device_id;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getFloor_no() {
        return floor_no;
    }

    public void setFloor_no(String floor_no) {
        this.floor_no = floor_no;
    }

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
