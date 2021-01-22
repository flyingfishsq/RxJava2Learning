package com.learning.app.writerxjava;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.learning.app.writerxjava.rxjava.Func1;
import com.learning.app.writerxjava.rxjava.Observable;
import com.learning.app.writerxjava.rxjava.OnSubscribe;
import com.learning.app.writerxjava.rxjava.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 手写RxJava的变换操作符
 */
public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_1)
    public void onViewClicked() {
        Observable.create(new OnSubscribe<String>() {
            @Override
            public void call(Subscribe<? super String> subscribe) {
                subscribe.onNext("男生： 看电影");
            }
        }).map(new Func1<String, Bitmap>() {
            @Override
            public Bitmap apply(String s) {
                tvResult.setText("兄弟老婆： 我不给你做饭");
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bear);
                return bitmap;
            }
        }).subscribe(new Subscribe<Bitmap>() {
            @Override
            public void onNext(Bitmap bitmap) {
                ivPic.setImageBitmap(bitmap);
//                tvResult.setText("老婆的闺蜜： 好，我来给你做饭");
            }
        });
    }
}
