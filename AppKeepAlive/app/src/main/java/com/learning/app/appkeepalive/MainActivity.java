package com.learning.app.appkeepalive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

/**
 * 守护进程对App的关键服务保活，而不是对整个应用的保活
 * 不能在Java代码中动态开启进程（除非是main函数）而不是在Manifest中声明process=":f"会被手机厂商禁止
 * 通过fork函数可以开进程，但它是在Linux中的
 * 利用Linux的socket来监听App被关闭
 * 在命令行窗口执行啊adb shell->ps查看android设备的后台进程
 * 关闭/system/bin/netd进程测试系统进程在守护下自动启动（ 执行命令是kill -9 1392，其中1392是进程号）
 * 其中手机的init进程是守护进程
 * 根据Linux的socket服务的特性，现将App的进程视为客户端，守护进程视为服务端
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
