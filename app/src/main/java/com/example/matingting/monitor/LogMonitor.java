package com.example.matingting.monitor;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.util.Printer;

import com.example.matingting.logcollector.FileUtils;
import com.example.matingting.logcollector.LogManager;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LogMonitor {

    public static final  long THRESHOLD = 600;

    public static final int MAXSTACKCOUNT = 50;

    private HandlerThread mLogThread = new HandlerThread("LogMonitor");

    private Handler mIOHandler;

    private MonitorPrinter monitorPrinter;

    private   String TAG;

    protected LinkedHashMap<Long,String> mStackMap = new LinkedHashMap<>(MAXSTACKCOUNT);

    public LogMonitor(String tag) {
        mLogThread.start();
        mIOHandler = new Handler(mLogThread.getLooper());
        this.TAG = tag;
        monitorPrinter = new MonitorPrinter();

    }

    public  class MonitorPrinter implements Printer{

        private   long startMessageTime;

        private   long finishMessageTime;

        @Override
        public void println(String x) {
            if (x.startsWith(">>>>> Dispatching to ")){
                startMessageTime = System.currentTimeMillis();
                startDump();
            }

            if (x.startsWith("<<<<< Finished to ")){
                finishMessageTime = System.currentTimeMillis();
                long duration = finishMessageTime - startMessageTime;

                if (duration >THRESHOLD){
                    notifyBlockEvent();
                }
                stopDump();
                startMessageTime = 0;
            }
        }

        private void stopDump() {
            mIOHandler.removeCallbacks(mLogRunnable);
        }

        @SuppressLint("UseLog")
        private void notifyBlockEvent() {
            ArrayList<String> stackList = getThreadStacks(startMessageTime, finishMessageTime);
            for (String s : stackList) {
                Log.e(TAG, s);

            }
            LogManager.getInstance().collectSystemLog(stackList);

            //TODO:写入文件


        }

        private void startDump() {

            mIOHandler.postDelayed(mLogRunnable,
                    THRESHOLD);

        }

        public ArrayList<String> getThreadStacks(long start,long end){
            ArrayList<String> result = new ArrayList<>();

            String lastTag = "";

            synchronized (mStackMap){
                for (Long stackTime : mStackMap.keySet()){
                    if (stackTime > start && stackTime <end){

                        String stackStr = mStackMap.get(stackTime);

                        if (!stackStr.equals(lastTag) &&  ! result.contains(stackStr)){
                            result.add(stackStr);
                        }

                        lastTag = stackStr;

                    }
                }
            }

            return result;
        }
    }

    private Runnable mLogRunnable = new Runnable() {

        @Override
        public void run() {
//            if (Debug.isDebuggerConnected())return;

            StringBuilder sb = new StringBuilder();


            StackTraceElement[] stackTrace = Looper.getMainLooper().
                    getThread().getStackTrace();

            for (StackTraceElement s :stackTrace){
                sb.append(s).append(FileUtils.getLineSeparator());
            }

            synchronized (mStackMap){
                if (mStackMap.size() == MAXSTACKCOUNT){
                    LinkedHashMap hashMap = new LinkedHashMap(MAXSTACKCOUNT);
                    hashMap.putAll(mStackMap);

                    //TODO:将hashMap写入文件
                }

                mStackMap.put(System.currentTimeMillis(),sb.toString());
            }

//            Log.e(TAG, sb.toString() );

            mIOHandler.postDelayed(mLogRunnable,THRESHOLD);
            //因为主线程的message执行的时间为T,当时间到了THRESHOLD时,执行此Runnable,若不写这句代码,剩下的T-THRESHOLD时间就无法抓到堆栈
            //因为改runnable队形的message执行后就被移除消息队列了


        }
    };




}
