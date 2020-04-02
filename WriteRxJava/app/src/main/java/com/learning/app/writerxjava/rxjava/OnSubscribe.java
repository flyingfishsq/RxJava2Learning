package com.learning.app.writerxjava.rxjava;

/**
 * 男生
 * T:看电影
 * Subscribe<? super T>:看电影的女生
 */
public interface OnSubscribe<T> extends Action1<Subscribe<? super T>>{
}
