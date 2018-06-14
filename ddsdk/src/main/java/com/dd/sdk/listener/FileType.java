package com.dd.sdk.listener;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.listener
 * @class describe
 * @time 2018/6/14 9:57
 * @change
 * @class describe
 */

public enum FileType {
    VIDEO_TYPE(0),PICTURE_TYPE(1) ;

    private int value;

    private FileType(int value) {
        this.value = value;
    }

    public static FileType getPasswordState(FileType nowDay) {
        int stateValue = nowDay.value;

        if (++stateValue == 3) {
            stateValue = 0;
        }

        return getPasswordStateValue(stateValue);
    }

    public static FileType getPasswordStateValue(int value) {
        for (FileType c : FileType.values()) {
            if (c.value == value) {
                return c;
            }
        }
        return null;
    }
}
