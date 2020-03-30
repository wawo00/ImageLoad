package com.openup.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import com.openup.app3.R;
import com.openup.imageloader.cache.ImageCache;
import com.openup.imageloader.config.DisplayConfig;
import com.openup.imageloader.config.ImageLoaderConfig;
import com.openup.imageloader.listener.LoadListener;
import com.openup.imageloader.load.LoadManager;
import com.openup.imageloader.request.BitmapRequest;
import com.openup.imageloader.request.ImageLoadRequestQueue;
import com.openup.imageloader.strategy.LoadStrategy;
import com.openup.imageloader.strategy.SerialPolicy;

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
    private static ImageLoader sInstance = new ImageLoader();
    private static ImageCache mImageCache;
    // 需要显示的view
    private static ImageView mImageView;
    private ImageLoaderConfig mConfig;
    ImageLoadRequestQueue queue;

    public ImageLoaderConfig getConfig() {
        return mConfig;
    }

    public void setConfig(ImageLoaderConfig config) {
        mConfig = config;
    }

    private ImageLoader() {
    }

    public static ImageLoader getInstance() {
        return sInstance;
    }

    public void init(ImageLoaderConfig config) {
        checkConfig(config);
        queue = new ImageLoadRequestQueue(mConfig.getThreadCount());
        queue.start();
    }

    private void checkConfig(ImageLoaderConfig config) {
        if (config == null) {
            throw new RuntimeException("null config is invalidated");
        }
        mConfig = config;
        mImageCache = mConfig.getImageCache();
    }


    public void displayImage(final ImageView view, final String url, LoadListener loadListener) {
        BitmapRequest bitmapRequest = new BitmapRequest(view, url, mConfig.getDisplayConfig(), loadListener == null ? null : loadListener);
        queue.addRequest(bitmapRequest);
    }


    public void stop() {
        if (queue != null) {
            queue.stopDispath();
        }
    }

    public void destory() {
        if (queue != null) {
            queue.clear();
            queue = null;
        }
    }
}

