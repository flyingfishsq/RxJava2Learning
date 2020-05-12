package com.learning.app.eventdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learning.app.eventdelivery.view.MyButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.learning.app.eventdelivery.MainActivity.TAG;

/**
 * View的事件传递机制
 */
public class SecondActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    @BindView(R.id.btn1)
    MyButton btn1;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);

        root.setOnTouchListener(this);
        root.setOnClickListener(this);

        btn1.setOnTouchListener(this);
        btn1.setOnClickListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e(TAG, "OnTouchListener---" + event.getAction() + "---view:" + v);
        return false;
        //返回true就接收不到OnTouchEvent和OnClickListener事件
//        return true;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "OnClickListener---view:" + v);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(SecondActivity.this,ThirdActivity.class));
    }
}
