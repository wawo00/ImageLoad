package com.openup.app3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivityForHandler extends AppCompatActivity {

    private ImageView iv;
    private Handler mHandler;
    /**
     * 图片地址集合
     */
    private String imgUrl[] = {
            "https://img-blog.csdn.net/20160903083245762"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv = findViewById(R.id.iv);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
             iv.setImageBitmap((Bitmap) msg.obj);
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                downLoadBitmap();
                Message message=new Message();
                message.obj=downLoadBitmap();
                mHandler.sendMessage(message);
            }
        }).start();

    }

    private Bitmap downLoadBitmap() {
        HttpURLConnection httpURLConnection = null;
        BufferedInputStream bufferedInputStream = null;
        Bitmap bitmap = null;
        try {
            URL url=new URL(imgUrl[0]);
            httpURLConnection= (HttpURLConnection) url.openConnection();
            bufferedInputStream=new BufferedInputStream(httpURLConnection.getInputStream(),8*1024);
            bitmap= BitmapFactory.decodeStream(bufferedInputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}