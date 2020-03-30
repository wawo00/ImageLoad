package com.openup.imageloader.strategy;


import com.openup.imageloader.request.BitmapRequest;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.strategy
 * @ClassName: SerialPolicy
 * @Description: SerialPolicy
 * @Author: Roy
 * @CreateDate: 2020/3/27 11:21
 */

public class SerialPolicy implements LoadStrategy {

    @Override
    public int compare(BitmapRequest bitmapRequest1, BitmapRequest bitmapRequest2) {
        return bitmapRequest1.getSerialNum()-bitmapRequest2.getSerialNum();
    }
}
