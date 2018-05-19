package com.example.matingting.monitor;

import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public class PerformanceUtils {

    private static final String TAG = "PerformanceUtils";

    //Exception和Error的区别,  NoClassDefException 和 ClassNotFoundException的区别, 可检查异常和不可检查异常的区别
    private PerformanceUtils() throws InstantiationException {
        throw new InstantiationException("Must not instantiate this class");
    }


    public static int getNumCores(){
        class CpuFilter implements FileFilter{

            @Override
            public boolean accept(File pathname) {
                return Pattern.matches("cpu[0-9]",pathname.getName());
            }
        }

        int coreNums = 0;


        try {
            File dir = new File("/sys/devices/system/cpu/");

            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        }catch (Exception e){
            Log.e(TAG,"getNumCores Exception:",e);

            coreNums = 1;
        }

        return coreNums;
    }

}
