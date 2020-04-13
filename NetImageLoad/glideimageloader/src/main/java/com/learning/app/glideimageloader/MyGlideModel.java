package com.learning.app.glideimageloader;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.GlideModule;

import java.lang.annotation.Annotation;

//修改图片在本地缓存的路径
public class MyGlideModel implements GlideModule {

    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
        String path = Environment.getExternalStorageDirectory()+"/glideDemo/";
        int size = 100*1024*1024;
        builder.setDiskCache(new DiskLruCacheFactory(path,size));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
