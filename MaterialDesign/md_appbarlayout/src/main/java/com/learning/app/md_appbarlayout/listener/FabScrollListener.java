package com.learning.app.md_appbarlayout.listener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FabScrollListener extends RecyclerView.OnScrollListener {
    //小于20的改变量，不予理睬，避免滑动时候的显隐抖动
    private static final int THRESHOLD = 20;
    private int mDistance = 0;
    private HideShowListener mListener;
    private boolean visible = true;//是否可见

    public FabScrollListener(HideShowListener listener) {
        mListener = listener;
    }

    //dy表示y轴方向的改变量，dy的正负决定滑动的方向
    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (mDistance > THRESHOLD&&visible) {
            //隐藏动画
            visible = false;
            mDistance = 0;
            mListener.onHide();
        } else if(mDistance<-THRESHOLD&&!visible) {
            //显示动画
            visible = true;
            mDistance = 0;
            mListener.onShow();
        }
        if(visible&&dy>0||(!visible&&dy<0)){
            mDistance += dy;
        }
    }

}
