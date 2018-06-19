package com.dd.sdk.net.rgw.impl;

import android.os.Build.VERSION_CODES;
import android.support.annotation.RequiresApi;

import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

public class S3Auth implements Interceptor {

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

    public S3Auth(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @RequiresApi(api = VERSION_CODES.O)
    private static String encodeBase64(byte[] data) {
        String base64 = Base64.getEncoder().encodeToString(data);
        if (base64.endsWith("\r\n")) {
            base64 = base64.substring(0, base64.length() - 2);
        }
        if (base64.endsWith("\n")) {
            base64 = base64.substring(0, base64.length() - 1);
        }

        return base64;
    }

    @RequiresApi(api = VERSION_CODES.O)
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String httpVerb = request.method();
        String date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
        String resource = request.url().encodedPath();

        try {
            String subresource = request.url().queryParameterName(0);
            if (subResources.contains(subresource)) {
                resource += "?" + subresource;
            }
        } catch (Exception e) {
            // not match, do nothing here.
        }

        String sign = sign(httpVerb, date, resource);

        request = request.newBuilder().header("Authorization", sign).header("Date", date).build();

        return chain.proceed(request);
    }

    @RequiresApi(api = VERSION_CODES.O)
    private String sign(String httpVerb, String date, String resource) {
        return sign(httpVerb, "", "", date, resource, null);
    }

    @RequiresApi(api = VERSION_CODES.O)
    private String sign(
            String httpVerb,
            String contentMD5,
            String contentType,
            String date,
            String resource,
            Map<String, String> metas) {

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
            byte[] signBytes = mac.doFinal(stringToSign.toString().getBytes("UTF8"));
            String signature = encodeBase64(signBytes);
            return "AWS" + " " + accessKey + ":" + signature;
        } catch (Exception e) {
            throw new RuntimeException("MAC CALC FAILED.");
        }
    }
}