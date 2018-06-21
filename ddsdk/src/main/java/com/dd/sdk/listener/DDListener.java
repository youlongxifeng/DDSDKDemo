package com.dd.sdk.listener;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.listener
 * @class describe
 * @time 2018/6/21 15:32
 * @change
 * @class describe
 */

public interface DDListener<T,E> {
       void onResponse(T t);
       void onErrorResponse(E e);
}
