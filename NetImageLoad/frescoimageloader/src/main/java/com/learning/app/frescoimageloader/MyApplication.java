package com.learning.app.frescoimageloader;

import android.app.Application;
import android.os.Environment;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.drawee.backends.pipeline.DraweeConfig;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.QualityInfo;

import java.io.File;

import okhttp3.OkHttpClient;

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
//        Fresco.initialize(this);

        //复杂的配置
        //磁盘缓存的配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                //设置缓存最大为100M
                .setMaxCacheSize(100*1024*1024)
                //缓存文件夹名称
                .setBaseDirectoryName("cacheFresco")
                //设置缓存文件的路径
                .setBaseDirectoryPath(new File(Environment.getExternalStorageDirectory()+"/Fresco"))
                .build();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        ProgressiveJpegConfig progressiveJpegConfig = new ProgressiveJpegConfig(){

            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return 0;
            }

            @Override
            public QualityInfo getQualityInfo(int scanNumber) {
                return null;
            }
        };
        ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory
                .newBuilder(this,okHttpClient)
                //开启向下采样功能
                .setDownsampleEnabled(true)
                .setMainDiskCacheConfig(diskCacheConfig)
                //渐进式图片加载的配置
                .setProgressiveJpegConfig(progressiveJpegConfig)
                .build();
        DraweeConfig draweeConfig = DraweeConfig.newBuilder().build();
        Fresco.initialize(this,imagePipelineConfig,draweeConfig);
    }
}
