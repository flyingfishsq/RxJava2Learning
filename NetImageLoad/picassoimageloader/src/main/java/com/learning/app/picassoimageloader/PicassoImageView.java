package com.learning.app.picassoimageloader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.IntRange;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ViewCompat;

/**
 * Created by office on 2017/4/10.
 * 能够显示加载进度的ImageView，这个代码很好
 */
public class PicassoImageView extends AppCompatImageView {
    private final int MAX_PROGRESS = 100;
    private Paint mArcPaint;
    private RectF mBound;
    private Paint mCirclePaint;
    private int mProgress = 0;

    public PicassoImageView(Context context) {
        this(context, null, 0);
    }

    public PicassoImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PicassoImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mArcPaint.setStrokeWidth(dpToPixel(0.1f, getContext()));
        mArcPaint.setColor(Color.argb(120, 0xff, 0xff, 0xff));
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(dpToPixel(2, getContext()));
        mCirclePaint.setColor(Color.argb(120, 0xff, 0xff, 0xff));
        mBound = new RectF();
    }

    public void setProgress(@IntRange(from = 0, to = MAX_PROGRESS) int mProgress) {
        this.mProgress = mProgress;
        ViewCompat.postInvalidateOnAnimation(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int min = Math.min(w, h);
        int max = w + h - min;
        int r = min / 5;
        //set up a square in the imageView
        //设置了在图片中间的一个小圆
        mBound.set(max / 2 - r, min / 2 - r, max / 2 + r, min / 2 + r);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画进度条, Paint是画笔，详见源码
        if (mProgress != MAX_PROGRESS && mProgress != 0) {
            float mAngle = mProgress * 360f / MAX_PROGRESS;
            //画扇型
            canvas.drawArc(mBound, 270, mAngle, true, mArcPaint);
            //画圆
            canvas.drawCircle(mBound.centerX(), mBound.centerY(), mBound.height() / 2, mCirclePaint);
        }
    }

    private static float scale;

    public static int dpToPixel(float dp, Context context) {
        if (scale == 0) {
            scale = context.getResources().getDisplayMetrics().density;
        }
        return (int) (dp * scale);
    }
}
