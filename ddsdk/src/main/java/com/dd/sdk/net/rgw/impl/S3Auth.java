package com.dd.sdk.net.rgw.impl;

import com.dd.sdk.DDSDK;
import com.dd.sdk.common.Base64;
import com.dd.sdk.tools.LogUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.rgw.impl
 * @class describe
 * @time 2018/6/19 17:03
 * @change
 * @class describe
 */

/**
 * @author Administrator
 * @name MyAmazonAPI
 * @class name：com.demo.myamazonapi.api.interceptor
 * @class describe
 * @time 2018/6/20 10:08
 * @change
 * @class describe
 */

public class S3Auth implements Interceptor {
    private final static String TAG = S3Auth.class.getSimpleName();
    private final String accessKey;
    private final String secretKey;

    /*
    The subResources that must be included when constructing the CanonicalizedResource Element are acl, lifecycle,
    location, logging, notification, partNumber, policy, requestPayment, torrent, uploadId, uploads, versionId,
    versioning, versions, and website.
     */
    String[] strResources = {"acl",
            "lifecycle",
            "location",
            "logging",
            "notification",
            "partNumber",
            "policy",
            "requestPayment",
            "torrent",
            "uploadId",
            "uploads",
            "versionId",
            "versioning",
            "versions",
            "website"};
    private final Set<String> subResources = new HashSet<>();
         /*   ImmutableSet.of(
                    "acl",
                    "lifecycle",
                    "location",
                    "logging",
                    "notification",
                    "partNumber",
                    "policy",
                    "requestPayment",
                    "torrent",
                    "uploadId",
                    "uploads",
                    "versionId",
                    "versioning",
                    "versions",
                    "website");*/

    {
        for (String str : strResources) {
            subResources.add(str);
        }
    }

    /*
    If the request specifies query string parameters overriding the response header values (see Get Object), append the
    query string parameters and their values. When signing, you do not encode these values; however, when making the
    request, you must encode these parameter values. The query string parameters in a GET request include
    response-content-type, response-content-language, response-expires, response-cache-control,
    response-content-disposition, and response-content-encoding.
    */
    // TODO: implement this
 /*   Set<String> queryStrings =
            ImmutableSet.of(
                    "response-content-type",
                    "response-content-language",
                    "response-expires",
                    "response-cache-control",
                    "response-content-disposition",
                    "response-content-encoding");*/

    public S3Auth() {
        this.accessKey = DDSDK.accessKey;
        this.secretKey = DDSDK.secretKey;
    }

    public S3Auth(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    private static String encodeBase64(byte[] data) {
        String base64 =Base64.getEncoder().encodeToString(data);
        if (base64.endsWith("\r\n")) {
            base64 = base64.substring(0, base64.length() - 2);
        }
        if (base64.endsWith("\n")) {
            base64 = base64.substring(0, base64.length() - 1);
        }

        return base64;
    }




    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String httpVerb = request.method();
        String date = "";
        Calendar cd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.UK);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
        date = sdf.format(cd.getTime());
        String resource = request.url().encodedPath();
        try {
            String subresource = request.url().queryParameterName(0);
            if (subResources.contains(subresource)) {
                resource += "?" + subresource;
            }
        } catch (Exception e) {
            // not match, do nothing here.
        }
        LogUtils.i(TAG, "createBucket  =====  \n  date=" + date + "  \n resource=" + resource);
        String sign = sign(httpVerb, date, resource);
        LogUtils.i(TAG, "createBucket  sign=" + sign.trim() + "\n  date=" + date.trim() + "  \n resource=" + resource.trim()+" fileBody=");
        request = request.newBuilder().header("Authorization", sign).header("Date", date).build();
        return chain.proceed(request);
    }


    public String sign(String httpVerb, String date, String resource) {
//        LogUtils.i(TAG, "createBucket  httpVerb=" + httpVerb + "\n  date=" + date + "  \n resource=" + resource);
        return sign(httpVerb, "", "", date, resource, null);
    }

    private String sign(String httpVerb, String contentMD5, String contentType, String date, String resource, Map<String, String> metas) {
        StringBuilder stringToSign =
                new StringBuilder(
                        httpVerb
                                + "\n"
                                + contentMD5.trim()
                                + "\n"
                                + contentType.trim()
                                + "\n"
                                + date
                                + "\n");
        if (metas != null) {
            for (Map.Entry<String, String> entity : metas.entrySet()) {
                stringToSign
                        .append(entity.getKey().trim())
                        .append(":")
                        .append(entity.getValue().trim())
                        .append("\n");
            }
        }
        stringToSign.append(resource);
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            byte[] keyBytes = secretKey.getBytes("UTF8");
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");
            mac.init(signingKey);
            byte[] signBytes = mac.doFinal(stringToSign.toString().trim().getBytes("UTF8"));
            String signature = encodeBase64(signBytes);
//            LogUtils.i(TAG, "createBucket  accessKey=" + accessKey + "  \n signature=" + signature);
            return "AWS" + " " + accessKey + ":" + signature;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("MAC CALC FAILED.");
        }
    }

}