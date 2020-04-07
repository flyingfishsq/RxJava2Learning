package com.learning.app.threadpoolloadimage;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 开启多线程加载多张图片
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.iv4)
    ImageView iv4;
    @BindView(R.id.iv5)
    ImageView iv5;

    private MoreImageLoader moreImageLoader;

    private final String[] picDatas = new String[]{
            "http://pic1.win4000.com/pic/3/79/0784877349.jpg",
            "http://www.nanrenwo.net/uploads/allimg/180921/8495-1P921155537.jpg",
            "http://news.hainan.net/Editor/img/201605/20160525/big/20160525093806414_3492482.jpg",
            "http://pic1.win4000.com/pic/5/d8/78b7776720.jpg",
            "http://img0.imgtn.bdimg.com/it/u=1620351390,348040897&fm=214&gp=0.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        moreImageLoader = MoreImageLoader.getInstance(this);
        moreImageLoader.loadImage(iv1,picDatas[0]);
        moreImageLoader.loadImage(iv2,picDatas[1]);
        moreImageLoader.loadImage(iv3,picDatas[2]);
        moreImageLoader.loadImage(iv4,picDatas[3]);
        moreImageLoader.loadImage(iv5,picDatas[4]);

    }
}
