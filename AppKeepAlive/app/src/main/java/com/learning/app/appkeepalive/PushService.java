package com.learning.app.appkeepalive;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Timer;
import java.util.TimerTask;

public class PushService extends Service {
    private static final String TAG = "PushService";
    int count = 0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        startWatcher();
        startPolling();
    }

    //启动Linux的socket服务端
    private void startWatcher(){
        Watcher watcher = new Watcher();
        watcher.createLinuxServerSocket(String.valueOf(Process.myUid()));
    }

    private void startPolling() {
        //定时器轮询以证明服务还活着
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.e(TAG, "服务" + count);
                count++;
            }
        }, 0, 1000 * 3);
    }
}
