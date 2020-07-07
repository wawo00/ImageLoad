package com.avidly.roy.mediation.adapters.videoAds;

import android.app.Activity;

import com.avidly.roy.mediation.RoyAdsApi;
import com.avidly.roy.mediation.adapters.base.BaseRewardVideoAdapter;
import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.utils.LogDevHelper;
import com.avidly.roy.mediation.utils.LogHelper;
import com.avidly.roy.mediation.utils.ThreadHelper;
import com.unity3d.ads.UnityAds;
import com.unity3d.ads.mediation.IUnityAdsExtendedListener;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.adapters
 * @ClassName: UnityRewardVideoAdapter
 * @Description: UnityRewardVideoAdapter
 * @Author: Roy
 * @CreateDate: 2020/6/2 19:02
 */

public class UnityRewardVideoAdapter extends BaseRewardVideoAdapter {
    private Activity mActivity;
    private static UnityRewardVideoAdapter sAdapter;

    public UnityRewardVideoAdapter(Activity activity) {
        mActivity = activity;
    }

    public static UnityRewardVideoAdapter getInstance(Activity mActivity) {
//        if (sAdapter == null) {
//            synchronized (UnityRewardVideoAdapter.class) {
//                if (sAdapter == null) {
//                    sAdapter = new UnityRewardVideoAdapter(mActivity);
//                }
//            }
//        }
        return new UnityRewardVideoAdapter(RoyAdsApi.getActivity());
    }

    @Override
    public void load(RoyAdLoadCallBack loadCallBack) {
        super.load(loadCallBack);
        UnityAds.initialize(mActivity, getAdEntity().getNetWorkKey(), mAdsListener, true);

    }


    @Override
    public String getNetWorksName() {
        return RoyNetWorks.Unity.getName();
    }


    @Override
    public void show(RoyAdDisplayCallBack displayCallBack) {
        super.show(displayCallBack);
        ThreadHelper.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (isReady()) {
                    UnityAds.show(mActivity, getAdEntity().getNetWorkPId());
                } else {
                    LogHelper.logi(getNetWorksName() + "_" + getAdEntity().getNetWorkPId() + " is not ready");
                }
            }
        });

    }

    @Override
    public void release() {

    }


    @Override
    public boolean isReady() {
        return UnityAds.isReady(getAdEntity().getNetWorkPId());
    }


    private IUnityAdsExtendedListener mAdsListener = new IUnityAdsExtendedListener() {

        @Override
        public void onUnityAdsReady(String placementId) {
            if (placementId.equals(mAdEntity.getNetWorkPId())){
                getLoadCallBack().onAdLoaded(placementId, getNetWorksName());
            }
        }

        @Override
        public void onUnityAdsError(UnityAds.UnityAdsError unityAdsError, String placementId) {
            if (placementId.equals(mAdEntity.getNetWorkPId())){
                getLoadCallBack().onAdFailedToLoad(placementId, getNetWorksName(), unityAdsError.toString());
            }
        }

        @Override
        public void onUnityAdsStart(String placementId) {
            if (placementId.equals(mAdEntity.getNetWorkPId())){
                LogDevHelper.logShowI("adStart"+mAdEntity.toString());
                getDisplayCallBack().onAdShow(placementId, getNetWorksName());
            }
        }

        @Override
        public void onUnityAdsFinish(String placementId, UnityAds.FinishState finishState) {
            if (placementId.equals(mAdEntity.getNetWorkPId())){
                getDisplayCallBack().onAdClose(placementId, getNetWorksName());
                getDisplayCallBack().onAdReward(placementId, getNetWorksName());
            }
        }

        @Override
        public void onUnityAdsClick(String placementId) {
            if (placementId.equals(mAdEntity.getNetWorkPId())){
                getDisplayCallBack().onAdClick(placementId, getNetWorksName());
            }
        }

        @Override
        public void onUnityAdsPlacementStateChanged(String placementId,
                                                    UnityAds.PlacementState placementState,
                                                    UnityAds.PlacementState placementState1) {
        }
    };

    class InnerShowCallBack implements  RoyAdDisplayCallBack{

        @Override
        public void onAdShow(String pid, String adName) {

        }

        @Override
        public void onAdShowError(String pid, String adName, String msg) {

        }

        @Override
        public void onAdClick(String pid, String adName) {

        }

        @Override
        public void onAdClose(String pid, String adName) {

        }

        @Override
        public void onAdReward(String pid, String adName) {

        }

        @Override
        public void onAdNOReward(String pid, String adName, String error) {

        }
    }
}
