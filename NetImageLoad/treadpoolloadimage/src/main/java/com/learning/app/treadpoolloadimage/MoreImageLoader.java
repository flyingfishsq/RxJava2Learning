package com.learning.app.treadpoolloadimage;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 图片加载线程队列管理类
 */
public class MoreImageLoader {
    private Context mContext;
    private static MoreImageLoader singleInstance = null;

    //线程池，设置最大线程容量为9
    private ExecutorService executorService;

    //用LinkedList方便对任务的添加和移除，效率更高
    private LinkedList<ImageTask> list = new LinkedList<>();

    public MoreImageLoader(Context context) {
        mContext = context;
        executorService = Executors.newFixedThreadPool(9);
    }

    public static synchronized MoreImageLoader getInstance(Context context) {
        if (singleInstance == null) {
            singleInstance = new MoreImageLoader(context);
        }
        return singleInstance;
    }

}
