package com.learning.app.imageloadprogresscontrol;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

/**
 * 图片下载进度的监听
 */
public class MainActivity extends AppCompatActivity {

    private static final String IMAGE_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1585559496852&di=aba254fab1f896431827fa3ce0d2114d&imgtype=0&src=http%3A%2F%2Fimg.pconline.com.cn%2Fimages%2Fupload%2Fupc%2Ftx%2Fwallpaper%2F1207%2F18%2Fc1%2F12378628_1342603613476.jpg";
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.tv)
    TextView tv;
    private byte[] imgBytes;

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
                    URL url = new URL(IMAGE_URL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setReadTimeout(10000);
                    handler.sendEmptyMessage(0);
                    if (urlConnection.getResponseCode() == 200) {
                        int contentLength = urlConnection.getContentLength();
                        InputStream inputStream = urlConnection.getInputStream();
                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        byte[] bytes = new byte[1024];
                        int length = -1;
                        while ((length = inputStream.read(bytes)) != -1) {
                            //可以在这里实时获取下载的字节数
                            int size = byteArrayOutputStream.size();
                            double percent = ((double)size / (double) contentLength)*100;
                            byteArrayOutputStream.write(bytes, 0, length);
                            Message message = Message.obtain();
                            message.what = 2;
                            message.obj = percent + "%";
                            handler.sendMessage(message);
                        }
                        int size = byteArrayOutputStream.size();
                        double percent = ((double)size / (double) contentLength)*100;
                        imgBytes = byteArrayOutputStream.toByteArray();
                        byteArrayOutputStream.close();
                        inputStream.close();

                        Message message = Message.obtain();
                        message.what = 1;
                        message.obj = percent + "%";
                        handler.sendMessage(message);
                    }else{
                        handler.sendEmptyMessage(-1);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(-1);
                } catch (IOException e) {
                    e.printStackTrace();
                    handler.sendEmptyMessage(-1);
                }
            }
        }).start();

    }

    private final int STATUS_BEGIN_DOWNLOAD = 0;//开始下载
    private final int STATUS_IS_DOWNLOADING = 2;//正在下载
    private final int STATUS_COMPLETE_DOWNLOAD = 1;//下载完成
    private final int STATUS_DOWNLOAD_ERROR = -1;//下载失败

    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case STATUS_BEGIN_DOWNLOAD:
                    pb.setVisibility(View.VISIBLE);
                    break;
                case STATUS_IS_DOWNLOADING:
                    String result = (String) (msg.obj);
                    tv.setText(result);
                    break;
                case STATUS_COMPLETE_DOWNLOAD:
                    String result2 = (String) (msg.obj);
                    tv.setText(result2);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                    ivPic.setImageBitmap(bitmap);
                    pb.setVisibility(View.INVISIBLE);
                    break;
                case STATUS_DOWNLOAD_ERROR:
                    Toast.makeText(MainActivity.this, "下载失败", Toast.LENGTH_SHORT).show();
                    pb.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };
}
