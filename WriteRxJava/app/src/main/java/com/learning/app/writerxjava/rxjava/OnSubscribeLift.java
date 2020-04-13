package com.learning.app.writerxjava.rxjava;

/**
 * 男生的好兄弟
 * 好兄弟想把自己老婆借给男生，帮他做饭，但是老婆不愿意给他做饭，于是把会做饭的闺蜜介绍给了男生
 * T表示男生想找会做饭的女生
 * R表示会做饭的女生
 */
public class OnSubscribeLift<T, R> implements OnSubscribe<R> {
    //男生
    Observable<T> boy;

    private Func1<? super T, ? extends R> transform;

    public OnSubscribeLift(Observable<T> boy, Func1<? super T, ? extends R> transform) {
        this.boy = boy;
        this.transform = transform;
    }

    //男生找到好兄弟，要调用好兄弟的call方法
    //这里到底应该用T还是R？
    @Override
    public void call(Subscribe<? super R> subscribe) {
        Subscribe<? super T> wife = new OperateChange<>(subscribe,transform);
    }

    //好兄弟的老婆(T只想看电影的女生，R会做饭的女生)
    //老婆要有一个会做饭的闺蜜
    class OperateChange<T, R> extends Subscribe<T> {
        Subscribe<? super R> actual;
        private Func1<? super T, ? extends R> transform;

        //构造方法是老公调用的
        public OperateChange(Subscribe<? super R> actual, Func1<? super T, ? extends R> transform) {
            this.actual = actual;
            this.transform = transform;
        }

        //自己放进去，闺蜜换出来
        @Override
        public void onNext(T t) {
            R r = this.transform.apply(t);
            actual.onNext(r);
        }
    }
}
