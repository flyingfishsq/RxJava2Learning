package com.learning.app.glideimageloader;

import androidx.annotation.NonNull;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProgressDataFetcher implements DataFetcher<InputStream> {
    private String url;
    private Call call;
    private boolean cancel = false;
    private InputStream inputStream;

    public ProgressDataFetcher(String url) {
        this.url = url;
    }

    //这里的api跟demo的改变了，也不知道写的对不对
    @Override
    public void loadData(@NonNull Priority priority, @NonNull DataCallback<? super InputStream> callback) {
        //依赖拦截器进行进度的监听
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor()).build();
        Request request = new Request.Builder().url(url).build();
        call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            if (cancel) {
                return;
            }
            if (response.isSuccessful()) {
                inputStream = response.body().byteStream();
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cleanup() {
        if (inputStream != null) {
            try {
                inputStream.close();
                inputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (call != null) {
            call.cancel();
        }
    }

    @Override
    public void cancel() {
        cancel = true;
    }

    @NonNull
    @Override
    public Class<InputStream> getDataClass() {
        return null;
    }

    @NonNull
    @Override
    public DataSource getDataSource() {
        return null;
    }
}
