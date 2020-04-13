package com.learning.app.glideimageloader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GlideImageLoader";
    private static final String URL = "http://wallcoo.com/flower/Romantic_Events_flowers_1920x1200/wallpapers/1920x1200/Romantic_Events_Flowers_photo_012.jpg";
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.btn_advance)
    Button btnAdvance;
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    //Glide与Activity生命周期的关联，可以暂停或恢复网络请求
    @Override
    protected void onResume() {
        super.onResume();
        Glide.with(this).resumeRequests();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Glide.with(this).pauseRequests();
    }

    private void initView() {
        //最简单的用法
        Glide.with(this).load(URL).into(ivPic);
        //加入个性化配置
//        Glide.with(this).load(URL)
//                //磁盘缓存的策略，这里表示不进行磁盘缓存
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
//                //是否进行内存缓存，true表示跳过，即不进行内存缓存
//                .skipMemoryCache(true)
//                //缓存的加载优先级
//                .priority(Priority.IMMEDIATE)
//                //禁用动画
////                .dontAnimate()
//                //加载过程中的占位图片
//                .placeholder(R.mipmap.ic_launcher)
//                //加载失败时显示的图片
//                .error(R.mipmap.ic_launcher)
//                //缩略图尺寸的限制，将原图一半大小的图片缓存为缩略图
////                .thumbnail(0.5f)
//                .transform(new MyBitmapTransformation())
//                //要显示的bitmap的尺寸
////                .override(100,100)
//                .centerCrop()
//                .into(ivPic);

//        Glide.with(this).load(URL).into(new SimpleTarget<Drawable>() {
//            @Override
//            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
//                ivPic.setImageDrawable(resource);
//            }
//        });

//        RequestBuilder<Drawable> drawableRequestBuilder = Glide.with(this).load(R.mipmap.ic_launcher);
//        Glide.with(this).load(URL).thumbnail(drawableRequestBuilder).into(ivPic);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                FutureTarget<File> fileFutureTarget = Glide.with(MainActivity.this).load(URL).downloadOnly(FutureTarget.SIZE_ORIGINAL, FutureTarget.SIZE_ORIGINAL);
//                try {
//                    File file = fileFutureTarget.get();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();

    }

    @OnClick({R.id.btn_clear, R.id.btn_advance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                Glide.get(this).clearMemory();
                //清除磁盘缓存必须放在线程中处理
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(MainActivity.this).clearDiskCache();
                    }
                }).start();
                break;
            case R.id.btn_advance:
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
                break;
        }
    }

    //未进行处理，直接返回
    class MyBitmapTransformation extends BitmapTransformation {
        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            //可对图片进行圆角等的处理
            return toTransform;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        }
    }
}
