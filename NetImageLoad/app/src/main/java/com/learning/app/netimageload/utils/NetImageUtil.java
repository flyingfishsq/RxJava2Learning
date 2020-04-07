package com.learning.app.netimageload.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 网络缓存机制
 */
public class NetImageUtil {
    private MemoryCacheUtil memoryCacheUtil;
    private LocalCacheUtil localCacheUtil;

    public NetImageUtil(MemoryCacheUtil mUtil, LocalCacheUtil lUtil) {
        this.memoryCacheUtil = mUtil;
        this.localCacheUtil = lUtil;
    }

    //将下载的图片进行内存缓存和本地缓存
    public void getBitmapFromNet(String url, ImageView imageView) {
        new BitmapTask().execute(imageView, url);
        Log.e("ImageCacheUtil", "--网络加载--成功");
    }

    //网络图片下载线程
    class BitmapTask extends AsyncTask<Object, Integer, Bitmap> {
        private ImageView imageView;
        private String url;

        @Override
        protected Bitmap doInBackground(Object[] objects) {
            imageView = (ImageView) objects[0];
            url = (String) objects[1];
            return downLoadBitmap(url);
        }

        //更新下载进度
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        //图片下载好显示到imageView中
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
            //保存到本地缓存
            localCacheUtil.saveBitmapToLocalCache(url, bitmap);
            //保存到内存缓存
            memoryCacheUtil.saveBitmapToMemory(url, bitmap);
        }
    }

    private Bitmap downLoadBitmap(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            if (urlConnection.getResponseCode() == 200) {
                InputStream inputStream = urlConnection.getInputStream();
                //压缩为原图的1/2
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 2;
                options.inPreferredConfig = Bitmap.Config.RGB_565;

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
                inputStream.close();
                return bitmap;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
