package com.avidly.roy.mediation.adapters;

import android.app.Activity;

import com.avidly.roy.mediation.adapters.base.BaseRewardVideoAdapter;
import com.avidly.roy.mediation.adapters.videoAds.AdmobRewardVideoAdapter;
import com.avidly.roy.mediation.adapters.videoAds.ApplovinRewardVideoAdapter;
import com.avidly.roy.mediation.adapters.videoAds.UnityRewardVideoAdapter;
import com.avidly.roy.mediation.constant.RoyNetWorks;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.adapters
 * @ClassName: RewardVideoAdapterFactory
 * @Description: RewardVideoAdapterFactory
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:51
 */

public class RewardVideoAdapterFactory {

    public  static BaseRewardVideoAdapter getAdapter(RoyNetWorks netWorks, Activity activity) {
        switch (netWorks) {
            case ADMOB:
                return AdmobRewardVideoAdapter.newInstance(activity);
            case Unity:
                return UnityRewardVideoAdapter.getInstance(activity);
            case APPLOVIN:
                return ApplovinRewardVideoAdapter.getNewInstance(activity);
        }
        return null;
    }


}
