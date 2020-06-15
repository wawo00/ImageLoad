package com.avidly.roy.mediation.adapters.videoAds;

import android.app.Activity;

import com.applovin.adview.AppLovinIncentivizedInterstitial;
import com.applovin.sdk.AppLovinAd;
import com.applovin.sdk.AppLovinAdClickListener;
import com.applovin.sdk.AppLovinAdDisplayListener;
import com.applovin.sdk.AppLovinAdLoadListener;
import com.applovin.sdk.AppLovinAdRewardListener;
import com.applovin.sdk.AppLovinErrorCodes;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.applovin.sdk.AppLovinSdkSettings;
import com.avidly.roy.mediation.adapters.base.BaseRewardVideoAdapter;
import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.utils.LogHelper;
import com.avidly.roy.mediation.utils.ThreadHelper;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.adapters
 * @ClassName: UnityRewardVideoAdapter
 * @Description: UnityRewardVideoAdapter
 * @Author: Roy
 * @CreateDate: 2020/6/2 19:02
 */

public class ApplovinRewardVideoAdapter extends BaseRewardVideoAdapter {
    private Activity mActivity;
    private AppLovinIncentivizedInterstitial interAds;

    public ApplovinRewardVideoAdapter(Activity activity) {
        mActivity = activity;
    }

    public static ApplovinRewardVideoAdapter getNewInstance(Activity mActivity) {
        return new ApplovinRewardVideoAdapter(mActivity);
    }


    @Override
    public void load(RoyAdLoadCallBack loadCallBack) {
        super.load(loadCallBack);
        AppLovinSdk.getInstance(getAdEntity().getNetWorkKey(), new AppLovinSdkSettings(), mActivity);
        interAds = AppLovinIncentivizedInterstitial.create(mActivity);
        interAds.preload(new AppLovinAdLoadListener() {
            @Override
            public void adReceived(AppLovinAd appLovinAd) {
                mLoadCallBack.onAdLoaded(getAdEntity().getNetWorkKey(), getNetWorksName());
            }

            @Override
            public void failedToReceiveAd(int errorCode) {
                mLoadCallBack.onAdFailedToLoad(getAdEntity().getNetWorkKey(), getNetWorksName(), errorCode + "");
            }
        });
    }


    @Override
    public String getNetWorksName() {
        return RoyNetWorks.APPLOVIN.getName();
    }


    @Override
    public void show(RoyAdDisplayCallBack displayCallBack) {
        super.show(displayCallBack);
        ThreadHelper.getInstance().runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (isReady()) {
                    interAds.show(mActivity, adRewardListener, null, adDisplayListener, adClickListener);
                } else {
                    LogHelper.logi(getNetWorksName() + " key " + getAdEntity().getNetWorkKey() + "is not ready");
                }
            }
        });

    }

    @Override
    public void release() {
        interAds = null;
    }

    @Override
    public boolean isReady() {
        return interAds.isAdReadyToDisplay();
    }

    // Reward Listener
    AppLovinAdRewardListener adRewardListener = new AppLovinAdRewardListener() {
        @Override
        public void userRewardVerified(AppLovinAd appLovinAd, Map map) {
            // AppLovin servers validated the reward. Refresh user balance from your server.  We will also pass the number of coins
            // awarded and the name of the currency.  However, ideally, you should verify this with your server before granting it.

            // i.e. - "Coins", "Gold", whatever you set in the dashboard.
            String currencyName = (String) map.get("currency");

            // For example, "5" or "5.00" if you've specified an amount in the UI.
            String amountGivenString = (String) map.get("amount");

            getDisplayCallBack().onAdReward(getAdEntity().getNetWorkKey(), getNetWorksName());
            LogHelper.logi("Rewarded " + amountGivenString + " " + currencyName);

            // By default we'll show a alert informing your user of the currency & amount earned.
            // If you don't want this, you can turn it off in the Manage Apps UI.
        }

        @Override
        public void userOverQuota(AppLovinAd appLovinAd, Map map) {
            // Your user has already earned the max amount you allowed for the day at this point, so
            // don't give them any more money. By default we'll show them a alert explaining this,
            // though you can change that from the AppLovin dashboard.
            getDisplayCallBack().onAdNOReward(getAdEntity().getNetWorkKey(), getNetWorksName(), "limited");
            LogHelper.logi("Reward validation request exceeded quota with response: " + map);
        }

        @Override
        public void userRewardRejected(AppLovinAd appLovinAd, Map map) {
            // Your user couldn't be granted a reward for this view. This could happen if you've blacklisted
            // them, for example. Don't grant them any currency. By default we'll show them an alert explaining this,
            // though you can change that from the AppLovin dashboard.
            getDisplayCallBack().onAdNOReward(getAdEntity().getNetWorkKey(), getNetWorksName(), "blacklisted");
            LogHelper.logi("Reward validation request was rejected with response: " + map);
        }

        @Override
        public void validationRequestFailed(AppLovinAd appLovinAd, int responseCode) {
            String errorStr = "";
            if (responseCode == AppLovinErrorCodes.INCENTIVIZED_USER_CLOSED_VIDEO) {
                // Your user exited the video prematurely. It's up to you if you'd still like to grant
                // a reward in this case. Most developers choose not to. Note that this case can occur
                // after a reward was initially granted (since reward validation happens as soon as a
                // video is launched).
                errorStr = "INCENTIVIZED_USER_CLOSED_VIDEO";
            } else if (responseCode == AppLovinErrorCodes.INCENTIVIZED_SERVER_TIMEOUT || responseCode == AppLovinErrorCodes.INCENTIVIZED_UNKNOWN_SERVER_ERROR) {
                // Some server issue happened here. Don't grant a reward. By default we'll show the user
                // a alert telling them to try again later, but you can change this in the
                // AppLovin dashboard.
                errorStr = "INCENTIVIZED_SERVER_TIMEOUT or INCENTIVIZED_UNKNOWN_SERVER_ERROR";
            } else if (responseCode == AppLovinErrorCodes.INCENTIVIZED_NO_AD_PRELOADED) {
                // Indicates that the developer called for a rewarded video before one was available.
                // Note: This code is only possible when working with rewarded videos.
                errorStr = "INCENTIVIZED_NO_AD_PRELOADED";
            }
            getDisplayCallBack().onAdNOReward(getAdEntity().getNetWorkKey(), getNetWorksName(), errorStr);
//            log("Reward validation request failed with error code: " + responseCode);
        }

        @Override
        public void userDeclinedToViewAd(AppLovinAd appLovinAd) {
            // This method will be invoked if the user selected "no" when asked if they want to view an ad.
            // If you've disabled the pre-video prompt in the "Manage Apps" UI on our website, then this method won't be called.
            getDisplayCallBack().onAdShowError(getAdEntity().getNetWorkKey(), getNetWorksName(), "userDeclined");
        }
    };


    // Ad Dispaly Listener
    AppLovinAdDisplayListener adDisplayListener = new AppLovinAdDisplayListener() {
        @Override
        public void adDisplayed(AppLovinAd appLovinAd) {
            getDisplayCallBack().onAdShow(getAdEntity().getNetWorkKey(), getNetWorksName());
        }

        @Override
        public void adHidden(AppLovinAd appLovinAd) {
            getDisplayCallBack().onAdClose(getAdEntity().getNetWorkKey(), getNetWorksName());
        }
    };

    // Ad Click Listener
    AppLovinAdClickListener adClickListener = new AppLovinAdClickListener() {
        @Override
        public void adClicked(AppLovinAd appLovinAd) {
            getDisplayCallBack().onAdClick(getAdEntity().getNetWorkKey(), getNetWorksName());
        }
    };

}
