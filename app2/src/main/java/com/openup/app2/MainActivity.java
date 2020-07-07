package com.openup.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private static final String TAG = "ROY";
    private HandlerThread mHandlerThread;
    private Handler workHandler;
    private Handler mainHandler;
    private Executor mExecutor = Executors.newFixedThreadPool(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        testWorkHandler();
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                testSetInitListener1();
            }
        });
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                testSetInitListener2();
            }
        });

    }

    private void testSetInitListener1() {
        MSSKAPI.setInitListener(new MSSKAPI.InitListener() {
            @Override
            public void onInited() {
                Log.i(TAG, "onInited: in listener1");
            }
        });
    }

    private void testSetInitListener2() {
        MSSKAPI.setInitListener(new MSSKAPI.InitListener() {
            @Override
            public void onInited() {
                Log.i(TAG, "onInited: in listener2");

            }
        });
    }


    private void testWorkHandler() {
        mHandlerThread = new HandlerThread("roy");
        mHandlerThread.start();
        tv = findViewById(R.id.tv);
        mainHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (tv == null) {
                    Log.i("ROY", "handleMessage: NULL");
                } else {
                    tv.setText(msg.what + "");
                }
                super.handleMessage(msg);
            }
        };


        workHandler = new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                for (int i = 0; i < 100; i++) {

                    mainHandler.sendEmptyMessageDelayed(i, 1 * 1000);
                }
                return true;
            }
        });

        workHandler.sendEmptyMessageDelayed(1, 1 * 1000);
    }


    public void getMd5value(View view) {
        String test1="9132912sad1dlas";
        String test2="速度快萨拉的1231sxczx;121-0101";
        Log.i(TAG, "getMd5value: test1"+test1);
        Log.i(TAG, "getMd5value: test1"+test2);
        Log.i(TAG, "getMd5value: after"+MD5Utils.getStringMD5(test1));
        Log.i(TAG, "getMd5value: after"+MD5Utils.getStringMD5(test2));
    }
}
