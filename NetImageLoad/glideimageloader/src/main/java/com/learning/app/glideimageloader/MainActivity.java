package com.learning.app.glideimageloader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "GlideImageLoader";
    private static final String URL = "http://wallcoo.com/flower/Romantic_Events_flowers_1920x1200/wallpapers/1920x1200/Romantic_Events_Flowers_photo_012.jpg";
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.btn_advance)
    Button btnAdvance;
    @BindView(R.id.iv_pic)
    ImageView ivPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        Glide.with(this).load(URL).into(ivPic);
    }

    @OnClick({R.id.btn_clear, R.id.btn_advance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                break;
            case R.id.btn_advance:
                break;
        }
    }
}
