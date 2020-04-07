package com.learning.app.threadpoolloadimage;

import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 线程池中的自定义图片下载线程
 */
public class ImageTask implements Runnable {

    private ImageView imageView;
    private String url;
    private byte[] imageBytes;

    public ImageTask(ImageView imageView, String url) {
        this.imageView = imageView;
        this.url = url;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(this.url);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10*1000);
            if(urlConnection.getResponseCode() == 200){
                InputStream inputStream = urlConnection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] bytes = new byte[1024];
                int length = -1;
                while ((length = inputStream.read(bytes))!=-1){
                    bos.write(bytes,0,length);
                }
                imageBytes = bos.toByteArray();
                handler.sendEmptyMessage(0);
                bos.close();
                inputStream.close();
            }else{
                handler.sendEmptyMessage(-1);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(-1);
        } catch (IOException e) {
            handler.sendEmptyMessage(-1);
            e.printStackTrace();
        }
    }

    public String getUrl(){
        return this.url;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    imageView.setImageBitmap(
                            BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length));
                    //很重要
                    // 这里通过imageView获取context，省去在一个自定义的类中需要传context的问题
                    // 删去当前任务对象，传的是ImageTask.this
                    MoreImageLoader.getInstance(imageView.getContext()).removeTask(ImageTask.this);
                    break;
                case -1:
                    MoreImageLoader.getInstance(imageView.getContext()).removeTask(ImageTask.this);
                    Log.e("ImageTask", "图片  "+url+"下载失败");
                    break;
            }
        }
    };
}
