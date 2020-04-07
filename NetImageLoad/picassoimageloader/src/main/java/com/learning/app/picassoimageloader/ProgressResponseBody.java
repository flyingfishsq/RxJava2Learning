package com.learning.app.picassoimageloader;

import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 自定义的ResponseBody
 */
public class ProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private BufferedSource bufferedSource;
    private ProgressListener progressListener;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        this.responseBody = responseBody;
        this.progressListener = listener;
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @NotNull
    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    /**
     * 监听方法
     *
     * @param source
     * @return
     */
    private Source source(Source source) {
        ForwardingSource forwardingSource = new ForwardingSource(source) {
            long totalBytesRead = 0;

            @Override
            public long read(@NotNull Buffer sink, long byteCount) throws IOException {
                //每次读到的大小
                long bytesRead = super.read(sink, byteCount);
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                int progress = (int)((100*totalBytesRead)/responseBody.contentLength());
                Log.e("下载的进度",""+progress);
                if(progressListener!=null){
                    progressListener.update(progress);
                }
                return bytesRead;
            }

            @Override
            public void close() throws IOException {
                super.close();
            }
        };
        return forwardingSource;
    }
}
