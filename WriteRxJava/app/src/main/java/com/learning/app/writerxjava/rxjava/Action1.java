package com.learning.app.writerxjava.rxjava;

/**
 * 行为
 */
@FunctionalInterface
public interface Action1<T> {
    //传进来的是什么样的女生，就去配合完成女生的行为
    void call(T t);
}
