package com.dd.sdk.net.volley;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.volley
 * @class describe
 * @time 2018/6/11 13:57
 * @change
 * @class describe
 */


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.mgson.Gson;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

public class GsonTypeRequest<T> extends Request<T> {
    private final static String TAG=GsonTypeRequest.class.getSimpleName();

    private Type mType;
    private Listener<T> mListener;

    public GsonTypeRequest(String url, Type type, Listener<T> listener, ErrorListener errlistener) {
        super(Method.GET, url, errlistener);
        this.mType = type;
        mListener = listener;
    }

    private Object mPost;

    /**
     * @param post        string or  object ，object 将会用Gson 装成string
     * @param url
     * @param type
     * @param listener
     * @param errlistener
     */
    public GsonTypeRequest(Object post, String url, Type type, Listener<T> listener, ErrorListener errlistener) {
        super(Method.POST, url, errlistener);
        this.mType = type;
        mListener = listener;
        mPost = post;
    }

    public static <T> GsonTypeRequest<T> newRequest(String url, Type type, Listener<T> listener, ErrorListener errlistener) {
        Log.i(TAG,"HTTP请求   url="+url+"     type="+type);
        return new GsonTypeRequest<T>(url, type, listener, errlistener);
    }

    public static <T> GsonTypeRequest<T> newRequest(Object post, String url, Type type, Listener<T> listener, ErrorListener errlistener) {
        return new GsonTypeRequest<T>(post, url, type, listener, errlistener);
    }

    private long mServerTime;

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            if (null != response.headers && response.headers.containsKey("X-Android-Received-Millis")) {
                mServerTime = Long.valueOf(response.headers.get("X-Android-Received-Millis"));
            }

            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        Cache.Entry entry = HttpHeaderParser.parseCacheHeaders(response);
        /*long live = getCacheLive();
        if (live > System.currentTimeMillis() && live != entry.ttl) {
            entry.ttl = live;
        }
        live = getRefershInerval();
        if (live > System.currentTimeMillis() && live != entry.softTtl) {
            entry.softTtl = live;
        }*/
        T r = null;
        try {
            Gson g = new Gson();
            r = g.fromJson(parsed, mType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Response.success(r, entry);
    }

    public long getTime() {
        return mServerTime;
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (null != mPost) {
            String d = null;
            if (String.class.isInstance(mPost)) {
                d = (String) mPost;
            } else {
                d = new Gson().toJson(mPost);
            }
            VolleyLog.d("Post data %s", d);
            return d.getBytes();
        }
        return super.getBody();
    }

    @Override
    protected void deliverResponse(T response) {
        // TODO Auto-generated method stub
        if (null != mListener) {
            mListener.onResponse(response );
        }
    }

}
