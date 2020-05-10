package com.learning.app.eventdelivery.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

import static com.learning.app.eventdelivery.MainActivity.TAG;

public class MyButton extends AppCompatButton {
    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //注意测试dispatchTouchEvent返回false，onTouchEvent返回true的情况
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e(TAG, "dispatchTouchEvent---" + event.getAction());
//        return super.dispatchTouchEvent(event);
        super.dispatchTouchEvent(event);//加上这句，返回true，分发逻辑总是正常的，返回false表示不想自己处理down之后的响应事件
        return true;
//        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG, "onTouchEvent---" + event.getAction());
//        return
        super.onTouchEvent(event);//加上这句，不论返回true或者false，OnClickListener都会执行，但是执行的View不同，如果是false，则是父控件的OnClickListener被执行

//返回true表示想要自己处理down之后的响应事件，返回false表示不想自己处理down之后的响应事件
        //涉及ViewGroup与View的事件分发逻辑
//        return true;
        return false;
    }
}
