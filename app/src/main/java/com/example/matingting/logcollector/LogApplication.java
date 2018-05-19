package com.example.matingting.logcollector;

import android.app.Application;
import android.content.Context;

import com.example.matingting.monitor.LogMonitor;

public class LogApplication extends Application {

    public static Context mApplicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationContext = getApplicationContext();



         LogMonitor monitor = new LogMonitor("LogCollector");
        getMainLooper().setMessageLogging(monitor.new MonitorPrinter());
    }





}
