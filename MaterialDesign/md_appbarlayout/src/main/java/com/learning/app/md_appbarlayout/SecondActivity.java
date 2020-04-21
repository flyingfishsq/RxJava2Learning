package com.learning.app.md_appbarlayout;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.learning.app.md_appbarlayout.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private boolean reverse = false;

    @OnClick(R.id.fab)
    public void onViewClicked() {
        float toDegree = reverse ? -180f : 180f;
        ObjectAnimator animator = ObjectAnimator.ofFloat(fab, "rotation", 0.0f, toDegree).setDuration(400);
        animator.start();
        reverse = !reverse;

        //Snackbar的弹出遮挡了FloatingActionButton的情况
        Snackbar.make(fab, "启动AppbarLayout", Snackbar.LENGTH_LONG)
                .setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(SecondActivity.this,ThirdActivity.class));
                    }
                }).show();
    }
}
