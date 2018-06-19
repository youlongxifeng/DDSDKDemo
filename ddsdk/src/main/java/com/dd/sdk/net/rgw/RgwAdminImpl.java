package com.dd.sdk.net.rgw;

import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;

import com.dd.sdk.net.rgw.impl.ErrorUtils;
import com.dd.sdk.net.rgw.impl.RgwAdminException;
import com.dd.sdk.net.rgw.impl.S3Auth;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.rgw
 * @class describe
 * @time 2018/6/19 17:01
 * @change
 * @class describe
 */

public class RgwAdminImpl implements RgwAdmin {
    private final OkHttpClient client;
    private final String endpoint;
    private static final RequestBody emptyBody = RequestBody.create(null, new byte[] {});

    public RgwAdminImpl(String accessKey, String secretKey, String endpoint) {
        validEndpoint(endpoint);
        this.client = new OkHttpClient().newBuilder().addInterceptor(new S3Auth(accessKey, secretKey)).build();
        this.endpoint = endpoint;

    }

    private static void validEndpoint(String endpoint) {
        if (HttpUrl.parse(endpoint) == null) {
            throw new IllegalArgumentException("endpoint is invalid");
        }
    }
    @Override
    public Optional<String> getObjectPolicy(String bucketName, String objectKey) {
        return null;
    }

    @Override
    public Optional<String> getBucketPolicy(String bucketName) {
        return null;
    }

    @RequiresApi(api = VERSION_CODES.KITKAT)
    @Override
    public void createBucket(String bucketName, Map<String, String> parameters) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(endpoint).newBuilder().addPathSegment(bucketName);
        appendParameters(parameters, urlBuilder);
        Request request = new Request.Builder().put(emptyBody).url(urlBuilder.build()).build();
        safeCall(request);
    }

    @RequiresApi(api = VERSION_CODES.KITKAT)
    @Override
    public void getBucket() {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(endpoint).newBuilder().addPathSegment("/");
        Request request = new Request.Builder().get().url(urlBuilder.build()).build();
        safeCall(request);
    }

    @Override
    public void deleteBucket(String bucketName) {

    }

    @RequiresApi(api = VERSION_CODES.KITKAT)
    @Override
    public void putBucketObject(String bucketName, String objectName, String filePath, Map<String, String> parameters) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(endpoint).newBuilder().addPathSegment(bucketName)
                                            .addPathSegment(objectName);

        appendParameters(parameters, urlBuilder);
        FileReader fr;
        char[] buf = new char[1024];
        String result = new String();
        try {
            fr = new FileReader(filePath);
            int num = 0;
            while ((num = fr.read(buf)) != -1) {
                result = result.concat(new String(buf, 0, num));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Request request = new Request.Builder().put(RequestBody.create(null, result.getBytes())).url(urlBuilder.build())
                                               .build();
        safeCall(request);
    }


    private static void appendParameters(Map<String, String> parameters, HttpUrl.Builder urlBuilder) {
        if (parameters != null) {
            for (Map.Entry<String, String> entity : parameters.entrySet() ) {
                parameters.put(entity.getKey(),entity.getValue());

            }

        }
    }

    /**
     * Guarantee that the request is execute success and the connection is
     * closed
     *
     * @param request
     *            request
     * @return resp body in str; null if no body or status code == 404
     * @throws RgwAdminException
     *             if resp code != (200||404)
     */
    @RequiresApi(api = VERSION_CODES.KITKAT)
    private String safeCall(Request request) {
        try (Response response = client.newCall(request).execute()) {
            if (response.code() == 404) {
                return null;
            }
            if (!response.isSuccessful()) {
                throw ErrorUtils.parseError(response);
            }
            ResponseBody body = response.body();
            if (body != null) {
                String bodyStr = response.body().string();
                System.out.println(bodyStr);
                return bodyStr;
            } else {
                return null;
            }
        } catch (IOException e) {
            throw new RgwAdminException(500, "IOException", e);
        }
    }
}
