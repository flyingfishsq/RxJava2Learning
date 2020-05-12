package com.learning.app.eventdelivery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

import static com.learning.app.eventdelivery.MainActivity.TAG;

public class MyButton2 extends AppCompatButton {
    public MyButton2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //注意测试dispatchTouchEvent返回false，onTouchEvent返回true的情况
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent---" + event.getAction()+"---view MyButton2 "+this);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent---" + event.getAction()+"---view MyButton2 "+this);
        return super.onTouchEvent(event);
    }
}
