package com.dd.sdk.net.okhttp;

import org.json.JSONObject;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
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

    /**
     * 注：@FormUrlEncoded将会自动将请求参数的类型调整为application/x-www-form-urlencoded
     * Field注解将每一个请求参数都存放至请求体中，还可以添加encoded参数，该参数为boolean型，具体的用法为
     * @param
     * @param appid
     * @param secret
     * @return
     */
    @FormUrlEncoded//
    @POST("v1/token")
    Observable<JSONObject>accessToken( @Field("appid") String appid, @Field("secret")String secret);



    /**
     * 上传文件
     */
    @PUT("/{bucket}/{object}")
    Observable<String> putBucketObject(@Url String url,@Path("bucket") String bucket/**桶名称*/, @Field("object") String object,/**文件名称*/
                                       @Part MultipartBody.Part file,//上传文件
                                       @QueryMap HashMap<String, String> map/**map信息*/);
}
