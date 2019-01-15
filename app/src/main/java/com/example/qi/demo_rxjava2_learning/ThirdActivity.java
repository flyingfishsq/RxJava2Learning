package com.example.qi.demo_rxjava2_learning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class ThirdActivity extends Activity {

    private Context mContext;
    private Button btn_do;
    private LinearLayout ll_content;

    private MyOnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        mContext = this;

        myOnClickListener = new MyOnClickListener();

        ll_content = (LinearLayout) findViewById(R.id.ll_content);

        btn_do = (Button) findViewById(R.id.btn_do);
        btn_do.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_do:
                    showObservableLifeCycleMethod();
                    break;
            }
        }
    }

    /**
     * 执行结果显示了RxJava的内部数据流向，最开始是建立订阅：doOnSubscribe，
     * 等到观察者处理完之后，会执行doFinally，doAfterTerminate
     * doOnNext实在onNext之前执行，它产生的Observable每发射一项数据就会调用它一次，它的Consumer接受发射的数据项
     * 一般用于在subscribe之前对数据进行处理
     */
    private void showObservableLifeCycleMethod() {
        Observable.just("Hello", " World", " haha!").doOnNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                addNewTextView("doOnNext: " + s);
            }
        }).doAfterNext(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                addNewTextView("doAfterNext: " + s);
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                addNewTextView("doOnComplete: ");
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addNewTextView("doOnSubscribe: ");
            }
        }).doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                addNewTextView("doAfterTerminate: ");
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                addNewTextView("doFinally: ");
            }
        }).doOnEach(new Consumer<Notification<String>>() {
            //Observable每发射一个数据就会触发这个回调，不仅包括onNext，还包括onComplete和onError
            @Override
            public void accept(Notification<String> stringNotification) throws Exception {
                addNewTextView("doOnEach: " + (stringNotification.isOnNext() ? "onNext" : stringNotification.isOnComplete() ? "onComplete" : "onError"));
            }
        }).doOnLifecycle(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                addNewTextView("doOnLifecycle: " + disposable.isDisposed());
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                addNewTextView("doOnLifecycle run: ");
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                addNewTextView("观察者收到消息: " + s);
            }
        });
    }

    private void addNewTextView(final String str) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = new TextView(mContext);
                tv.setText(str);
                ll_content.addView(tv);
            }
        });
    }
}
