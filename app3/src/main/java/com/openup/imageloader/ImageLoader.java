package com.openup.imageloader;

import android.widget.ImageView;

import com.openup.imageloader.cache.ImageCache;
import com.openup.imageloader.config.ImageLoaderConfig;
import com.openup.imageloader.listener.LoadListener;
import com.openup.imageloader.request.BitmapRequest;
import com.openup.imageloader.request.ILoadRequest;
import com.openup.imageloader.request.ImageLoadRequestPool;
import com.openup.imageloader.request.ImageLoadRequestQueue;

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
    ILoadRequest loadRequest;

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
//        loadRequest = new ImageLoadRequestQueue(mConfig.getThreadCount());
//        loadRequest.start();
        loadRequest=new ImageLoadRequestPool();
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
        loadRequest.addRequest(bitmapRequest);
    }


    public void stop() {
        if (loadRequest != null) {
            loadRequest.stopDispath();
        }
    }

    public void destory() {
        if (loadRequest != null) {
            loadRequest.clear();
            loadRequest = null;
        }
    }
}

