package com.learning.app.localimageload;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 本地图片加载框架，raw，assets，drawable
 */
public class MainActivity extends AppCompatActivity {

    private static final String PATH = Environment.getExternalStorageDirectory() + "/img.jpg";

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_pic_name)
    TextView tvPicName;
    @BindView(R.id.btn_1)
    Button btn1;
    @BindView(R.id.btn_2)
    Button btn2;
    @BindView(R.id.btn_3)
    Button btn3;
    @BindView(R.id.btn_4)
    Button btn4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    /**
     * 本地来源的图片加载
     */
    private void displayLocalImageFile() {
        File file = new File(PATH);
        if (file.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(PATH);
            ivPic.setImageBitmap(bitmap);
            tvPicName.setText("本地来源的图片加载  img.jpg");
        }else {
            tvPicName.setText("本地来源的图片没有找到  img.jpg");
        }
    }

    /**
     * assets目录来源的图片加载
     */
    private void displayAssetsImageFile() {
        try {
            InputStream inputStream = getAssets().open("img123.jpg");
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            ivPic.setImageBitmap(bitmap);
            tvPicName.setText("assets目录来源的图片加载  img123.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * raw目录来源的图片加载
     */
    private void displayRawImageFile() {
        InputStream inputStream = getResources().openRawResource(R.raw.timg);
        //做一下压缩处理
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //下面两个属性要同时设置才有效，让bitmap在内存低的情况下可以被系统回收
        options.inPurgeable = true;
        options.inInputShareable = true;
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        ivPic.setImageBitmap(bitmap);
        tvPicName.setText("raw目录来源的图片加载  timg.jpg");
    }

    /**
     * drawable目录来源的图片加载
     * 从drawable还是mipmap取，决定于参数R.mipmap.ic_launcher
     */
    private void displayDrawableImageFile() {
        Resources resources = getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher);
        ivPic.setImageBitmap(bitmap);
        tvPicName.setText("drawable目录来源的图片加载  ic_launcher.jpg");
    }

    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4})
    public void onViewClicked(View view) {
        ivPic.setImageBitmap(null);
        switch (view.getId()) {
            case R.id.btn_1:
                displayLocalImageFile();
                break;
            case R.id.btn_2:
                displayAssetsImageFile();
                break;
            case R.id.btn_3:
                displayRawImageFile();
                break;
            case R.id.btn_4:
                displayDrawableImageFile();
                break;
        }
    }
}
