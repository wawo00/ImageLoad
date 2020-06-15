package com.openup.imageloader.request;

import android.util.Log;

import com.openup.imageloader.load.LoadManager;
import com.openup.imageloader.utils.LogHelper;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.request
 * @ClassName: RequestDispatcher
 * @Description: 循环将请求拿出来执行
 * @Author: Roy
 * @CreateDate: 2020/3/27 15:25
 */

public class RequestDispatcher extends Thread {
    private BlockingQueue<BitmapRequest> mQueue;

    public RequestDispatcher(BlockingQueue<BitmapRequest> requestDeque,String name) {
        super(name);
        mQueue = requestDeque;
    }

    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                BitmapRequest request = mQueue.take();
                LogHelper.logi("request in RequestDispatcher is"+request.getRequestUrl()+" id is"+request.getSerialNum()+" thread is "+this.getName());
                if (request.isCancelRequest()) {
                    continue;
                }
                final String schema = parseSchema(request.getRequestUrl());
                LoadManager.getInstance().getLoader(schema).loadBitmap(request);
            }
        } catch (InterruptedException e) {
            LogHelper.loge("thread has error " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String parseSchema(String uri) {
        if (uri.contains("://")) {
            return uri.split("://")[0];
        } else {
            LogHelper.loge(getName() + "### wrong scheme, image uri is : " + uri);
        }
        return "";
    }

}
