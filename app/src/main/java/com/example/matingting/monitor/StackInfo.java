package com.example.matingting.monitor;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.matingting.logcollector.LogApplication;

import java.util.ArrayList;

public class StackInfo {

    public static final String KEY_API = "api-level";
    public static final String KEY_UID = "uid";
    public static final String KEY_CPU_CORE_COUNT = "cpu-core";
    public static final String KEY_CPU_BUSY = "cpu-busy";
    public static final String KEY_CPU_RATE = "cpu-rate";
    public static final String KEY_TIME_COST = "time";
    public static final String KEY_TIME_START = "time-start";
    public static final String KEY_TIME_END = "time-end";
    public static final String KEY_PROCESS = "process";
    public static final String KEY_VERSION_NAME = "versionName";
    public static final String KEY_VERSION_CODE = "versionCode";
    public static final String KEY_NETWORK = "network";
    public static final String KEY_TOTAL_MEMORY = "totalMemory";
    public static final String KEY_FREE_MEMORY = "freeMemory";

    public ArrayList<String> stackInfoList;



    private int versionCode;
    private String versionName;

    private static int sCpuCoreNum = 0;
    private static String sPhoneModel;
    private static String sApiLevel;

    static {
        sCpuCoreNum = PerformanceUtils.getNumCores();
        sPhoneModel = Build.MODEL;
        sApiLevel = Build
    }



    public static StackInfo newInstance(){
        StackInfo stackInfo = new StackInfo();

        Context context = LogApplication.mApplicationContext;

        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),0);

            stackInfo.versionCode = packageInfo.versionCode;
            stackInfo.versionName = packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }




    }



}
