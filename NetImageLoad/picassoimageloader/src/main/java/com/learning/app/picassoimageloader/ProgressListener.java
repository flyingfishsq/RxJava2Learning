package com.learning.app.picassoimageloader;

import androidx.annotation.IntRange;

//监听下载进度的接口
public interface ProgressListener {
    //注意这个注解，不知道什么意思
    void update(@IntRange(from = 0,to = 100) int progress);
}
