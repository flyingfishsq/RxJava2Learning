package com.learning.app.md_appbarlayout;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learning.app.md_appbarlayout.adapter.MyAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * NestedScrollView的使用
 */
public class ForthActivity extends AppCompatActivity {

    @BindView(R.id.nestedScrollView)
    NestedScrollView nestedScrollView;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forth);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        setSupportActionBar(toolBar);
        setTitle("AppbarLayout与NestedScrollView");
        toolBar.setTitleTextColor(Color.YELLOW);
    }
}
