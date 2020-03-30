package com.openup.imageloader.load;

import android.graphics.Bitmap;

import com.openup.imageloader.request.BitmapRequest;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.load
 * @ClassName: NoLoader
 * @Description: NoLoader
 * @Author: Roy
 * @CreateDate: 2020/3/27 15:03
 */

public class NoLoader extends AbsLoader {

    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        return null;
    }
}
