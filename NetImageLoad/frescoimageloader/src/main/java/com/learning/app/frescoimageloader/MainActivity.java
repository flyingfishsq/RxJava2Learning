package com.learning.app.frescoimageloader;

import android.graphics.Bitmap;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.AbstractDraweeController;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FrescoImageLoader";
    private static final String URL = "http://wallcoo.com/ad/Linux_penguin/wallpapers/1024x768/Linux_penguin_wallpaper30.jpg";
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.btn_clear)
    Button btnClear;
    @BindView(R.id.btn_advance)
    Button btnAdvance;
    @BindView(R.id.iv_pic)
    SimpleDraweeView ivPic;

    private ImagePipeline imagePipeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView(){
        imagePipeline = Fresco.getImagePipeline();

        Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);

        LightingColorFilter lightingColorFilter = new LightingColorFilter(0x88888888, 0x00000000);
        GenericDraweeHierarchyBuilder genericDraweeHierarchyBuilder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy genericDraweeHierarchy = genericDraweeHierarchyBuilder
                .setPlaceholderImage(R.drawable.ic_launcher_background)
                .setFailureImage(R.drawable.ic_launcher_background)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER)
                //在图片上加滤镜遮罩层
                .setActualImageColorFilter(lightingColorFilter)
                //加载失败重试(自动重试4次)的图片
                .setRetryImage(R.mipmap.timg)
                .setProgressBarImage(new ProgressBarDrawable())
                //按下状态的图片
                .setPressedStateOverlay(drawable)
                .build();
        ivPic.setHierarchy(genericDraweeHierarchy);
        //设置图像显示的宽高比
        ivPic.setAspectRatio(2.09f);

        ImageDecodeOptions imageDecodeOptions = ImageDecodeOptions.newBuilder()
                .setBitmapConfig(Bitmap.Config.ARGB_8888)
                .build();
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(URL))
                .setImageDecodeOptions(imageDecodeOptions)
                .setProgressiveRenderingEnabled(true)//渐进式显示JPEG图片
                .setLocalThumbnailPreviewsEnabled(true)//本地缩略图的预先加载
//                .setResizeOptions(new ResizeOptions(200,200))
                //最低请求级别
                .setLowestPermittedRequestLevel(ImageRequest.RequestLevel.FULL_FETCH)
                //图片的自动旋转
                .setAutoRotateEnabled(true)
                .build();
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setOldController(ivPic.getController())
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {

                    }

                    @Override
                    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable animatable) {

                    }

                    @Override
                    public void onIntermediateImageSet(String id, @Nullable ImageInfo imageInfo) {

                    }

                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {

                    }

                    @Override
                    public void onFailure(String id, Throwable throwable) {

                    }

                    @Override
                    public void onRelease(String id) {

                    }
                })
                .setImageRequest(imageRequest)
                //点击实现图片重新加载
                .setTapToRetryEnabled(true)
                .setAutoPlayAnimations(true)
                .build();
        ivPic.setController(draweeController);
    }

    @OnClick({R.id.btn_clear, R.id.btn_advance})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_clear:
                //统一清除
//                imagePipeline.clearMemoryCaches();
//                imagePipeline.clearDiskCaches();
                //等价于
//                imagePipeline.clearCaches();

                //单个清除
                //通过url清除控件上的图片的方式
                imagePipeline.evictFromMemoryCache(Uri.parse(URL));
                imagePipeline.evictFromDiskCache(Uri.parse(URL));
                //等价于
//                imagePipeline.evictFromCache(Uri.parse(URL));
                break;
            case R.id.btn_advance:
                ivPic.setImageURI(URL);
                break;
        }
    }
}
