package com.openup.app3.design;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design
 * @ClassName: ImageLoader
 * @Description: ImageLoader
 * @Author: Roy
 * @CreateDate: 2020/3/20 18:28
 */

public class ImageLoader {
    private static String TAG = "Roy_imageLoader";
    private Context mContext;

    ImageCache imgCache = new ImageCache();
    DiskCache mDiskCache=new DiskCache();
    private boolean useDiskCahe;
    // 需要显示的view
    private ImageView mImageView;
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private Handler mainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (mImageView != null) {
                mImageView.setImageBitmap((Bitmap) msg.obj);
            }
        }
    };

    public boolean isUseDiskCahe() {
        return useDiskCahe;
    }

    public void setUseDiskCahe(boolean useDiskCahe) {
        this.useDiskCahe = useDiskCahe;
    }
    public void displayImage(final ImageView view, final String url) {
        Bitmap bitmap = isUseDiskCahe() ? mDiskCache.getImage(url) : imgCache.getBitmap(url);
        if (bitmap == null) {
            mExecutorService.submit(new Runnable() {
                @Override
                public void run() {
                    Bitmap tempImage = downLoadBitmap(url);
                    if (isUseDiskCahe()) {
                        mDiskCache.putImage(url, tempImage);
                    } else {
                        imgCache.putBitmap(url, tempImage);
                    }
                    updateImage(view, tempImage);
                }
            });
        }
        updateImage(view, bitmap);
    }

    private void updateImage(final ImageView view, final Bitmap bitmap) {
        mImageView = view;
        mainHandler.post(new Runnable() {
            @Override
            public void run() {
                view.setImageBitmap(bitmap);
            }
        });
    }

    public Bitmap downLoadBitmap(String imageUrl) {
        URL url = null;
        HttpURLConnection httpURLConnection = null;
        Bitmap bitmap = null;
        BufferedInputStream inputStream = null;
        try {
            url = new URL(imageUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(httpURLConnection.getInputStream(), 8 * 1024);
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpURLConnection.disconnect();
            closeClosabel(inputStream);
        }
        return bitmap;
    }

    private void closeClosabel(Closeable clz) {
        if (clz != null) {
            try {
                clz.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
