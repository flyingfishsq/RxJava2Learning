package com.demo.universalimageloader;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "UniversalImageLoader";
    private static final String URL = "http://wallcoo.com/nature/Apple_OSX_Mountain_Lion_Secret_Wallpapers/wallpapers/3200x2000/Aerial03.jpg";
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv)
    TextView tv;

    private ImageLoader imageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        imageLoader = ImageLoader.getInstance();
//        imageLoader.displayImage(URL, ivPic);
        imageLoader.displayImage(URL, ivPic, null, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                Log.e(TAG, "onLoadingStarted");
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                Log.e(TAG, failReason.toString());
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                ivPic.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                Log.e(TAG,"onLoadingCancelled");
            }
        }, new ImageLoadingProgressListener() {
            @Override
            public void onProgressUpdate(String imageUri, View view, int current, int total) {
                int i = 0;
                i++;
                tv.setText("进度" + (current * 100) / total + "%");
            }
        });

        //开启异步线程下载图片
//        new Thread(new Runnable(){
//
//            @Override
//            public void run() {
//                Bitmap bitmap = imageLoader.loadImageSync(URL);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        ivPic.setImageBitmap(bitmap);
//                    }
//                });
//            }
//        }).start();

//        //取消当前控件的加载任务
//        imageLoader.cancelDisplayTask(ivPic);
//        //暂停图片的加载任务
//        imageLoader.pause();
//        //恢复图片的加载任务
//        imageLoader.resume();
//        //停止图片的加载任务
//        imageLoader.stop();
//        //销毁imageLoader对象
//        imageLoader.destroy();
//
//        imageLoader.clearDiskCache();
//        imageLoader.clearMemoryCache();
//
//        //本地图片加载处理
//        String imagePath = Environment.getExternalStorageDirectory() + "/img.png";
//        String imageUrl = ImageDownloader.Scheme.FILE.wrap(imagePath);
//        //Content Provider图片的加载处理
//        String contentPath = "content://media/external/audio/albumart/13";
//        String contentUrl = ImageDownloader.Scheme.CONTENT.wrap(contentPath);
//        //Assets
//        String assets = ImageDownloader.Scheme.ASSETS.wrap("img.png");
//        //drawable
//        String drawable = ImageDownloader.Scheme.DRAWABLE.wrap("R.drawable.image");
    }
}
