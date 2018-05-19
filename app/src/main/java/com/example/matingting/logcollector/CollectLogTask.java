package com.example.matingting.logcollector;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class CollectLogTask implements Runnable {

    private Context mContext;
    private String mTag;
    private String mMsg;
    private int  mLogType;

    private ArrayList<String> mStackInfoList;


    public CollectLogTask(Context mContext, String mTag, String mMsg, int mLogType) {
        this.mContext = mContext;
        this.mTag = mTag;
        this.mMsg = mMsg;
        this.mLogType = mLogType;
    }


    public CollectLogTask(Context context, int logType) {
        this(context,"","",logType);
    }

    @Override
    public void run() {
        String subDirname = "";

        String filename = "";

        if (mLogType == LogUtils.CUSTOM_LOG_COLLECT){

            filename = subDirname = FileUtils.LOG_SUB_DIR_NAME;


        }else if (mLogType == LogUtils.SYSTEM_LOG_COLLECT){

        }else if (mLogType == LogUtils.STACK_INFO_LOG_COLLECT){
          filename = subDirname = FileUtils.STACK_SUB_DIR_NAME;
        }
        saveLog2File(subDirname,filename);
    }

    private void saveLog2File(String subDirName,String filename) {
        if (FileUtils.idSDCardMounted()){

            try {
                File file = FileUtils.getLogFile(mContext,subDirName,filename);
                FileUtils.writeStr2File(file,buildLog());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String buildLog() {
        String timeStr = FileUtils.simpleDateFormat.format(new Date());

        return timeStr +FileUtils.getLineSeparator()+mTag+":"+mMsg +FileUtils.getLineSeparator();
    }

}
