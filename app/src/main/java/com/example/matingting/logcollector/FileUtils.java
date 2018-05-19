package com.example.matingting.logcollector;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileUtils {

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm:SS ");



    public static final String  LOG_DIR_NAME = "PaoPao_collect_log";

    public static final String LOG_SUB_DIR_NAME = "Log";


    public static final String STACK_SUB_DIR_NAME = "Stack";


    public static final String CRASH_SUB_DIR_NAME = "Crash";



    public static boolean idSDCardMounted(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }


    public static File getExternalCacheDir(Context context){
        return context.getExternalCacheDir();
    }

    public static File getAppLogDir(Context context,String subDirName){
        File file = getExternalCacheDir(context);

        File logDir = new File(file,LOG_DIR_NAME);

        File subDir = new File(logDir,subDirName);

        subDir.mkdirs();

        return subDir;
    }


    public static File getLogFile(Context context,String subDirname,String name) throws IOException {
        String fileName = dateFormat.format(new Date()) +"_"+name+"_log.txt";
        File file = new File(getAppLogDir(context,subDirname),fileName);

        if (!file.exists() || file.isDirectory()){
            FileUtils.delete(file);
            file.createNewFile();

            writeStr2File(file,LogUtils.buildSystemInfo(context));
        }

        return file;
    }

    public static void writeStr2File(File file,String str) throws IOException {
        FileOutputStream outputStream = new FileOutputStream(file,true);
        OutputStreamWriter writer = new OutputStreamWriter(outputStream,"utf-8");
        writer.write(str);
        writer.write(getLineSeparator());
        writer.flush();
        outputStream.flush();
        outputStream.close();
    }


    public static String getLineSeparator(){

        String lineSeparator = System.getProperty("line.separator");

        return TextUtils.isEmpty(lineSeparator) ? "\n":lineSeparator;
    }




    public static String getCrashName(Context context,String name){
        return dateFormat.format(new Date())+"_"+name+"_crash.txt";
    }

    public static void delete(File file) {

        if (file == null){
            return;
        }

        if (file.isFile()){
            file.delete();
        }

        File[] files = file.listFiles();
        if (files == null){
            return;
        }

        for (File f:files){
            if (f.isDirectory()){
                delete(f);
            }else {
                f.delete();
            }
        }

        file.delete();
    }
}
