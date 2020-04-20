package com.learning.app.md_appbarlayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.learning.app.md_appbarlayout.adapter.MyAdapter;
import com.learning.app.md_appbarlayout.listener.FabScrollListener;
import com.learning.app.md_appbarlayout.listener.HideShowListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * CoordinatorLayout监听滑动控件的滑动通过Behavior反馈到其他子控件并执行一些动画
 * 其中，滑动控件指的是RecyclerView，NestedScrollView和ViewPager，其它的不行
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    ImageButton fab;

    private List<String> mData = new ArrayList<>();

    private RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        initView();
    }

    private void initData() {
        for (int i = 0; i < 20; i++) {
            mData.add("这是第" + (i + 1) + "个条目");
        }
    }

    private void initView() {
        setSupportActionBar(toolBar);
        setTitle("MaterialDesign之浮动按钮");
        toolBar.setTitleTextColor(Color.YELLOW);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(mData));
        //用属性动画
        recyclerView.addOnScrollListener(new FabScrollListener(new HideShowListener() {
            @Override
            public void onShow() {
                //减速显示
                toolBar.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(3));
                fab.animate().translationY(0)
                        .setInterpolator(new DecelerateInterpolator(3));
            }

            @Override
            public void onHide() {
                //加速隐藏
                toolBar.animate().translationY(-toolBar.getHeight())
                        .setInterpolator(new AccelerateInterpolator(3));
                fab.animate().translationY(fab.getHeight()+layoutParams.bottomMargin)
                        .setInterpolator(new AccelerateInterpolator(3));
            }
        }));
        layoutParams = (RelativeLayout.LayoutParams) fab.getLayoutParams();
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        //用Behavior实现
        startActivity(new Intent(MainActivity.this, SecondActivity.class));
    }
}
