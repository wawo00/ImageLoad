package com.openup.app3.design;

import android.graphics.Bitmap;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design
 * @ClassName: DoubleCache
 * @Description: DoubleCache
 * @Author: Roy
 * @CreateDate: 2020/3/23 18:18
 */

public class DoubleCache implements ImageCache {

    private MyLruCache mLruCache = new MyLruCache();
    private MyDiskCache mDiskCache = new MyDiskCache();

    @Override
    public void put(String url, Bitmap bitmap) {
        if (mLruCache.get(url) == null) {
            mLruCache.put(url, bitmap);
        }
        if (mDiskCache.get(url) == null) {
            mDiskCache.put(url, bitmap);
        }
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = null;
        if (mLruCache.get(url) != null) {
            bitmap = mLruCache.get(url);
        }
        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }
}
