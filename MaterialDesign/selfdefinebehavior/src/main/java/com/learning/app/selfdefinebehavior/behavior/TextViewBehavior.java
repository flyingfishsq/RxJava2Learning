package com.learning.app.selfdefinebehavior.behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

/**
 * 这里的泛型可以特指TextView，也可以泛指View，表示可以作用在任意View上
 * 一个View依赖另一个View的状态，要重写layoutDependsOn与onDependentViewChanged方法
 */
public class TextViewBehavior extends CoordinatorLayout.Behavior<View> {
    //特别要加两个参数的构造方法
    public TextViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //child表示监听者，dependency表示你要监听的View（你所依赖的，被监听的），表示注册监听关系
    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency instanceof TextView || super.layoutDependsOn(parent, child, dependency);
    }

    //当被监听的view发生改变时发生的回调
    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        //在垂直方向的位移
        int offset = dependency.getTop() - child.getTop();
        ViewCompat.offsetTopAndBottom(child, offset);
        child.animate().rotation(child.getTop() * 20);
        return true;
    }
}
