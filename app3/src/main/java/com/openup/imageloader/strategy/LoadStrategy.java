package com.openup.imageloader.strategy;


import com.openup.imageloader.request.BitmapRequest;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design.strategy
 * @ClassName: LoadStrategy
 * @Description: LoadStrategy
 * @Author: Roy
 * @CreateDate: 2020/3/27 10:55
 */

public interface LoadStrategy {
    public int compare(BitmapRequest bitmapRequest1, BitmapRequest bitmapRequest2);
}
