package com.learning.app.picassoimageloader;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "PicassoImageLoader";
    private static final String URL = "http://wallcoo.com/nature/Apple_OS_X_Mountain_Lion_Wallpapers/wallpapers/3200x2000/Isles.jpg";
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.btn_advance)
    Button btnAdvance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        Picasso.with(this).load(URL).into(ivPic);
    }

    @OnClick(R.id.btn_clear)
    public void onViewClicked() {
    }

    @OnClick(R.id.btn_advance)
    public void onViewClicked2() {
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }
}
