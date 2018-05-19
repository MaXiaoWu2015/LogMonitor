package com.example.matingting.logcollector;

import android.content.Context;

public class LogUtils {

    //两个开关,一个由用户开启,一个由后台开启
    public static boolean USER_COLLECT = true;

    public static boolean DEV_COLLECT = true;



    public static int CUSTOM_LOG_COLLECT = 0x01;

    public static int SYSTEM_LOG_COLLECT = 0x02;

    public static int STACK_INFO_LOG_COLLECT = 0x03;


    public static int CUSTOM_SYSTEM_LOG_COLLECT = 0x04;


    public static String buildSystemInfo(Context context){
//        String
        return "TODO: write systemInfo";
    }

}
