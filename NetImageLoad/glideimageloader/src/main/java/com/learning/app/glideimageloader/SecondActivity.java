package com.learning.app.glideimageloader;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {
    private static final String URL = "http://wallcoo.com/flower/Romantic_Events_flowers_1920x1200/wallpapers/1920x1200/Romantic_Events_Flowers_photo_012.jpg";
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        initView();
    }

    //Demo里的这个方法已被删除
    private void initView(){
//        Glide.with(this).using(new ProgressModelLoader()).load(URL).into(ivPic);
    }
}
