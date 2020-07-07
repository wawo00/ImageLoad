package com.avidly.roy.mediation.strategy.load;

import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.request.AdsRequest;
import com.avidly.roy.mediation.utils.LogHelper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.load
 * @ClassName: AdRequestQueue
 * @Description: adrequst请求队列
 * @Author: Roy
 * @CreateDate: 2020/6/15 11:13
 */

public class AdRequestQueue {
    private BlockingQueue<AdsRequest> mQueue = new PriorityBlockingQueue<>();
    private CopyOnWriteArrayList<RequestDispatch> mDispatches = new CopyOnWriteArrayList<>();

    // 创建的线程数目
    public void addRequest(AdsRequest adsRequest) {
        try {
            if (!mQueue.contains(adsRequest)) {
                mQueue.put(adsRequest);
            } else {
                LogHelper.logi("请求队列中已经存在此请求==" + adsRequest.getRequestId());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void startDispatch() {
        try {
            if (mQueue.size() == 0) {
                return;
            }
            int num=mQueue.size();
            for (int i = 0; i < num; i++) {
                LogHelper.logi("##启动线程" + i);
                RequestDispatch requestDispatch = new RequestDispatch(mQueue.take());
                requestDispatch.start();
                mDispatches.add(requestDispatch);
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }

    }

    public void start() {
//        stop();
        startDispatch();
    }

    public void stop() {
        if (!mDispatches.isEmpty()) {
            for (int i = 0; i < mDispatches.size(); i++) {
                mDispatches.get(i).interrupt();
            }
        }
        mQueue.clear();
    }
}
