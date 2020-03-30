package com.openup.imageloader.request;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.openup.imageloader.ImageLoader;
import com.openup.imageloader.config.DisplayConfig;
import com.openup.imageloader.strategy.LoadStrategy;
import com.openup.imageloader.listener.LoadListener;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design.strategy.request
 * @ClassName: BitmapRequest
 * @Description: 网络请求的包装类
 * @Author: Roy
 * @CreateDate: 2020/3/27 10:56
 */

public class BitmapRequest implements Comparable<BitmapRequest> {
    // 请求地址
    private String requestUrl;

    // 展示相关的参数
    private DisplayConfig mDisplayConfig;

    // 请求的回调
    private LoadListener mLoadListener;

    //  需要填充的imageview
    Reference<ImageView> mReference;

    // 请求的序列号
    private AtomicInteger serialNum = new AtomicInteger();

    // 是否取消请求
    private boolean isCancelRequest;

    // 加载策略
    private LoadStrategy mLoadSrategy = ImageLoader.getInstance().getConfig().getLoadSrategy();

   // 不缓存到本地，只缓存到内存中，因为如果是本地的也缓存的话，就相当于拷贝了
    public boolean justCacheInMem = false;


    public BitmapRequest(ImageView imageView, String requestUrl, DisplayConfig displayConfig, LoadListener listener) {
        mReference = new WeakReference<ImageView>(imageView);
        this.requestUrl = requestUrl;
        mLoadListener = listener;
        mDisplayConfig = displayConfig;
        imageView.setTag(requestUrl);
    }


    public int getSerialNum() {
        return serialNum.get();
    }

    public void setSerialNum(int serialNum) {
        this.serialNum.set(serialNum);
    }


    public String getRequestUrl() {
        return requestUrl;
    }

    public LoadListener getLoadListener() {
        return mLoadListener;
    }

    public ImageView getImageView() {
        return mReference.get();
    }

    /**
     * 判断imageview的tag与uri是否相等,常用于listview中的item复用
     *
     * @return
     */
    public boolean isImageViewTagValid() {
        return getImageView() != null ? getImageView().getTag().equals(requestUrl) : false;
    }


    @Override
    public int compareTo(BitmapRequest anthorOne) {
        return mLoadSrategy.compare(this, anthorOne);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj != this) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        BitmapRequest other = (BitmapRequest) obj;

        if (other.getSerialNum() != this.getSerialNum() || other.getRequestUrl() != this.getRequestUrl()) {
            return false;
        }
        if (other.getImageView() != this.getImageView()) {
            return false;
        }
        return true;

    }

    public boolean isCancelRequest() {
        return isCancelRequest;
    }

    public void setCancelRequest(boolean cancelRequest) {
        isCancelRequest = cancelRequest;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result += prime * result + ((requestUrl == null) ? 0 : requestUrl.hashCode());
        result += prime * result + ((mReference == null) ? 0 : mReference.get().hashCode());
        result += prime * result + serialNum.get();
        return result;
    }
}
