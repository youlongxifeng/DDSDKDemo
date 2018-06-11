package com.dd.sdk.tools;

import com.dd.sdk.BuildConfig;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.tools
 * @class describe
 * @time 2018/6/11 14:32
 * @change
 * @class describe
 */


public class Sign {
    public static void sign(StringBuilder builder) {
        sign(builder, true);

    }

    public static void sign(StringBuilder builder, boolean spilt) {
        String signData = signString(builder);
        builder.append(spilt ? "&signkey=" : "signkey=").append(signData);
    }

    public static String signString(StringBuilder builder) {
        builder.append(BuildConfig.SERVER_FETURE);
        return signString(builder.substring(0));
    }

    public static String signString(String str) {
        final String content   = str;
        final String key       = "doordu_door_$^%@$";
        final String HMAC_SHA1 = "HmacSHA1";
        return sign(key, content, HMAC_SHA1);
    }

    public static String sign(String key, String content, String type) {
        String r = null;

        try {
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes("utf-8"), type);
            Mac mac = Mac.getInstance(type);
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(content.getBytes("utf-8"));
            r = to16(rawHmac, true);
        } catch (Exception var7) {

        }

        return r;
    }

    public static String to16(byte[] source, boolean low) {
        return to16(source, 0, source.length, low);
    }

    public static String to16(byte[] source, int start, int length, boolean low) {
        char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        if(low) {
            hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        }

        byte[] tmp = source;
        int len = 0 != length?length:source.length;
        char[] str = new char[len << 1];
        int k = 0;

        for(int i = start; i < len; ++i) {
            byte byte0 = tmp[i];
            str[k++] = hexDigits[byte0 >>> 4 & 15];
            str[k++] = hexDigits[byte0 & 15];
        }

        return new String(str);
    }

}
