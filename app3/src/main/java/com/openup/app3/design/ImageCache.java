package com.openup.app3.design;

import android.graphics.Bitmap;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design
 * @ClassName: ImageCache
 * @Description: ImageCache
 * @Author: Roy
 * @CreateDate: 2020/3/23 17:55
 */

public interface ImageCache {
    public void put(String url, Bitmap bitmap);

    public Bitmap get(String url);
}
