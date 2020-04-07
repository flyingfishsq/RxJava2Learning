package com.learning.app.picassoimageloader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PicassoImageLoader";
    private static final String URL = "http://wallcoo.com/nature/Apple_OS_X_Mountain_Lion_Wallpapers/wallpapers/3200x2000/Isles.jpg";
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.btn_advance)
    Button btnAdvance;

    private Picasso picasso;

    private ProgressListener progressListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        progressListener = new ProgressListener() {
            @Override
            public void update(int progress) {
                runOnUiThread(() -> {
                    tv.setText("你好啊，已经下载了" + progress + "%");
                });
            }
        };
        initView();
    }

    private void initView() {
        MyApplication.setProgressListener(progressListener);
        picasso = Picasso.with(this);
        //设置调试标识，会在图片的角标显示三种颜色，蓝色：本地缓存文件加载，红色：网络加载，绿色：内存缓存文件加载
        picasso.setIndicatorsEnabled(true);
        picasso.load(URL).into(ivPic);
    }

    @OnClick(R.id.btn_clear)
    public void onViewClicked() {
        //无效
        picasso.invalidate(URL);
    }

    @OnClick(R.id.btn_advance)
    public void onViewClicked2() {
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }
}
