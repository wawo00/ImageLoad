package com.openup.imageloader.request;

import android.graphics.Bitmap;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.request
 * @ClassName: ILoadRequest
 * @Description: ILoadRequest
 * @Author: Roy
 * @CreateDate: 2020/3/30 16:53
 */

public interface ILoadRequest {

    public void addRequest(BitmapRequest bitmapRequest);

    public void start();

    public void stopDispath();

    public void clear();
}
