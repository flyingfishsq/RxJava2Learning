package com.learning.app.netimageload;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 * 1.质量压缩
 * 2.尺寸压缩
 * 3.采样率压缩
 * 4.缩放法压缩(Matrix)
 * 5.RGB_565法（比ARGB_8888少一半）
 * 6.createScaledBitmap
 * <p>
 * Bitmap所占用的内存=图片的长度*图片的宽度*一个像素点占用的字节数（3个参数决定的，任意减少一个的信息，就达到了压缩的效果）
 */
public class ImageUtil {

    public static final String TAG = "图片压缩";

    /**
     * 对图片进行基于采样率的压缩
     *
     * @param bitmap
     * @param samplingRate </>采样率必须是2的整数倍，否则会被四舍五入，传入2，生成的新图片的大小是原图片的1/2
     * @return
     */
    public static Bitmap compressBitmapBySampling(Bitmap bitmap, int samplingRate) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = samplingRate;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        Log.e(TAG, "压缩后的大小：" + bit.getByteCount());
        return bit;
    }

    /**
     * 对图片进行基于质量的压缩
     *
     * @param bitmap
     * @param quality (0,100]
     * @return
     */
    public static Bitmap compressBitmapByQuality(Bitmap bitmap, int quality) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bit;
    }

    /**
     * 对图片进行基于Matrix(缩放比例)的压缩
     *
     * @param bitmap
     * @param scaleWidth(0,1]
     * @param scaleHeight(0,1]
     * @return
     */
    public static Bitmap compressBitmapByMatrix(Bitmap bitmap, float scaleWidth, float scaleHeight) {
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bit = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return bit;
    }

    /**
     * 对图片进行基于通道格式的压缩
     *
     * @param path   本地图片路径
     * @param config ARGB_8888,RGB_565...
     * @return
     */
    public static Bitmap compressBitmapByPass(String path, Bitmap.Config config) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;
        //通过路径拿到图片
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 对图片进行指定目标长宽的压缩
     *
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap compressBitmapBySize(Bitmap bitmap, int width, int height) {
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        return scaledBitmap;
    }

    /**
     * 文件加载压缩，按照比例压缩，1表示不压缩，2表示压缩到1/2
     *
     * @param path
     * @param sampleSize
     * @return
     */
    public static Bitmap compressBitmapBySize(String path, int sampleSize) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //仅仅是读取原图片的尺寸，但没有将图片加入内存中，不会耗费内存
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = sampleSize;
        options.inJustDecodeBounds = false;//这时图片是真正加载到内存中
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        return bitmap;
    }

    /**
     * 对图片进行基于压缩格式的压缩
     *
     * @param bitmap
     * @param compressFormat jpg比png小
     * @return
     */
    public static Bitmap compressBitmapByFormat(Bitmap bitmap, Bitmap.CompressFormat compressFormat) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(compressFormat, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        Bitmap bit = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bit;
    }

}
