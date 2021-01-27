package com.example.qi.demo_rxjava2_learning;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Cold Observable
 */
public class FourthActivity extends Activity {

    private Context mContext;

    private Button btn_cold;

    private LinearLayout ll_content;

    private MyOnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        mContext = this;

        myOnClickListener = new MyOnClickListener();

        ll_content = findViewById(R.id.ll_content);

        btn_cold = findViewById(R.id.btn_cold);
        btn_cold.setOnClickListener(myOnClickListener);
    }

    class MyOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cold:
                    coldObservableDo();
                    break;
            }
        }
    }

    private void coldObservableDo() {
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) {
                addNewTextView("***    subscriber1: " + aLong);
            }
        };

        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(Long aLong) {
                addNewTextView("    ###subscriber2: " + aLong);
            }
        };

        //这段代码看不懂
        Observable<Long> observable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(ObservableEmitter<Long> e) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS,
                        Schedulers.computation()).take(Integer.MAX_VALUE).subscribe(e::onNext);
            }
        }).observeOn(Schedulers.newThread());

        observable.subscribe(subscriber1);
        observable.subscribe(subscriber2);

        //这里这个线程延时是做什么用？
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
