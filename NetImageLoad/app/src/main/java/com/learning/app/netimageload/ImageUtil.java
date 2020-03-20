package com.learning.app.netimageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class ImageUtil {

    //对图片进行基于采样率的压缩
    public static Bitmap getBitmap(Bitmap bitmap, int samplingRate) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = samplingRate;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(0);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        return bit;
    }

}
