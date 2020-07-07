package com.avidly.roy.mediation.wrapper;

import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.callback.RoyAdOuterDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdOuterLoadCallBack;
import com.avidly.roy.mediation.strategy.ShowManager;
import com.avidly.roy.mediation.strategy.load.LoadManager;
import com.avidly.roy.mediation.utils.LogHelper;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.wrapper
 * @ClassName: RoyRewardVideo
 * @Description: RoyRewardVideo
 * @Author: Roy
 * @CreateDate: 2020/6/5 14:58
 */

public class RoyRewardVideo {
    RoyAdOuterLoadCallBack mCpLoadCallBack;

    public void setLoadCallBack(RoyAdOuterLoadCallBack loadCallBack) {
        mCpLoadCallBack = loadCallBack;
        LoadManager.getInstance().setLoadCallBack(loadCallBack);
    }

    public boolean isReady() {
        return LoadManager.getInstance().hasReadyAds();
    }

    public void show(String placementId, RoyAdOuterDisplayCallBack displayCallBack) {
        if (!isReady()) {
            LogHelper.logw("no ads ");
            return;
        }
        ShowManager.getInstance().showAd(placementId, displayCallBack);
    }
}
