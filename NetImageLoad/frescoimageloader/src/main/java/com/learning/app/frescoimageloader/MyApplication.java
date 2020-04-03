package com.learning.app.frescoimageloader;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 对Fresco做全局配置
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
    }

    private void initConfig(){
        //最简单的配置
        Fresco.initialize(this);
    }
}
