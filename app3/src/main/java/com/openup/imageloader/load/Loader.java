package com.openup.imageloader.load;

import android.graphics.Bitmap;

import com.openup.imageloader.request.BitmapRequest;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.load
 * @ClassName: Loader
 * @Description: Loader
 * @Author: Roy
 * @CreateDate: 2020/3/27 14:18
 */

public interface Loader {
    public void loadBitmap(BitmapRequest bitmapRequest);
}
