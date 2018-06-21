package com.dd.sdk.net.okhttp;

import com.dd.sdk.DDSDK;
import com.dd.sdk.net.okhttp.interceptor.NetworkInterceptor;
import com.dd.sdk.net.rgw.impl.S3Auth;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.okhttp
 * @class describe
 * @time 2018/6/15 17:17
 * @change
 * @class describe
 */

public class ApiEngine {
    private volatile static ApiEngine apiEngine;
    private Retrofit retrofit, retrofitAmazon;
    private OkHttpClient clientAmazon;

    private ApiEngine() {
        HttpLoggingInterceptor.Logger mLog = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                //Log.i("TAG", "message==" + message);
            }
        };

        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //缓存
        int size = 1024 * 1024 * 100;
        File cacheFile = new File(DDSDK.getInstance().getContext().getCacheDir(), "OkHttpCache");
        Cache cache = new Cache(cacheFile, size);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                //.cookieJar(new CookiesManager())//在OkHttpClient创建时，传入这个CookieJar的实现，就能完成对Cookie的自动管理了
                .addNetworkInterceptor(new NetworkInterceptor())// 将有网络拦截器当做网络拦截器添加
                .addInterceptor(loggingInterceptor)
                .cache(cache)
                .build();

        clientAmazon = new OkHttpClient.Builder()
                .connectTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                .writeTimeout(12, TimeUnit.SECONDS)
                //.cookieJar(new CookiesManager())//在OkHttpClient创建时，传入这个CookieJar的实现，就能完成对Cookie的自动管理了
                .addNetworkInterceptor(new S3Auth())// 将有网络拦截器当做网络拦截器添加
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(true)
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.152:8888")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        retrofitAmazon = new Retrofit.Builder()
                .baseUrl(DDSDK.endpoint)
                .client(clientAmazon)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

    }

    public static ApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }

    public ApiAmazonService getApiAmazonService() {
        return retrofitAmazon.create(ApiAmazonService.class);
    }

    public OkHttpClient clientAmazon() {
        if (clientAmazon == null) {
            clientAmazon = new OkHttpClient.Builder()
                    .connectTimeout(12, TimeUnit.SECONDS)
                    .writeTimeout(12, TimeUnit.SECONDS)
                    .writeTimeout(12, TimeUnit.SECONDS)
                    //.cookieJar(new CookiesManager())//在OkHttpClient创建时，传入这个CookieJar的实现，就能完成对Cookie的自动管理了
                    .addNetworkInterceptor(new S3Auth())// 将有网络拦截器当做网络拦截器添加
                    .retryOnConnectionFailure(true)
                    .build();
        }
        return clientAmazon;
    }
}
