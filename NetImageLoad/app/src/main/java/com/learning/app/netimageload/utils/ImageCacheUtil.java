package com.learning.app.netimageload.utils;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.learning.app.netimageload.R;

public class ImageCacheUtil {
    private static final String TAG = ImageCacheUtil.class.getSimpleName();

    private MemoryCacheUtil memoryCacheUtil;
    private LocalCacheUtil localCacheUtil;
    private NetImageUtil netImageUtil;

    public ImageCacheUtil() {
        memoryCacheUtil = new MemoryCacheUtil();
        localCacheUtil = new LocalCacheUtil();
        netImageUtil = new NetImageUtil();
    }

    //把网络图片显示到view上
    public void loadImage(ImageView imageView, String url) {
        //显示default图片
        imageView.setImageResource(R.mipmap.ic_launcher);
        Bitmap bitmap = null;
        //先从内存缓存读取
        bitmap = memoryCacheUtil.getBitmapFromMemory(url);
        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
            Log.e(TAG, "--内存加载--成功");
            return;
        }

        //内存未获取，从本地缓存读取
        bitmap = localCacheUtil.getBitmapFromLocalCache(url);
        if(bitmap!=null){
            imageView.setImageBitmap(bitmap);
            Log.e(TAG, "--本地缓存--加载成功");
            //把本地获取的缓存图片保存到内存中
            memoryCacheUtil.saveBitmapToMemory(url, bitmap);
            return;
        }

        //本地缓存也未获取，从网络读取
        netImageUtil.getBitmapFromNet(url,imageView);
    }
}
