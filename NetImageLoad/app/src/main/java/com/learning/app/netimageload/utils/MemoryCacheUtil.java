package com.learning.app.netimageload.utils;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * 内存缓存机制，三级缓存中最重要的
 */
public class MemoryCacheUtil {
//    //使用强引用，容易被内存机制回收
//    private HashMap<String, Bitmap> memoryCache = new HashMap<>();
//
//    //使用弱引用（软引用）
//    private HashMap<String, SoftReference<Bitmap>> softMemoryCache = new HashMap<>();

    //安卓2.3之后会优先回收弱引用，所以推荐使用LRU方式
    private LruCache<String, Bitmap> lruCache;

    public MemoryCacheUtil() {
        //获取手机的最大内存的1/8作为图片缓存空间
        long maxMemory = Runtime.getRuntime().maxMemory() / 8;
        lruCache = new LruCache<String, Bitmap>((int) maxMemory) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                int byteCount = value.getByteCount();
                return byteCount;
            }
        };
    }

    public Bitmap getBitmapFromMemory(String url) {
        Bitmap bitmap = lruCache.get(url);
        return bitmap;
    }

    /**
     * @param url    url是bitmap对应的标识符
     * @param bitmap
     */
    public void saveBitmapToMemory(String url, Bitmap bitmap) {
        lruCache.put(url, bitmap);
    }

}
