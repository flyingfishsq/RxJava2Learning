package com.learning.app.writerxjava.rxjava;

/**
 * 黑屋子
 * 要有一个男生（被观察者）
 * T代表当前女生（观察者）的类型
 */
public class Observable<T> {
    private OnSubscribe<T> onSubscribe;

    public Observable(OnSubscribe<T> onSubscribe) {
        this.onSubscribe = onSubscribe;
    }

    public static <T> Observable<T> create(OnSubscribe<T> onSubscribe){
        return new Observable<T>(onSubscribe);
    }

    //圣诞老人送来女生
    public void subscribe(Subscribe<? super T> subscribe){
        onSubscribe.call(subscribe);
    }
}
