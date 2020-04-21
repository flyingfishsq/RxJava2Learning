package com.learning.app.md_appbarlayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learning.app.md_appbarlayout.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * layout_scrollFlags
 * scroll：里面所有的子控件想要当滑出屏幕的时候View都必须设置这个flag，没有设置的将被固定在屏幕顶部
 * 上会一起上，下会在可滑动子控件归位时再下（上一起上，下最后下）
 * enterAlways（'quick return' pattern）：RecyclerView下拉不到最底部，就不返回原位置
 * <p>
 * enterAlwaysCollapsed
 * <p>
 * snap
 */
public class ThirdActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_third);
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
        setTitle("AppbarLayout的实现");
        toolBar.setTitleTextColor(Color.YELLOW);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(mData));
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(ThirdActivity.this,ForthActivity.class));
    }
}
