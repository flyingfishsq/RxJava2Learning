package com.learning.app.md_appbarlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * CoordinatorLayout继承自ViewGroup，通过协调并调度子控件或者布局来实现触摸（滑动）产生一些相关的动画效果
 */
public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
}
