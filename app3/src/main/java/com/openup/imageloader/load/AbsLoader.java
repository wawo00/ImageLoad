package com.openup.imageloader.load;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.openup.imageloader.ImageLoader;
import com.openup.imageloader.cache.ImageCache;
import com.openup.imageloader.config.DisplayConfig;
import com.openup.imageloader.config.ImageLoaderConfig;
import com.openup.imageloader.request.BitmapRequest;
import com.openup.imageloader.utils.LogHelper;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.load
 * @ClassName: AbsLoader
 * @Description: 图片从缓存的获取和保存到缓存
 * @Author: Roy
 * @CreateDate: 2020/3/27 16:49
 */

public abstract class AbsLoader implements Loader {
    private ImageLoaderConfig mConfig = ImageLoader.getInstance().getConfig();
    private BitmapRequest mBitmapRequest;
    private ImageView mImageView;


    @Override
    public void loadBitmap(BitmapRequest bitmapRequest) {
        mBitmapRequest = bitmapRequest;
        mImageView = bitmapRequest.getImageView();
        Bitmap bitmap = mConfig.getImageCache().get(bitmapRequest.getRequestUrl());

        LogHelper.logi("### 是否有缓存 : " + bitmapRequest + ", uri = " + bitmapRequest.getRequestUrl());
        if (bitmap == null) {
            // 进行网络的下载
            showLoadingImg(mConfig);
            bitmap = onLoadImage(bitmapRequest);
            cacheBitmap(bitmap, bitmapRequest);
        } else {
            bitmapRequest.justCacheInMem = true;
        }

        // 显示到UI上
        deliveryToUIThread(bitmapRequest.getImageView(), bitmap);

    }

    private void deliveryToUIThread(final ImageView imageView, final Bitmap bitmap) {
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() {
            @Override
            public void run() {
                updateImage(bitmap);
            }
        });

    }

    private void updateImage(Bitmap bitmap) {
        if (bitmap == null && hasLoadFailImg(mConfig.getDisplayConfig())) {
            mImageView.setImageResource(mConfig.getDisplayConfig().failedResId);
        }
        if (mBitmapRequest.getLoadListener() != null) {
            mBitmapRequest.getLoadListener().onComplete(mBitmapRequest.getRequestUrl(), bitmap);
        }
        if (mBitmapRequest != null && mImageView.getTag().equals(mBitmapRequest.getRequestUrl())) {
            mImageView.setImageBitmap(bitmap);
        }
    }

    private void cacheBitmap(Bitmap bitmap, BitmapRequest bitmapRequest) {
        if (mConfig.getImageCache() != null && bitmap != null) {
            synchronized (mConfig.getImageCache()) {
                mConfig.getImageCache().put(bitmapRequest.getRequestUrl(), bitmap);

            }
        }
    }

    public void showLoadingImg(final ImageLoaderConfig config) {
        final ImageView view = mBitmapRequest.getImageView();
        if (hasLoadingImg(config.getDisplayConfig()) && mBitmapRequest.isImageViewTagValid()) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setImageResource(config.getDisplayConfig().loadingResId);
                }
            });
        }

    }

    /**
     * @param request
     * @return
     */
    protected abstract Bitmap onLoadImage(BitmapRequest request);


    private boolean hasLoadingImg(DisplayConfig displayConfig) {
        return displayConfig != null && mConfig.getDisplayConfig().loadingResId > 0;
    }

    private boolean hasLoadFailImg(DisplayConfig displayConfig) {
        return displayConfig != null && displayConfig.failedResId > 0;
    }
}
