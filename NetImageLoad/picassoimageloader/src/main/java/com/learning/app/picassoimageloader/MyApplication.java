package com.learning.app.picassoimageloader;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Picasso的全局配置
 * 自定义Picasso的全局线程池
 * 自定义Picasso默认缓存路径
 * 自定义Picasso加载图片进度的监听
 *
 * Picasso实例的两种创建方式，一种是with方法(本质上也是调用build方法)，一种是build方法
 */
public class MyApplication extends Application {
    private ThreadPoolExecutor threadPoolExecutor;
    //获取当前设备的cpu数量
    private int cpuCount = 0;
    //picasso默认并发线程是3个
    private Picasso picasso;
    private OkHttpClient okHttpClient;
    private static ProgressListener mProgressListener;

    @Override
    public void onCreate() {
        super.onCreate();
        initPicasso();
    }

    private void initPicasso() {
        //在OkHttpClient中设置图片的缓存路径和缓存文件夹的最大容量10M
        okHttpClient = new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                return response.newBuilder().body(new ProgressResponseBody(response.body(), mProgressListener)).build();
            }
        }).cache(new Cache(new File(getExternalCacheDir() + "myPicasso"), 10 * 1024 * 1024)).build();
        cpuCount = Runtime.getRuntime().availableProcessors();
        //设置线程最小数量（比cpu数量多一个），最大数量
        threadPoolExecutor = new ThreadPoolExecutor(cpuCount + 1, cpuCount * 2 + 1,
                1, TimeUnit.MINUTES, new PriorityBlockingQueue<Runnable>());
        picasso = new Picasso.Builder(this)
                .executor(threadPoolExecutor)
                //不使用自定义的Downloader，使用OkHttp的Downloader
//                .downloader(new Downloader() {
//                    @Override
//                    public Response load(Uri uri, int networkPolicy) throws IOException {
//                        return null;
//                    }
//
//                    @Override
//                    public void shutdown() {
//
//                    }
//                })
                //设置缓存路径
                .downloader(new OkHttp3Downloader(okHttpClient))
                .build();
        Picasso.setSingletonInstance(picasso);
    }

    public static void setProgressListener(ProgressListener listener) {
        mProgressListener = listener;
    }
}