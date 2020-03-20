package com.learning.app.netimageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv_pic)
    ImageView ivPic;

    private final String mUrl = "http://pic1.win4000.com/wallpaper/7/4fceadcc9e1fb.jpg";
    private byte[] pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mUrl);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int length = -1;
                        while ((length = inputStream.read(bytes)) != -1) {
                            bos.write(bytes, 0, length);
                        }
                        pic = bos.toByteArray();
                        bos.close();
                        inputStream.close();
                        Message msg = Message.obtain();
                        msg.what = 0;
                        handler.sendMessage(msg);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    Bitmap bitmap = BitmapFactory.decodeByteArray(pic,0,pic.length);
                    ivPic.setImageBitmap(bitmap);
                    break;
            }
        }
    };
}
