package com.openup.imageloader.strategy;


import com.openup.imageloader.request.BitmapRequest;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.strategy
 * @ClassName: ReverseStrategy
 * @Description: ReverseStrategy
 * @Author: Roy
 * @CreateDate: 2020/3/27 11:25
 */

public class ReverseStrategy implements LoadStrategy {
    @Override
    public int compare(BitmapRequest bitmapRequest1, BitmapRequest bitmapRequest2) {
        return bitmapRequest2.getSerialNum()-bitmapRequest1.getSerialNum();
    }
}
