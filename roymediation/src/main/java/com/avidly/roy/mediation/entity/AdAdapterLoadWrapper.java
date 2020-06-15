package com.avidly.roy.mediation.entity;

import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.entity
 * @ClassName: AdAdapterLoadWrapper
 * @Description: AdAdapterLoadWrapper
 * @Author: Roy
 * @CreateDate: 2020/6/8 15:26
 */

public class AdAdapterLoadWrapper {
    private int retryTimes;
    private BaseAdAdpter mAdAdpter;
    public AdAdapterLoadWrapper(BaseAdAdpter adAdpter) {
        mAdAdpter = adAdpter;
    }

    public int getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }

    public BaseAdAdpter getAdAdpter() {
        return mAdAdpter;
    }

    public void setAdAdpter(BaseAdAdpter adAdpter) {
        mAdAdpter = adAdpter;
    }

    public void retryLoad() {
        retryTimes++;
    }

    public void reset() {
        retryTimes = 0;
    }
}
