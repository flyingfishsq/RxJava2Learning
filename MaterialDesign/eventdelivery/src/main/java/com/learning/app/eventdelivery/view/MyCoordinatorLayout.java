package com.learning.app.eventdelivery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import static com.learning.app.eventdelivery.MainActivity.TAG;

public class MyCoordinatorLayout extends CoordinatorLayout {
    public MyCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent---" + event.getAction()+"---view MyCoordinatorLayout "+this);
        return super.dispatchTouchEvent(event);
    }

    //ViewGroup独有的方法
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        Log.e(TAG, "onInterceptTouchEvent---" + event.getAction()+"---view MyCoordinatorLayout "+this);
        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent---" + event.getAction()+"---view MyCoordinatorLayout "+this);
        return super.onTouchEvent(event);
    }
}
