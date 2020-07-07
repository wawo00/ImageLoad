package com.avidly.roy.mediation.strategy.load;

import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.request.AdsRequest;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.load
 * @ClassName: RequestDispatch
 * @Description: RequestDispatch
 * @Author: Roy
 * @CreateDate: 2020/6/15 11:15
 */

public class RequestDispatch extends Thread {

    private AdsRequest mRequest;


    public RequestDispatch(AdsRequest adsRequest) {
        mRequest = adsRequest;
    }

    @Override
    public void run() {
        if (!this.isInterrupted()) {
            if (mRequest.isCancel()) {
                return;
            }
            mRequest.getBaseAdAdpter().load(mRequest.getRoyAdLoadCallBack());
        }
        super.run();
    }
}
