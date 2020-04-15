package com.learning.app.md_floatingactionbutton.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * 在xml里加上Behavior属性，是通过generateLayoutParams方法反射获取的
 */
public class MyFloatingButtonBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private boolean isAnimatingOut = true;

    public MyFloatingButtonBehavior(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }

    @Override
    public void onNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull FloatingActionButton child, @NonNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull int[] consumed) {
        if (dyConsumed > 0) {
            animateOut(child);
        } else if (dyConsumed < 0) {
            animateIn(child);
        }

        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);
    }

    private void animateOut(FloatingActionButton view) {
        ViewCompat.animate(view).translationY(0).start();
    }

    private void animateIn(FloatingActionButton view) {
        ViewCompat.animate(view).translationY(view.getHeight()*2).start();
    }
}
