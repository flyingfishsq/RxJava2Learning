package com.learning.app.writerxjava;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.learning.app.writerxjava.rxjava.Observable;
import com.learning.app.writerxjava.rxjava.OnSubscribe;
import com.learning.app.writerxjava.rxjava.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手写RxJava
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.tv_result)
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_1)
    public void onViewClicked() {
        Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscribe<? super String> subscribe) {
                subscribe.onNext("走，去看电影");
            }
        }).subscribe(new Subscribe<String>() {
            @Override
            public void onNext(String s) {
                runOnUiThread(()->{
                    tvResult.setText("女生收到 RxJava: "+s);
                });
            }
        });
    }
}
