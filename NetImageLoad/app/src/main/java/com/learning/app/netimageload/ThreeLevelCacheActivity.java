package com.learning.app.netimageload;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 三级缓存策略
 * 1.网络缓存
 * 2.本地缓存
 * 3.内存缓存
 * <p>
 * 使用的优先级是321
 */
public class ThreeLevelCacheActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_level_cache);
    }
}
