package com.learning.app.md_floatingactionbutton.behavior;

import android.content.Context;
import android.util.AttributeSet;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * 在xml里加上Behavior属性，是通过generateLayoutParams方法反射获取的
 */
public class MyFloatingButtonBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    public MyFloatingButtonBehavior(Context context, AttributeSet attr){
        super(context,attr);
    }
}
