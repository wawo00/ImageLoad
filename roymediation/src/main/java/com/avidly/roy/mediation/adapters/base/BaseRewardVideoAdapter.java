package com.avidly.roy.mediation.adapters.base;

import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.RoyAdType;

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
}
