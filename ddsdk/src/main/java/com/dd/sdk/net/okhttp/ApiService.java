package com.dd.sdk.net.okhttp;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.okhttp
 * @class describe
 * @time 2018/6/15 17:18
 * @change
 * @class describe
 */

public interface ApiService {

    @Multipart
    @PUT("v2/book/1220562")
    Observable<String> postVideoOrPicture(@Url String url,@Part MultipartBody.Part photo,
                                      @Part("username") RequestBody username,
                                      @Part("password") RequestBody password );
}
