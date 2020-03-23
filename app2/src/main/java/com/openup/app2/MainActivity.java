package com.openup.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private HandlerThread mHandlerThread;
    private Handler workHandler;
    private Handler mainHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandlerThread = new HandlerThread("roy");
        mHandlerThread.start();
        tv = findViewById(R.id.tv);

        mainHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
              if (tv==null){
                  Log.i("ROY", "handleMessage: NULL");
              }else {
                  tv.setText(msg.what+"");
              }
                super.handleMessage(msg);
            }
        };


        workHandler = new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {
                for (int i = 0; i < 100; i++) {

                    mainHandler.sendEmptyMessageDelayed(i,1*1000);
                }
                return true;
            }
        });

        workHandler.sendEmptyMessageDelayed(1,1*1000);

    }


}
