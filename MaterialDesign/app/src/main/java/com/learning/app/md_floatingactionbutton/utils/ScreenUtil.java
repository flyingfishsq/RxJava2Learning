package com.learning.app.md_floatingactionbutton.utils;

import android.content.Context;

public class ScreenUtil {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    public static int px2dip(Context context, float pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
