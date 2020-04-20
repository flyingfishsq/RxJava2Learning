package com.learning.app.md_appbarlayout.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.learning.app.md_appbarlayout.R;

/**
 * 在xml里加上Behavior属性，是通过generateLayoutParams方法反射获取的
 */
public class MyFloatingButtonBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private boolean isAnimatingOut = false;
    private Toolbar toolbar;
    private ViewGroup.MarginLayoutParams layoutParams;

    public MyFloatingButtonBehavior(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        //当被观察的View（RecyclerView）滑动之前的回调
        //滑动关联轴，super表示其它的由父类处理
        layoutParams = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
        toolbar = coordinatorLayout.findViewById(R.id.toolBar);
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        //当被观察的View（RecyclerView）开始滑动的回调
        if (dyConsumed > 20 && !isAnimatingOut) {
            animateOut(child,toolbar);
            isAnimatingOut = true;
        } else if (dyConsumed < -20 && isAnimatingOut) {
            animateIn(child,toolbar);
            isAnimatingOut = false;
        }

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }

    private void animateOut(FloatingActionButton view,Toolbar toolbar) {
        ViewCompat.animate(view).translationY(view.getHeight()+layoutParams.bottomMargin).start();
        ViewCompat.animate(view).scaleX(0f).scaleY(0f).start();
        ViewCompat.animate(toolbar).translationY(-toolbar.getHeight()).start();
        ViewCompat.animate(toolbar).scaleX(0f).scaleY(0f).start();
    }

    private void animateIn(FloatingActionButton view,Toolbar toolbar) {
        ViewCompat.animate(view).translationY(0).start();
        ViewCompat.animate(view).scaleX(1f).scaleY(1f).start();
        ViewCompat.animate(toolbar).translationY(0).start();
        ViewCompat.animate(toolbar).scaleX(1f).scaleY(1f).start();
    }
}
