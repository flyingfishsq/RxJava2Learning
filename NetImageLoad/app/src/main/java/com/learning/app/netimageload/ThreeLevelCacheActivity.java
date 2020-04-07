package com.learning.app.netimageload;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.learning.app.netimageload.utils.ImageCacheUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 三级缓存策略
 * 1.网络缓存
 * 2.本地缓存
 * 3.内存缓存
 * <p>
 * 使用的优先级是321
 */
public class ThreeLevelCacheActivity extends AppCompatActivity {

    private ImageCacheUtil imageCacheUtil;

    @BindView(R.id.iv_pic)
    ImageView ivPic;

    private final String mUrl = "http://pic1.win4000.com/wallpaper/b/55597435bb036.jpg";
    @BindView(R.id.iv_pic_2)
    ImageView ivPic2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_level_cache);
        ButterKnife.bind(this);
        imageCacheUtil = new ImageCacheUtil();
        initView();
    }

    private void initView() {
        imageCacheUtil.loadImage(ivPic, mUrl);
        imageCacheUtil.loadImage(ivPic2,mUrl);
    }
}
