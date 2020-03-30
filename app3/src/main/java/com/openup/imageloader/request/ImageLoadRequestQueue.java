package com.openup.imageloader.request;

import com.openup.imageloader.ImageLoader;
import com.openup.imageloader.strategy.LoadStrategy;
import com.openup.imageloader.utils.LogHelper;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.request
 * @ClassName: ImageLoadRequestQueue
 * @Description: ImageLoadRequestQueue
 * @Author: Roy
 * @CreateDate: 2020/3/27 15:35
 */

public class ImageLoadRequestQueue implements ILoadRequest {

    private BlockingQueue<BitmapRequest> mRequestQueue = new PriorityBlockingQueue<BitmapRequest>(20);

    // 请求id的生成器
    private AtomicInteger mSerialNumGenerator = new AtomicInteger(0);

    // 能开启的线程=核心线程+1
    private int dispathNum = Runtime.getRuntime().availableProcessors() + 1;

    // dispath数组
    private RequestDispatcher[] mDispatchers = null;

    public void clear() {
        mRequestQueue.clear();
    }

    public ImageLoadRequestQueue(int processNum) {
        this.dispathNum = Math.min(processNum, dispathNum);
    }

    /**
     * 天机request到请求队列中
     *
     * @param request
     */
    public void addRequest(BitmapRequest request) {
        if (!mRequestQueue.contains(request)) {
            int requestId = generateSerialNum();
            request.setSerialNum(requestId);
            mRequestQueue.add(request);
            LogHelper.logi("mRequestQueue size is "+mRequestQueue.size());
        } else {
            LogHelper.loge("request" + request.getSerialNum() + " has been added in this queue");
        }
    }

    private final void startDispath() {
        mDispatchers = new RequestDispatcher[dispathNum];
        for (int i = 0; i < dispathNum; i++) {
            LogHelper.logi("启动线程" + i);
            mDispatchers[i] = new RequestDispatcher(mRequestQueue,"thread ="+i);
            mDispatchers[i].start();
        }
    }

    public void stopDispath() {
        if (mDispatchers != null && mDispatchers.length > 0) {
            for (RequestDispatcher dispatcher : mDispatchers) {
                dispatcher.interrupt();
            }
        }
    }

    public void start() {
        stopDispath();
        startDispath();
    }

    private final int generateSerialNum() {
        // 为每个请求生成序列号
        return mSerialNumGenerator.incrementAndGet();
    }

}
