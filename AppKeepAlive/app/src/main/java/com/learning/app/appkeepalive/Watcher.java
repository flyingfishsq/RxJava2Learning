package com.learning.app.appkeepalive;

//守护进程
public class Watcher {
    static {
        System.loadLibrary("native-lib");
    }

    //创建服务端的Socket
    public native void createLinuxServerSocket(String userId);
}
