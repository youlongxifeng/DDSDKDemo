package com.dd.sdk.net.okhttp;

import com.dd.sdk.DDSDK;
import com.dd.sdk.thread.ThreadManager;
import com.dd.sdk.tools.LogUtils;
import com.dd.sdk.tools.SPUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
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
     *
     * @param objectName
     * @param filePath
     * @return
     */
    public static Response putBucketObject(final String objectName, final String filePath, final OkHttpCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String bucket_name = SPUtils.get("bucket_name", "bucket_name");
                Map<String, String> parameters = new HashMap<>();
                parameters.put("x-amz-acl", "public-read-write");
                OkHttpClient clientAmazon = ApiEngine.getInstance().clientAmazon();//new OkHttpClient().newBuilder().addInterceptor(new S3Auth(DDSDK.accessKey, DDSDK.secretKey)).build();//
                HttpUrl.Builder urlBuilder = HttpUrl.parse(DDSDK.endpoint).newBuilder().addPathSegment(DDSDK.bucket_name)
                                                    .addPathSegment(objectName);
                LogUtils.i("response======endpoint=" + DDSDK.endpoint + "  accessKey=" + DDSDK.accessKey + "  secretKey=" + DDSDK.secretKey + " \n bucket_name=" + DDSDK.bucket_name);
                for (Map.Entry<String, String> entity : parameters.entrySet()) {
                    urlBuilder.addQueryParameter(entity.getKey(), entity.getValue());
                }
                File file = new File(filePath);
                LogUtils.i("response=======" + file.exists() + "  FILE=" + file.getAbsolutePath());
                RequestBody fileBody = RequestBody.create(null, file);
                Request request = new Request.Builder().put(fileBody).url(urlBuilder.build())
                                                       .build();
                clientAmazon.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.i("response=======e=" + e);
                        callback.onOkFailure(e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response != null) {
                            LogUtils.i("response=======" + response.toString());
                            callback.onOkResponse(response.toString());
                        }

                    }
                });

            }
        };
        ThreadManager.getThreadPollProxy().execute(runnable);
        return null;
    }

}
