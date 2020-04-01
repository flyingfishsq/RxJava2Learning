package com.demo.universalimageloader;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;
import java.io.FileReader;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
    }

    private void initConfig() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .defaultDisplayImageOptions(getDisplayImageOptions())
                //不允许缓存一张图片的多种尺寸的缓存图片
                .denyCacheImageMultipleSizesInMemory()
                //磁盘缓存
                .diskCache(new UnlimitedDiskCache(new File(getExternalCacheDir() + "/universalimageloader")))
                //磁盘缓存图片尺寸
                .diskCacheExtraOptions(400, 400, null)
                .diskCacheFileCount(100)
                //磁盘缓存命名方式，默认是MD5
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
                //磁盘缓存大小，设置最大100M
                .diskCacheSize(100 * 1024 * 1024)
                //图片解码器，把inputStream转换成bitmap对象，是否输出日志
                .imageDecoder(new BaseImageDecoder(true))
                .imageDownloader(new BaseImageDownloader(this))
                //设置内存缓存的大小，设置最大10M
                .memoryCache(new LruMemoryCache(10 * 1024 * 1024))
                //内存缓存图片尺寸
                .memoryCacheExtraOptions(400, 400)
                .memoryCacheSize(10 * 1024 * 1024)
                //设置内存缓冲的最大占总内存的比例，设置成13%以下
                .memoryCacheSizePercentage(12)
                //自定义下载图片的线程池，此处设置成默认的线程池
                .taskExecutor(null)
                //自定义读取图片缓存的线程池
                .taskExecutorForCachedImages(null)
                //设置图片的处理顺序，设置为先进先出
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                //核心线程池的大小，默认是3
                .threadPoolSize(5)
                //线程优先级
                .threadPriority(Thread.NORM_PRIORITY)
                //输出调试信息
                .writeDebugLogs()
                .build();
        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions getDisplayImageOptions() {
        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                .bitmapConfig(Bitmap.Config.ARGB_8888)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                //拷贝一个option
//                .cloneFrom()
                //考虑图片的附加信息
                .considerExifParams(true)
//                .decodingOptions(null)
                //把图片做圆角处理
                .displayer(new RoundedBitmapDisplayer(5))
                .extraForDownloader(null)
                .handler(new Handler())
                //较好的缩放方式
                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                //缓存到内存之前的处理方式
                .preProcessor(null)
                //缓存到内存之后的处理方式
                .postProcessor(null)
                //加载前是否对imageView进行重置
                .resetViewBeforeLoading(false)
                //url为空时显示的图片
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                //显示失败时显示的图片
                .showImageOnFail(R.mipmap.ic_launcher)
                //加载中显示的图片
                .showImageOnLoading(R.mipmap.ic_launcher)
                .build();
        return displayImageOptions;
    }
}
