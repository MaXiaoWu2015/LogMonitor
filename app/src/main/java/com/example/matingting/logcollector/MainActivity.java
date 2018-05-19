package com.example.matingting.logcollector;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Printer;
import android.view.View;
import com.example.matingting.view.CustomConstraintLayout;

public class MainActivity extends AppCompatActivity implements CustomConstraintLayout.onPerformDrawListener {
    private static final String TAG = "MainActivity";
    private  long start;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogManager.getInstance().collectCustomLog(TAG,"onCreate");

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(800);
                    Thread.sleep(700);
                    Thread.sleep(20);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

//        try {
//            Thread.sleep(900);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        LogManager.getInstance().collectCustomLog(TAG,"onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        try {
//            Thread.sleep(700);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        LogManager.getInstance().collectCustomLog(TAG,"onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogManager.getInstance().collectCustomLog(TAG,"onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogManager.getInstance().collectCustomLog(TAG,"onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogManager.getInstance().collectCustomLog(TAG,"onDestroy");
    }

    @Override
    public void onPerformDraw() {
       getMainLooper().setMessageLogging(new Printer() {
           @Override
           public void println(String x) {

           }
       });
    }
}
