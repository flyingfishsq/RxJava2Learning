package com.learning.app.md_instagram.util;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;

import com.squareup.picasso.Transformation;

//https://blog.csdn.net/t1623183652/article/details/53008673绘制圆形头像的文章
public class CircleTransformation implements Transformation {
    private static final int STROKE_WIDTH = 6;

    @Override
    public Bitmap transform(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth()-size)/2;
        int y = (source.getHeight()-size)/2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if(squaredBitmap!=source){
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size,size,source.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(bitmap,Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);

        Paint paint1 = new Paint();
        paint1.setColor(Color.WHITE);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(STROKE_WIDTH);

        float r = size / 2f;
        canvas.drawCircle(r,r,r,paint);
        canvas.drawCircle(r,r,r-STROKE_WIDTH/2,paint1);

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return "CircleTransformation";
    }
}
