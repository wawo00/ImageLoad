package com.openup.app3.design;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design
 * @ClassName: ImageCache
 * @Description: ImageCache
 * @Author: Roy
 * @CreateDate: 2020/3/23 10:31
 */

public class ImageCache {
    private LruCache<String, Bitmap> imgCache;

    public ImageCache() {
        initCache();
    }

    private void initCache() {
        imgCache = new LruCache<String, Bitmap>((int) (getMaxMemory() / 4)) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    private long getMaxMemory() {
        return Runtime.getRuntime().freeMemory();
    }

    public Bitmap getBitmap(String url) {
        return imgCache.get(url);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        if (getBitmap(url) != null) {
            return;
        }
        imgCache.put(url, bitmap);


    }
}
