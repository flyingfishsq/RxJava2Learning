package com.learning.app.eventdelivery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * View的事件传递机制
 */
public class MainActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    public static final String TAG = "EventDelivery";

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    /**
     * @param savedInstanceState ListenerInfo是View的所有Listener的管理器
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        root.setOnTouchListener(this);
        root.setOnClickListener(this);

        btn1.setOnTouchListener(this);
        btn1.setOnClickListener(this);
    }

    /**
     * @param v
     * @param event
     * @return 控件的Listener事件触发的顺序是先OnTouch，再OnClick
     * 控件的OnTouch返回true，OnClick事件就没有了，阻止了事件的传递，返回false才会传递up事件
     * 源码依据：View的事件分发：
     * 1.dispatchTouchEvent()
     * 2.OnTouchListener->OnTouch()
     * 3.OnTouchEvent
     * 4.OnClickListener->OnClick()
     * <p>
     * 结论：
     * 如果OnTouchListener的OnTouch方法返回了true，那么View里面的OnTouchEvent就不会被调用
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e(TAG, "OnTouchListener---" + event.getAction() + "---view:" + v);
//        return false;
        return true;
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "OnClickListener---view:" + v);
    }

    @OnClick(R.id.fab)
    public void onViewClicked() {
        startActivity(new Intent(MainActivity.this,SecondActivity.class));
    }
}
