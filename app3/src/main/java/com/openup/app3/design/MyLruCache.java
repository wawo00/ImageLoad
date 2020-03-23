package com.openup.app3.design;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design
 * @ClassName: MyLruCache
 * @Description: MyLruCache
 * @Author: Roy
 * @CreateDate: 2020/3/23 18:01
 */

public class MyLruCache implements ImageCache {
    private LruCache<String, Bitmap> mLruCache;

    public MyLruCache() {
        init();
    }

    private void init() {
        mLruCache = new LruCache<String, Bitmap>(getMaxMemory() / 4) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        if (mLruCache.get(url) != null) {
            return;
        }
        mLruCache.put(url,bitmap);
    }

    @Override
    public Bitmap get(String url) {
        Bitmap bitmap=mLruCache.get(url);
        return bitmap;
    }

    private int getMaxMemory() {
        return (int) Runtime.getRuntime().freeMemory();
    }
}
