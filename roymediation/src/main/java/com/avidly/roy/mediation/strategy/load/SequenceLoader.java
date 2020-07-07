package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;

import com.avidly.roy.mediation.RoyAdsApi;
import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.ConstantValue;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.entity.AdEntity;
import com.avidly.roy.mediation.utils.LogHelper;
import com.avidly.roy.mediation.utils.ThreadHelper;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.load
 * @ClassName: SequenceLoader
 * @Description: SequenceLoader
 * @Author: Roy
 * @CreateDate: 2020/6/3 16:35
 */

public class SequenceLoader extends BaseLoader {
    private BaseAdAdpter mAdAdpter;
    private static int MAX_RetryTimes = 3;

    @Override
    public LoadStrategy getLoadStrategy() {
        return LoadStrategy.Sequence;
    }

    @Override
    public void startLoad() {
        LogHelper.logi("loading size is " + getLoadings().toString());
        if (getLoadings().isEmpty()) {
            return;
        }
        for (final BaseAdAdpter adAdpter : getLoadings()) {
            AdAdapterLoadWrapper wrapper = new AdAdapterLoadWrapper(adAdpter);
            loadAds(wrapper, 0);
        }
    }

    @Override
    public void startLoad(BaseAdAdpter adAdpter) {

    }

    /**
     * 包装成wrapper,用于增加重试策略
     *
     * @param wrapper
     */
    private void loadAds(AdAdapterLoadWrapper wrapper, int secDelay) {
        if (secDelay > 0) {
            ThreadHelper.getInstance().runInWorkThreadDelay(new LoadRunnable(wrapper), secDelay);
        } else {
            ThreadHelper.getInstance().runInWorkThread(new LoadRunnable(wrapper));
        }
    }


    class LoadRunnable implements Runnable {
        AdAdapterLoadWrapper innerAdapterWrapper;

        LoadRunnable(AdAdapterLoadWrapper innerAdapterWrapper) {
            this.innerAdapterWrapper = innerAdapterWrapper;
        }

        @Override
        public void run() {
            innerAdapterWrapper.getAdAdpter().load(new RoyAdLoadCallBack() {
                @Override
                public void onAdLoaded(String pid, String adName) {
                    LogHelper.logi("onAdLoaded pid " + pid + " ad name" + adName);
                    notifyCpLoadCallback();
                    innerAdapterWrapper.reset();
                    addinLoadedList(innerAdapterWrapper.getAdAdpter());
                    removeFromLoadingList(innerAdapterWrapper.getAdAdpter());
                }

                @Override
                public void onAdFailedToLoad(String pid, String adName, String errorCode) {
                    LogHelper.logi("onAdFailedToLoad pid " + pid + " ad name" + adName + " errorcode " + errorCode);
//                notifyCpLoadCallback(false);
                    removeFromLoadingList(innerAdapterWrapper.getAdAdpter());
                    innerAdapterWrapper.retryLoad();
                    if (innerAdapterWrapper.getRetryTimes() <= MAX_RetryTimes) {
                        loadAds(innerAdapterWrapper, 10);
                    } else {

                    }
                }
            });
        }
    }
}

class AdAdapterLoadWrapper {
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



