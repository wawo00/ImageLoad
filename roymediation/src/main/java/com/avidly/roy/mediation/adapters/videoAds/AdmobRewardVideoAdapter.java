package com.avidly.roy.mediation.adapters.videoAds;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.avidly.roy.mediation.adapters.base.BaseRewardVideoAdapter;
import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.utils.LogHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.adapters
 * @ClassName: AdmobRewardVideoAdapter
 * @Description: AdmobRewardVideoAdapter
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:51
 */

public class AdmobRewardVideoAdapter extends BaseRewardVideoAdapter {
    private RoyAdDisplayCallBack mDisplayCallBack;
    private RoyAdLoadCallBack mLoadCallback;
    private Activity mActivity;
    private AtomicBoolean isInited = new AtomicBoolean(false);
    private RewardedAd mRewardedAd;

    private AdmobRewardVideoAdapter(Activity activity) {
        mActivity = activity;
    }

    public static AdmobRewardVideoAdapter newInstance(Activity activity) {
        if (activity == null) {
            return null;
        }
        return new AdmobRewardVideoAdapter(activity);
    }

    @Override
    public void load(RoyAdLoadCallBack loadCallBack) {
        mLoadCallback = loadCallBack;
        if (isInited.get() != true) {
            MobileAds.initialize(mActivity, new OnInitializationCompleteListener() {
                @Override
                public void onInitializationComplete(InitializationStatus initializationStatus) {
                    isInited.set(true);
                }
            });
        } else {
            //直接进行load
            mRewardedAd = new RewardedAd(mActivity, getAdEntity().getNetWorkKey());
            mRewardedAd.loadAd(new AdRequest.Builder().build(), adLoadCallback);
        }
    }


    @Override
    public String getNetWorksName() {
        return RoyNetWorks.ADMOB.getName();
    }

    @Override
    public void show(RoyAdDisplayCallBack displayCallBack) {
        mDisplayCallBack = displayCallBack;
        if (mRewardedAd.isLoaded()) {
            mRewardedAd.show(mActivity, adCallback);
        } else {
            LogHelper.logi(getAdEntity().getNetWorkKey() + " is not ready");
        }
    }

    @Override
    public void release() {
        mRewardedAd = null;
    }

    @Override
    public boolean isReady() {
        return mRewardedAd.isLoaded();
    }

    RewardedAdLoadCallback adLoadCallback = new RewardedAdLoadCallback() {
        @Override
        public void onRewardedAdLoaded() {
            mLoadCallback.onAdLoaded(getAdEntity().getNetWorkKey(), getNetWorksName());
            // Ad successfully loaded.
        }

        @Override
        public void onRewardedAdFailedToLoad(int errorCode) {
            // Ad failed to load.
            mLoadCallback.onAdLoaded(getAdEntity().getNetWorkKey(), getNetWorksName());
        }
    };


    RewardedAdCallback adCallback = new RewardedAdCallback() {
        @Override
        public void onRewardedAdOpened() {
            // Ad opened.
            mDisplayCallBack.onAdShow(getAdEntity().getNetWorkKey(), getNetWorksName());
        }

        @Override
        public void onRewardedAdClosed() {
            // Ad closed.
            mDisplayCallBack.onAdClose(getAdEntity().getNetWorkKey(), getNetWorksName());
        }

        @Override
        public void onUserEarnedReward(@NonNull RewardItem reward) {
            // User earned reward.
            mDisplayCallBack.onAdReward(getAdEntity().getNetWorkKey(), getNetWorksName());
        }

        @Override
        public void onRewardedAdFailedToShow(int errorCode) {
            // Ad failed to display.
            mDisplayCallBack.onAdShowError(getAdEntity().getNetWorkKey(), getNetWorksName(), errorCode + "");
        }
    };
}
