package com.learning.app.netimageload.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.core.os.EnvironmentCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;

/**
 * 本地缓存机制
 */
public class LocalCacheUtil {
    private static final String PATH = Environment.getExternalStorageDirectory() + "/imageZip/";

    /**
     * 从本地缓存获取文件
     * @param url
     * @return
     */
    public Bitmap getBitmapFromLocalCache(String url) {
        String fileName = null;
        Bitmap bitmap = null;

        try {
            fileName = Md5.getMD5(url);
            File file = new File(PATH + fileName);
            bitmap = BitmapFactory.decodeStream(new FileInputStream(file));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * @param url    url是bitmap对应的标识符
     * @param bitmap
     */
    public void saveBitmapToLocalCache(String url, Bitmap bitmap) {
        try {
            String fileName = Md5.getMD5(url);
            File file = new File(PATH + fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(file));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
