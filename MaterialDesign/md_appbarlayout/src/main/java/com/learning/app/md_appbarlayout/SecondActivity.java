package com.learning.app.md_appbarlayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learning.app.md_appbarlayout.adapter.MyAdapter;
import com.learning.app.md_appbarlayout.listener.FabScrollListener;
import com.learning.app.md_appbarlayout.listener.HideShowListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * CoordinatorLayout继承自ViewGroup，通过协调并调度子控件或者布局来实现触摸（滑动）产生一些相关的动画效果
 */
public class SecondActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    private List<String> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
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
        setTitle("Behavior实现浮动按钮");
        toolBar.setTitleTextColor(Color.YELLOW);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(mData));
    }

}
