package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;

import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.ConstantValue;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.entity.AdEntity;
import com.avidly.roy.mediation.utils.LogHelper;
import com.avidly.roy.mediation.utils.ThreadHelper;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.load
 * @ClassName: SequenceLoadr
 * @Description: SequenceLoadr
 * @Author: Roy
 * @CreateDate: 2020/6/3 16:35
 */

public class SequenceLoadr extends BaseLoader {

    @Override
    public void initWillLoads() {
        //admob
        AdEntity admobEntity1 = new AdEntity(RoyNetWorks.ADMOB.getName(), ConstantValue.Admob_RW_ID_1, "");
        AdEntity admobEntity2 = new AdEntity(RoyNetWorks.ADMOB.getName(), ConstantValue.Admob_RW_ID_2, "");

        // unity
        AdEntity unityEntity1 = new AdEntity(RoyNetWorks.Unity.getName(), ConstantValue.Unity_RW_GAME_ID, ConstantValue.Unity_RW_PID_1);
        AdEntity unityEntity2 = new AdEntity(RoyNetWorks.Unity.getName(), ConstantValue.Unity_RW_GAME_ID, ConstantValue.Unity_RW_PID_2);

        //applovin
        AdEntity applovinEntity1 = new AdEntity(RoyNetWorks.APPLOVIN.getName(), ConstantValue.Applovin_RW_ID_1, "");
        AdEntity applovinEntity2 = new AdEntity(RoyNetWorks.APPLOVIN.getName(), ConstantValue.Applovin_RW_ID_2, "");

        addInWillLoadList(admobEntity1, admobEntity2, unityEntity1, unityEntity2, applovinEntity1, applovinEntity2);
        addInLoadingList(admobEntity1, admobEntity2, unityEntity1, unityEntity2, applovinEntity1, applovinEntity2);
    }

    @Override
    public LoadStrategy getLoadStrategy() {
        return LoadStrategy.Sequence;
    }

    public void loadAds(Activity activity) {
        for (AdEntity adEntity : getLoadings()) {
            final BaseAdAdpter adAdpter = getAdAdapter(adEntity, activity);
            ThreadHelper.runOnLoadThread(new Runnable() {
                @Override
                public void run() {
                    adAdpter.load(new InnerDisplayCallBack());
                }
            });
        }
    }

    class InnerDisplayCallBack implements RoyAdLoadCallBack {

        @Override
        public void onAdLoaded(String pid, String adName) {
            LogHelper.logi("onAdLoaded pid " + pid + " ad name" + adName);

        }

        @Override
        public void onAdFailedToLoad(String pid, String adName, String errorCode) {
            LogHelper.logi("onAdFailedToLoad pid " + pid + " ad name" + adName + " errorcode " + errorCode);
        }

    }


}
