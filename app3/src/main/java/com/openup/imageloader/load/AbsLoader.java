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
    /**
     * 图片缓存
     */
    private static ImageCache mCache = ImageLoader.getInstance().getConfig().getImageCache();

//    @Override
//    public void loadBitmap(BitmapRequest bitmapRequest) {
//        Bitmap resultBitmap=mCache.get(bitmapRequest.getRequestUrl());
//        StringBuffer stringBuffer=new StringBuffer();
//        if (resultBitmap==null){
//            stringBuffer.append("no cahce");
//        }else{
//            stringBuffer.append("has cahce");
//        }
//        stringBuffer.append("--uri = " + bitmapRequest.getRequestUrl());
//        LogHelper.logi(stringBuffer.toString());
//
//        if (resultBitmap == null) {
//            showLoadingImg(bitmapRequest);
//            resultBitmap = onLoadImage(bitmapRequest);
//            cacheBitmap(resultBitmap, bitmapRequest);
//        } else {
//            bitmapRequest.justCacheInMem = true;
//        }
//
//        deliveryToUIThread(bitmapRequest, resultBitmap);
//    }


    @Override
    public void loadBitmap(BitmapRequest bitmapRequest) {
        mBitmapRequest = bitmapRequest;
       ImageView mImageView = bitmapRequest.getImageView();
        Bitmap bitmap = mConfig.getImageCache().get(bitmapRequest.getRequestUrl());
        StringBuffer stringBuffer = new StringBuffer();
        if (bitmap == null) {
            stringBuffer.append("no cahce");
        } else {
            stringBuffer.append("has cahce");
        }
        stringBuffer.append("--uri = " + bitmapRequest.getRequestUrl());
        LogHelper.logi(stringBuffer.toString());
        if (bitmap == null) {
            // 进行网络的下载
//            showLoadingImg(mConfig);
            bitmap = onLoadImage(bitmapRequest);
            cacheBitmap(bitmap, bitmapRequest);
        } else {
            bitmapRequest.justCacheInMem = true;
        }
        LogHelper.logi(" loadbitmap result is " + bitmap.getByteCount());
        // 显示到UI上
        deliveryToUIThread(bitmapRequest, bitmap);
    }


    private void deliveryToUIThread(final BitmapRequest bitmapRequest, final Bitmap bitmap) {
        final ImageView imageView = bitmapRequest.getImageView();
        if (imageView == null) {
            return;
        }
        imageView.post(new Runnable() {
            @Override
            public void run() {
                updateImage(bitmapRequest, bitmap);
            }
        });

    }

    private void updateImage(BitmapRequest bitmapRequest, Bitmap bitmap) {
        final ImageView mImageView = bitmapRequest.getImageView();
        if (bitmap == null && hasLoadFailImg(mConfig.getDisplayConfig())) {
            mImageView.setImageResource(mConfig.getDisplayConfig().failedResId);
        }
        if (bitmapRequest.getLoadListener() != null) {
            bitmapRequest.getLoadListener().onComplete(bitmapRequest.getRequestUrl(), bitmap);
        }
        if (bitmapRequest != null && mImageView.getTag().equals(bitmapRequest.getRequestUrl())) {
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

    public void showLoadingImg(final BitmapRequest bitmapRequest) {
        final ImageView view = bitmapRequest.getImageView();
        if (hasLoadingImg(mConfig.getDisplayConfig()) && bitmapRequest.isImageViewTagValid()) {
            view.post(new Runnable() {
                @Override
                public void run() {
                    view.setImageResource(mConfig.getDisplayConfig().loadingResId);
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
