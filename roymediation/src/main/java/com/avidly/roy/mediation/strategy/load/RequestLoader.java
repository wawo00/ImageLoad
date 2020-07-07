package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;
import android.util.Log;

import com.avidly.roy.mediation.RoyAdsApi;
import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.ConstantValue;
import com.avidly.roy.mediation.constant.RoyAdType;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.entity.AdEntity;
import com.avidly.roy.mediation.request.AdsRequest;
import com.avidly.roy.mediation.utils.LogDevHelper;
import com.avidly.roy.mediation.utils.LogHelper;
import com.avidly.roy.mediation.utils.ThreadHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;

import java.util.ArrayList;
import java.util.List;

import static com.avidly.roy.mediation.strategy.load.LoadStrategy.Request;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.load
 * @ClassName: RequestLoader
 * @Description: RequestLoader
 * @Author: Roy
 * @CreateDate: 2020/6/15 16:26
 */

public class RequestLoader extends BaseLoader {
    private AdRequestQueue mAdRequestQueue;
    private int retryTimes;
    private List<BaseAdAdpter> mAdpters;

    @Override
    public LoadStrategy getLoadStrategy() {
        return Request;
    }

    @Override
    public void startLoad() {
        if (mAdRequestQueue == null) {
            mAdRequestQueue = new AdRequestQueue();
        }
        initWillLoads(RoyAdsApi.getActivity(), retryTimes);
        retryTimes++;
    }

    /**
     * 用于retry操作
     *
     * @param adAdpter
     */
    @Override
    public void startLoad(BaseAdAdpter adAdpter) {
//        if (adAdpter.mAdEntity.getRetryNum() == adAdpter.mAdEntity.getMaxRetry()) {
//
//            return;
//        }
//        adAdpter.mAdEntity.countRetryNum();
//        mAdRequestQueue.addRequest(new AdsRequest(adAdpter, RoyAdType.REWARDVIDEO, new InnerCallBack(adAdpter)));
//        mAdRequestQueue.start();
    }

    private void initWillLoads(Activity activity, int retryTimes) {
        //admob
        getAdEntities(activity, retryTimes);
//        mAdpters.add(applovinEntity1);
//        mAdpters.add(applovinEntity2);

    }

    /**
     * 看下如何拆分reloaded的adapter和需要load的adapter的重试关系
     *
     * @param activity
     * @param retryTimes
     * @return
     */
    private void getAdEntities(Activity activity, int retryTimes) {

        if (retryTimes == 0) {
            mAdpters = new ArrayList<>();
            AdEntity admobEntity1 = new AdEntity(RoyNetWorks.ADMOB.getName(), ConstantValue.Admob_RW_ID_1, "", 4, retryTimes);
            AdEntity admobEntity2 = new AdEntity(RoyNetWorks.ADMOB.getName(), ConstantValue.Admob_RW_ID_2, "", 4, retryTimes);
            AdEntity admobEntity3 = new AdEntity(RoyNetWorks.ADMOB.getName(), ConstantValue.Admob_RW_ID_3, "", 6, retryTimes);
            AdEntity admobEntity4 = new AdEntity(RoyNetWorks.ADMOB.getName(), ConstantValue.Admob_RW_ID_4, "", 3, retryTimes);

            //applovin
            AdEntity applovinEntity1 = new AdEntity(RoyNetWorks.APPLOVIN.getName(), ConstantValue.Applovin_RW_ID_1, "");
            AdEntity applovinEntity2 = new AdEntity(RoyNetWorks.APPLOVIN.getName(), ConstantValue.Applovin_RW_ID_2, "");

            //unity
            AdEntity unity1 = new AdEntity(RoyNetWorks.Unity.getName(), ConstantValue.Unity_RW_GAME_ID, ConstantValue.Unity_RW_PID_1, 4);
            AdEntity unity2 = new AdEntity(RoyNetWorks.Unity.getName(), ConstantValue.Unity_RW_GAME_ID, ConstantValue.Unity_RW_PID_2, 6);

//        addInWillLoadList(activity, admobEentity1, admobEntity2, admobEntity3, admobEntity4, applovinEntity1, applovinEntity2);

//            mAdpters.add(getAdAdapter(admobEntity1, activity));
////            mAdpters.add(getAdAdapter(admobEntity2, activity));
////            mAdpters.add(getAdAdapter(admobEntity3, activity));
////            mAdpters.add(getAdAdapter(admobEntity4, activity));
            mAdpters.add(getAdAdapter(unity1, activity));
            mAdpters.add(getAdAdapter(unity2, activity));

        } else {
            mAdRequestQueue.stop();
        }


        for (BaseAdAdpter adAdpter : mAdpters) {
            // 如果已经loading了就不去load了
            if (getLoadeds().contains(adAdpter)) {
                LogDevHelper.logLoadI("已经存在于loaded中，不需要load" + adAdpter.mAdEntity.toString());
                continue;
            }
            LogDevHelper.logLoadI("加载-" + adAdpter.mAdEntity.toString());
            mAdRequestQueue.addRequest(new AdsRequest(adAdpter, RoyAdType.REWARDVIDEO, new InnerCallBack(adAdpter)));
        }
        mAdRequestQueue.start();
    }

    class InnerCallBack implements RoyAdLoadCallBack {
        BaseAdAdpter adAdpter;

        InnerCallBack(BaseAdAdpter adAdpter) {
            this.adAdpter = adAdpter;
        }

        @Override
        public void onAdLoaded(String pid, String adName) {
            LogDevHelper.logLoadI("onAdLoaded pid " + pid + " ad name" + adName);
            // unity 联盟只追加设置的placementid的key到loaded中
            if (adName.equals(RoyNetWorks.Unity.getName()) && !adAdpter.mAdEntity.getNetWorkPId().equals(pid)) {
                return;
            }
            addinLoadedList(adAdpter);
            notifyCpLoadCallback();
        }

        @Override
        public void onAdFailedToLoad(String pid, String adName, String errorCode) {
            LogHelper.logi("onAdFailedToLoad pid " + pid + " ad name" + adName + " errorcode " + errorCode);
            // 10s后进行retry操作
            ThreadHelper.getInstance().runInWorkThreadDelay(new Runnable() {
                @Override
                public void run() {
                    startLoad(adAdpter, this);
                }
            }, 10);
        }
    }

    /**
     * retry操作
     *
     * @param adAdpter
     * @param runnable
     */
    private void startLoad(BaseAdAdpter adAdpter, Runnable runnable) {
        LogDevHelper.logLoadI("retry load  " + adAdpter.mAdEntity.toString());
        if (adAdpter.mAdEntity.getRetryNum() == adAdpter.mAdEntity.getMaxRetry()) {
            LogDevHelper.logLoadI("到达max rety,不进行load  " + adAdpter.mAdEntity.getNetWorkKey());
            ThreadHelper.getInstance().removeInWorkThread(runnable);
            return;
        }
        adAdpter.mAdEntity.countRetryNum();
        mAdRequestQueue.addRequest(new AdsRequest(adAdpter, RoyAdType.REWARDVIDEO, new InnerCallBack(adAdpter)));
        mAdRequestQueue.start();
    }
}
