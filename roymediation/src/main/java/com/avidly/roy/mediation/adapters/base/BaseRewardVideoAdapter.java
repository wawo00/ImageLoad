package com.avidly.roy.mediation.adapters.base;

import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.RoyAdType;
import com.avidly.roy.mediation.utils.LogHelper;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.adapters.base
 * @ClassName: BaseRewardVideoAdapter
 * @Description: BaseRewardVideoAdapter
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:46
 */

public abstract class BaseRewardVideoAdapter extends BaseAdAdpter {
    public RoyAdLoadCallBack mLoadCallBack;
    public RoyAdDisplayCallBack mDisplayCallBack;

    @Override
    public RoyAdType getType() {
        return RoyAdType.REWARDVIDEO;
    }

    protected void setLoadCallBack(RoyAdLoadCallBack loadCallBack) {
        mLoadCallBack = loadCallBack;
    }

    protected void setDisplayCallBack(RoyAdDisplayCallBack royAdDisplayCallBack) {
        mDisplayCallBack = royAdDisplayCallBack;
    }
    @Override
    public void load(RoyAdLoadCallBack loadCallBack) {
        setLoadCallBack(loadCallBack);
        LogHelper.logi("load rewardVideo "+getAdEntity().toString());
    }

    @Override
    public void show(RoyAdDisplayCallBack displayCallBack) {
        setDisplayCallBack(displayCallBack);
        LogHelper.logi("show rewardVideo "+getAdEntity().toString());
    }

    protected RoyAdLoadCallBack getLoadCallBack() {
        return mLoadCallBack;
    }

    protected RoyAdDisplayCallBack getDisplayCallBack() {
        return mDisplayCallBack;
    }
}
