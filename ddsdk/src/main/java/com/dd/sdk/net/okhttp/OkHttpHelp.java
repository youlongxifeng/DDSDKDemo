package com.dd.sdk.net.okhttp;

import com.dd.sdk.DDSDK;
import com.dd.sdk.tools.LogUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.okhttp
 * @class describe
 * @time 2018/6/21 10:40
 * @change
 * @class describe
 */

public class OkHttpHelp {

    /**
     * 上传视频图片文件到亚马逊云
     * @param bucketName
     * @param objectName
     * @param filePath
     * @return
     */
    public static Response putBucketObject(String bucketName, String objectName, String filePath) {
        Map<String, String> parameters=new HashMap<>();
        parameters.put("x-amz-acl", "public-read-write");
        Response response = null;
        OkHttpClient clientAmazon = ApiEngine.getInstance().clientAmazon();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(DDSDK.endpoint).newBuilder().addPathSegment(bucketName)
                                            .addPathSegment(objectName);
        for (Map.Entry<String, String> entity : parameters.entrySet()) {
            urlBuilder.addQueryParameter(entity.getKey(), entity.getValue());
        }
        FileReader fr;
        char[] buf = new char[1024 * 8];
        StringBuilder result = new StringBuilder();
        LogUtils.i("response====start==result=" + result.length());
        try {
            fr = new FileReader(filePath);
            int num = 0;
            while ((num = fr.read(buf)) != -1) {
                result = result.append(new String(buf, 0, num));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        LogUtils.i("response====end==result=" + result.length());
        File file = new File(filePath);
        RequestBody fileBody = RequestBody.create(null, file);
        Request request = new Request.Builder().put(fileBody).url(urlBuilder.build()).build();
        try {
            response = clientAmazon.newCall(request).execute();
            LogUtils.i("response=======" + response);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.i("response======e=" + e);
        }

        return response;
    }

}
