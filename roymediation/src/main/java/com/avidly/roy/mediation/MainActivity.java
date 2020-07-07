package com.avidly.roy.mediation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.avidly.roy.mediation.callback.RoyAdOuterDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdOuterLoadCallBack;
import com.avidly.roy.mediation.utils.LogHelper;
import com.avidly.roy.mediation.wrapper.RoyRewardVideo;

public class MainActivity extends Activity {
    RoyRewardVideo mRoyRewardVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRoyMediation();
    }

    private void initRoyMediation() {
        RoyAdsApi.init(this);
        mRoyRewardVideo = new RoyRewardVideo();
        mRoyRewardVideo.setLoadCallBack(new RoyAdOuterLoadCallBack() {
            @Override
            public void onAdLoaded() {
                LogHelper.logi("outer onAdLoaded");
            }

            @Override
            public void onAdFailedToLoad(String errorCode) {
                LogHelper.logi("outer onAdFailedToLoad " + errorCode);
            }
        });

    }


    public void showVideos(View view) {
        if (mRoyRewardVideo.isReady()) {
            mRoyRewardVideo.show("reward_video", new RoyAdOuterDisplayCallBack() {
                @Override
                public void onAdShow(String placementId) {
                    LogHelper.logi("outer onAdShow " + placementId);
                }

                @Override
                public void onAdShowError(String placementId, String msg) {
                    LogHelper.logi("outer onAdShowError " + placementId + " error " + msg);
                }

                @Override
                public void onAdClick(String placementId) {
                    LogHelper.logi("outer onAdClick " + placementId);
                }

                @Override
                public void onAdClose(String placementId) {
                    LogHelper.logi("outer onAdClose " + placementId);
                }

                @Override
                public void onAdReward(String placementId) {
                    LogHelper.logi("outer onAdReward " + placementId);
                }

                @Override
                public void onAdNOReward(String placementId, String msg) {
                    LogHelper.logi("outer onAdNOReward " + placementId + " error " + msg);
                }
            });
        }else{
            LogHelper.logw("no ads");
        }
    }
}
