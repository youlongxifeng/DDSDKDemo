package com.dd.sdk.net.okhttp;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.okhttp
 * @class describe
 * @time 2018/6/19 18:56
 * @change
 * @class describe
 */

public interface ApiAmazonService {
    /**
     * @param bucket
     * @param map
     * @return
     * @Path：所有在网址中的参数（URL的问号前面），如： http://102.10.10.132/api/Accounts/{accountId}
     * @Query：URL问号后面的参数，如： http://102.10.10.132/api/Comments?access_token={access_token}
     * @QueryMap：相当于多个@Query
     * @Field：用于POST请求，提交单个数据
     * @Body：相当于多个@Field，以对象的形式提交
     */
    /**
     * 创建桶
     */
    @PUT("/{bucket}")
    Observable<String> createBucket(@Path("bucket") String bucket, @QueryMap HashMap<String, String> map);

    /**
     * 获取桶
     * @param bucket
     * @return
     */
    @PUT("{bucket}")
    Observable<String> getBucket(@Path("bucket") String bucket);


    /**
     * 上传文件
     */
    @PUT("/{bucket}/{object}")
    Observable<String> putBucketObject(@Path("bucket") String bucket/**桶名称*/, @Field("object") String object,/**文件名称*/
                                       @Part MultipartBody.Part file,//上传文件
                                       @QueryMap HashMap<String, String> map/**map信息*/);
}
