package com.learning.app.picassoimageloader;

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

    public ProgressResponseBody(ResponseBody responseBody){
        this.responseBody = responseBody;
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
        if(bufferedSource == null){
            bufferedSource = Okio.buffer(responseBody.source());
        }
        return bufferedSource;
    }

    private Source source(Source source){
        ForwardingSource forwardingSource = new ForwardingSource(source) {
            @Override
            public long read(@NotNull Buffer sink, long byteCount) throws IOException {


                return super.read(sink, byteCount);
            }

            @Override
            public void close() throws IOException {
                super.close();
            }
        };
        return forwardingSource;
    }
}
