package com.example.matingting.logcollector;


import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class LogManager{

    private LogManager() {

    }

    //外部类在加载时,内部类不会被加载,只有静态内部类在使用时才加载
    private static class InstanceHolder{
        private static final ILogManager INSTANCE = new LogManagerImpl(LogApplication.mApplicationContext);
    }


    public static ILogManager getInstance(){
        return  InstanceHolder.INSTANCE;
    }


    protected static class LogManagerImpl implements ILogManager {

        private Context mContext;

        private ExecutorService mExecutorService;

        private int mAvailableThreads;

        public LogManagerImpl(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public boolean collectCustomLog(String tag, String msg) {


            if (LogUtils.DEV_COLLECT && LogUtils.USER_COLLECT){
                submitTask(new CollectLogTask(mContext,tag,msg,LogUtils.CUSTOM_LOG_COLLECT));
                return true;
            }

            return false;
        }


        public void submitTask(Runnable runnable){
            checkExecutorService();
            mExecutorService.submit(runnable);
        }

        private void checkExecutorService() {

            if (mExecutorService == null || mExecutorService.isShutdown()){

                if (mAvailableThreads <= 0){
                    mAvailableThreads = 1;
                }

                mExecutorService = Executors.newFixedThreadPool(mAvailableThreads,
                        new ThreadFactory() {
                            @Override
                            public Thread newThread(@NonNull Runnable r) {
                                Thread t = new Thread(r);
                                t.setPriority(Thread.NORM_PRIORITY-1);
                                t.setName("Log_Collector_Thread");
                                return t;
                            }
                        });
            }

        }

        @Override
        public boolean collectSystemLog() {
            return false;
        }

        @Override
        public boolean collectStackInfo(String stackStr) {
            submitTask(new CollectLogTask(mContext,"",stackStr, LogUtils.STACK_INFO_LOG_COLLECT));
            return true;
        }


    }
}
