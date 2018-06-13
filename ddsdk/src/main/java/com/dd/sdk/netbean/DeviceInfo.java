package com.dd.sdk.netbean;

/**
 * @author Administrator
 * @name DoorDuProjectSDK
 * @class name：com.dd.sdk.netbean
 * @class describe
 * @time 2018/6/9 13:39
 * @change
 * @class describe
 */

public class DeviceInfo {
    /**
     *  "name": "雅新东路41号",
     "ver": "5.8.395.0",
     "uid": "DDD4011612-1808",
     "type": 1,
     "vercode": 583950
     */
    private String name;
    private String ver;
    private String uid;
    private int type;
    private int vercode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVer() {
        return ver;
    }

    public void setVer(String ver) {
        this.ver = ver;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getVercode() {
        return vercode;
    }

    public void setVercode(int vercode) {
        this.vercode = vercode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DeviceInfo{");
        sb.append("name='").append(name).append('\'');
        sb.append(", ver='").append(ver).append('\'');
        sb.append(", uid='").append(uid).append('\'');
        sb.append(", type=").append(type);
        sb.append(", vercode=").append(vercode);
        sb.append('}');
        return sb.toString();
    }
}
