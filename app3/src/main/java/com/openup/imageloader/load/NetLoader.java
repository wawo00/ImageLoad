package com.openup.imageloader.load;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.openup.imageloader.request.BitmapRequest;
import com.openup.imageloader.utils.IOutils;
import com.openup.imageloader.utils.LogHelper;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.load
 * @ClassName: NetLoader
 * @Description: NetLoader
 * @Author: Roy
 * @CreateDate: 2020/3/27 14:23
 */

public class NetLoader extends AbsLoader {

    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        URL mURL;
        HttpURLConnection mHttpURLConnection = null;
        BufferedInputStream mInputStream = null;
        Bitmap mBitmap = null;
        try {
            mURL = new URL(request.getRequestUrl());
            mHttpURLConnection = (HttpURLConnection) mURL.openConnection();
            mInputStream = new BufferedInputStream(mHttpURLConnection.getInputStream(), 8 * 1024);
            mBitmap = BitmapFactory.decodeStream(mInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mHttpURLConnection.disconnect();
            IOutils.closeCloseable(mInputStream);
        }
        return mBitmap;
    }

    // 为了不用每次都这么写，使用一个IOUtils
//    private static void closeClosabel(Closeable clz) {
//        if (clz != null) {
//            try {
//                clz.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
