package com.learning.app.writerxjava.rxjava;

/**
 * 男生
 * T:看电影
 * Subscribe<? super T>:看电影的女生
 *
 * super与extends的用法，一个用于取，一个用于存，可以用现实中的生物->动物（植物）->人（猴子）来理解
 * extends用于返回类型的限定，不能用于参数类型的限定
 * super用于参数类型限定，不能用于返回类型的限定
 */
public interface OnSubscribe<T> extends Action1<Subscribe<? super T>>{
}
