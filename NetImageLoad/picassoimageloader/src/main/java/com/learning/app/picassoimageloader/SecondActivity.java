package com.learning.app.picassoimageloader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "SecondActivity";
    private static final String URL = "http://wallcoo.com/star/Lee_Young_Ah/wallpapers/1280x1024/%5Bwallcoo_com%5D_Lee_Young_Ah_wallpaper_200655956.jpg";
    @BindView(R.id.iv_pic)
    PicassoImageView ivPic;
    @BindView(R.id.tv)
    TextView tv;

    private ProgressListener progressListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        progressListener = new ProgressListener() {
            @Override
            public void update(int progress) {
                runOnUiThread(()->{
                    tv.setText(progress + "%");
                    ivPic.setProgress(progress);
                });
            }
        };
        initView();
    }

    private void initView() {
        MyApplication.setProgressListener(progressListener);
        Picasso picasso = Picasso.with(this);
        //设置调试标识，会在图片的角标显示三种颜色，蓝色：本地缓存文件加载，红色：网络加载，绿色：内存缓存文件加载
        picasso.setIndicatorsEnabled(true);
        //加载时候日志输出
        picasso.setLoggingEnabled(true);
        //MemoryPolicy.NO_CACHE表示不从内存缓存读取
        //MemoryPolicy.NO_STORE表示不做内存缓存存储
        picasso.load(URL).config(Bitmap.Config.ALPHA_8)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                //NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE不从本地缓存文件读取，也不做本地缓存文件存储
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .into(ivPic);
    }
}
