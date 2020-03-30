package com.openup.app3.listener;

import android.graphics.Bitmap;
import android.widget.ImageView;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.listener
 * @ClassName: LoadListener
 * @Description: LoadListener
 * @Author: Roy
 * @CreateDate: 2020/3/27 11:00
 */

public interface LoadListener {
    public void onStart(String url, ImageView imageView);

    public void onComplete(String url, Bitmap bitmap);
}
