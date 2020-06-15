package com.avidly.roy.mediation.strategy.load;

import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.entity.AdAdapterLoadWrapper;

import java.util.concurrent.BlockingQueue;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.load
 * @ClassName: LoadDispatcher
 * @Description: LoadDispatcher
 * @Author: Roy
 * @CreateDate: 2020/6/8 15:19
 */

public class LoadDispatcher extends Thread {
    private BlockingQueue<AdAdapterLoadWrapper> mLoadingQueue;

    /**
     * @param queue
     */
    public LoadDispatcher(BlockingQueue<AdAdapterLoadWrapper> queue) {
        mLoadingQueue = queue;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                AdAdapterLoadWrapper loadWrapper = mLoadingQueue.take();
                if (loadWrapper.getRetryTimes()>3){
                    continue;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        super.run();
    }
}

