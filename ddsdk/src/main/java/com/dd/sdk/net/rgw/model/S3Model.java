package com.dd.sdk.net.rgw.model;

import com.dd.sdk.net.okhttp.ApiEngine;

import java.io.File;
import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.rgw.model
 * @class describe
 * @time 2018/6/19 19:26
 * @change
 * @class describe
 */

public class S3Model {

    public static Observable<String> createBucket( String bucketName, HashMap<String, String> parameters) {
        return ApiEngine.getInstance().getApiAmazonService().createBucket( bucketName, parameters);//bucketName 桶的名称  创建桶
    }

    public static Observable<String> getBucket( String bucket_name ) {
        return ApiEngine.getInstance().getApiAmazonService().getBucket(bucket_name);//bucketName 桶的名称  创建桶
    }


    public static Observable<String> putBucketObject(String fileType,  String bucket_name, String fileName, String fileAddress, HashMap<String, String> parameters) {
        File file=new File(fileAddress);
        RequestBody requestBody= RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData(fileType,file.getName(),requestBody);

        return ApiEngine.getInstance().getApiAmazonService().putBucketObject( bucket_name, fileName, part, parameters);//bucketName 桶的名称  创建桶
    }
}
