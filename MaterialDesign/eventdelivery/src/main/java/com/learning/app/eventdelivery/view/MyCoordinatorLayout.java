package com.learning.app.eventdelivery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import static com.learning.app.eventdelivery.MainActivity.TAG;

/**
 * ViewGroup中
 *             if (actionMasked == MotionEvent.ACTION_DOWN) {
 *                 // Throw away all previous state when starting a new touch gesture.
 *                 // The framework may have dropped the up or cancel event for the previous gesture
 *                 // due to an app switch, ANR, or some other state change.
 *                 cancelAndClearTouchTargets(ev);
 *                 resetTouchState();
 *             }
 *
 * clearTouchTargets这个方法使mFirstTouchTarget = null;
 *
 * setDisallowIntercept
 *
 * onInterceptTouchEvent所有的ViewGroup都是默认不拦截的
 *
 * 所谓的拦截，是指按下去自身及以后的后续事件move，up等等，拦截下来给自己的onTouch用
 */
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
