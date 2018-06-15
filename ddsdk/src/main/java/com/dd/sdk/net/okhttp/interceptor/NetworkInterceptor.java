package com.dd.sdk.net.okhttp.interceptor;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.okhttp.interceptor
 * @class describe
 * @time 2018/6/15 17:19
 * @change
 * @class describe
 */

import com.dd.sdk.tools.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Administrator
 * @name MyMvp
 * @class name：com.company.project.android.api.interceptor
 * @class describe
 * @time 2018/2/8 17:09
 * @change
 * @class describe
 */

public class NetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

      //  request.newBuilder().addHeader(Constant.TIME_TOKEN, String.valueOf(System.currentTimeMillis()));//添加服务器访问时间

        boolean isConnected =true;// NetWorkUtil.isConnected(DDSDK.getContext());
      /*  //无网络时强制使用缓存
        if (!isConnected) {
            request = request.newBuilder()
                             .cacheControl(CacheControl.FORCE_CACHE)
                             .build();
        }*/
        String method = request.method();
        if ("POST".equalsIgnoreCase(method)) {
            LogUtils.i("POST 请求方式  url=" + request.url());
        } else if ("GET".equalsIgnoreCase(method)) {
            LogUtils.i("GET 请求方式  url=" + request.url());
        }
        Response response = chain.proceed(request);
       /* String cookie = response.header("Set-Cookie");
        if (cookie != null) {
            SharedPrefercesUtils.saveCookiePreference(BaseApplication.getContext().getBaseContext(), cookie);
        }*/
        if (isConnected) {
            // 有网络时，设置超时为0
            int maxStale = 0;
            response.newBuilder()
                    .header("Cache-Control", "public, max-age=" + maxStale)
                    // .removeHeader("Pragma")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                    .build();
        } else {
            // 无网络时，设置超时为3周
            int maxStale = 60 * 60 * 24 * 21;
            response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    // .removeHeader("Pragma")
                    .build();
        }

        return response;
    }
}
