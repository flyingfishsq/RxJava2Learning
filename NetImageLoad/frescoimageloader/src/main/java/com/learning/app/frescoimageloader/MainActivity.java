package com.learning.app.frescoimageloader;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FrescoImageLoader";
    private static final String URL = "http://wallcoo.com/ad/Linux_penguin/wallpapers/1024x768/Linux_penguin_wallpaper30.jpg";
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
