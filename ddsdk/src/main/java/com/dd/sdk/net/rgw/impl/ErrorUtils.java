package com.dd.sdk.net.rgw.impl;

import com.google.mgson.Gson;
import com.google.mgson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Response;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.rgw.impl
 * @class describe
 * @time 2018/6/19 17:17
 * @change
 * @class describe
 */

public class ErrorUtils {
    private static final Type mapType = new TypeToken<Map<String, String>>() {}.getType();
    private static final Gson gson = new Gson();

    public static RgwAdminException parseError(Response response) {
        try {
            //noinspection unchecked
            return new RgwAdminException(
                    response.code(),
                    ((Map<String, String>) gson.fromJson(response.body().string(), mapType)).get("Code"));
        } catch (Exception e) {
            return new RgwAdminException(response.code());
        }
    }
}
