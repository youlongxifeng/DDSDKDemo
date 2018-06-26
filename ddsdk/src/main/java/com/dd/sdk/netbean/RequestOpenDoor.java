package com.dd.sdk.netbean;

import java.io.Serializable;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/14 15:54
 * @change
 * @class describe  指令开门
 */

public class RequestOpenDoor implements Serializable {
    /**
     * 设备id
     */
    private String door_guid;
    /**
     * 开门请求类型
     *
     * @see
     */
    private      int     operateType;
    /**
     * 开门请求时的一些数据
     */
    public   String  attach;
    /**
     * 房间号
     */
    public   String  roomId;
    /**
     * 楼层
     */
    public   int     floor;
    /**
     * 唯一编号 Serial Number
     */
    public   String sn;
    /**
     * 开门类型 描述,只用于本地日志记录
     */
    public   String  reason;

    /**
     * 因为目前开门请求及开门完成后续传递都使用的是此类
     * 所以开门提示也一起携带了
     * @see  　
     */
    private      int     mHint;

    public int getOperateType() {
        return operateType;
    }

    public void setOperateType(int operateType) {
        this.operateType = operateType;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RequestOpenDoor{");
        sb.append("operateType=").append(operateType);
        sb.append(", attach='").append(attach).append('\'');
        sb.append(", roomId='").append(roomId).append('\'');
        sb.append(", floor=").append(floor);
        sb.append(", sn='").append(sn).append('\'');
        sb.append(", reason='").append(reason).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
