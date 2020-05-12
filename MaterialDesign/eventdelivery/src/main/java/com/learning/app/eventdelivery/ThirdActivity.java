package com.learning.app.eventdelivery;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.learning.app.eventdelivery.view.MyCoordinatorLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.learning.app.eventdelivery.MainActivity.TAG;

/**
 * ViewGroup的事件传递机制
 * <p>
 * dispatchTouchEvent()
 * onTouchEvent()
 * onInterceptTouchEvent()，触摸事件拦截
 */
public class ThirdActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {

    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.root)
    MyCoordinatorLayout root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
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
    }

    @Override
    public void onClick(View v) {
        Log.e(TAG, "OnClickListener---view:" + v);
    }
}
