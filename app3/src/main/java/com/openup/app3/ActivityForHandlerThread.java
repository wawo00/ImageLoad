package com.openup.app3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ActivityForHandlerThread extends AppCompatActivity {
    private HandlerThread mHandlerThread;
    private ImageView iv;
    private Handler workHandler;
    private Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            iv.setImageBitmap((Bitmap) msg.obj);
        }
    };
    /**
     * 图片地址集合
     */
    private String imgUrl[] = {
            "https://img-blog.csdn.net/20160903083245762"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_handler_thread);
        mHandlerThread = new HandlerThread("111");
        mHandlerThread.start();
        iv = findViewById(R.id.iv);
        workHandler = new Handler(mHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                Message message = new Message();
                message.obj = downLoadBitmap();
                mainHandler.sendMessage(message);
                return false;
            }
        });
        workHandler.sendEmptyMessage(1);
    }

    private Bitmap downLoadBitmap() {
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        Bitmap bitmap = null;
        try {
            URL url = new URL(imgUrl[0]);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream(), 8 * 1024);
            bitmap = BitmapFactory.decodeStream(bufferedInputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        workHandler.removeCallbacks(null);
        workHandler.removeMessages(1);
    }
}
