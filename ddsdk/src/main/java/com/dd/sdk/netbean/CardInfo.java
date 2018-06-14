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



    public boolean isAll() {
        return isAll;
    }

    public void setAll(boolean all) {
        isAll = all;
    }

    public List<T> getInfo() {
        return mInfo;
    }

    public void setInfo(List<T> info) {
        mInfo = info;
    }
    public int count()
    {
        return null == mInfo?0:mInfo.size();
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CardInfo{");
        sb.append("isAll=").append(isAll);
        sb.append(", mInfo=").append(mInfo);
        sb.append('}');
        return sb.toString();
    }
}
