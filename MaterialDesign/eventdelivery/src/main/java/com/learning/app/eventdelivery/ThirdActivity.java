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
 * <p>
 * <p>
 * ViewGroup的重点代码
 * final boolean disallowIntercept = (mGroupFlags & FLAG_DISALLOW_INTERCEPT) != 0;
 * if (!disallowIntercept) {
 * intercepted = onInterceptTouchEvent(ev);
 * ev.setAction(action); // restore action in case it was changed
 * } else {
 * intercepted = false;
 * }
 * <p>
 * <p>
 * <p>
 * if (child == null) {
 * handled = super.dispatchTouchEvent(event);
 * } else {
 * handled = child.dispatchTouchEvent(event);
 * }
 *
 * 作业：1.在ScrollView里面嵌套一个ListView，解决滑动冲突
 * 2.ListView全部展开
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
