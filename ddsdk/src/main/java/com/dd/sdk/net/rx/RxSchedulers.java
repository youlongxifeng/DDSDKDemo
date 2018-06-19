package com.dd.sdk.net.rx;

import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Administrator
 * @name DDSDKDemo
 * @class name：com.dd.sdk.net.rx
 * @class describe
 * @time 2018/6/19 9:37
 * @change
 * @class describe
 */

public class RxSchedulers {
    public static <T> FlowableTransformer<T, T> switchFlowableThread() {
        return new FlowableTransformer<T, T>() {
            @Override
            public Publisher<T> apply(Flowable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 统一线程处理 ompose简化线程
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> switchObservableThread() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream
                        .subscribeOn(Schedulers.io())//指定的就是发射事件的线程
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());//指定的就是订阅者接收事件的线程。
            }
        };
    }
}
