package com.example.matingting.logcollector;

import java.util.ArrayList;

public interface ILogManager {

    public boolean collectCustomLog(String tag,String msg);

    public boolean collectSystemLog();

    public boolean collectStackInfo(String stackStr);


}
