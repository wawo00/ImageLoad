package com.openup.imageloader.config;

import com.openup.imageloader.cache.DoubleCache;
import com.openup.imageloader.cache.ImageCache;
import com.openup.imageloader.strategy.LoadStrategy;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design.config
 * @ClassName: ImageLoaderConfig
 * @Description: ImageLoaderConfig
 * @Author: Roy
 * @CreateDate: 2020/3/27 10:40
 */

public class ImageLoaderConfig {
    // 选择的缓存策略
    private ImageCache mImageCache = new DoubleCache();
    // loading和失败时候的显示策略
    private DisplayConfig mDisplayConfig = new DisplayConfig();

    // 加载策略
    private LoadStrategy mLoadSrategy;

    // 设置线程池大小
    private int threadCount = Runtime.getRuntime().availableProcessors() + 1;

    public ImageCache getImageCache() {
        return mImageCache;
    }

    public LoadStrategy getLoadSrategy() {
        return mLoadSrategy;
    }

    public DisplayConfig getDisplayConfig() {
        return mDisplayConfig;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public ImageLoaderConfig setThreadCount(int count) {
        this.threadCount = Math.max(1, count);
        return this;
    }

    public ImageLoaderConfig setImageCache(ImageCache imageCache) {
        mImageCache = imageCache;
        return this;
    }
    public ImageLoaderConfig setImageLoadingHold(int drawable) {
        mDisplayConfig.loadingResId = drawable;
        return this;
    }

    public ImageLoaderConfig setImageLoadingFail(int drawable) {
        mDisplayConfig.failedResId = drawable;
        return this;
    }


    public ImageLoaderConfig setLoadSrategy(LoadStrategy loadSrategy) {
        mLoadSrategy = loadSrategy;
        return this;
    }
}
