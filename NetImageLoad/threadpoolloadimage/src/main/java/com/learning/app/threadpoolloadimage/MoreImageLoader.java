package com.learning.app.threadpoolloadimage;

import android.content.Context;
import android.widget.ImageView;

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
    private LinkedList<ImageTask> list;

    public MoreImageLoader(Context context) {
        mContext = context;
        executorService = Executors.newFixedThreadPool(9);
        list = new LinkedList<>();
    }

    public static synchronized MoreImageLoader getInstance(Context context) {
        if (singleInstance == null) {
            singleInstance = new MoreImageLoader(context);
        }
        return singleInstance;
    }

    public void loadImage(ImageView imageView, String url) {
        //首先判断这个url是否在下载任务列表中，不在，则添加到任务列表
        if (isTaskExisted(url)) {
            return;
        }

        ImageTask imageTask = new ImageTask(imageView, url);
        //用线程锁锁定list
        synchronized (list) {
            list.add(imageTask);
        }
        //让线程池执行这个任务
        executorService.execute(imageTask);
    }

    //根据Url判断这个任务是否在任务链表中
    private boolean isTaskExisted(String url) {
        if (url == null) {
            return true;//不添加到任务链表
        }
        synchronized (list) {
            for (int i = 0; i < list.size(); i++) {
                ImageTask imageTask = list.get(i);
                if (imageTask != null && imageTask.getUrl().equals(url)) {
                    //找到就return，而不通过continue关键字
                    return true;
                }
            }
        }

        return false;
    }

    public void removeTask(ImageTask task){
        synchronized (list){
            list.remove(task);
        }
    }
}
