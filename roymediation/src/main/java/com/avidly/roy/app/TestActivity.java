package com.avidly.roy.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.avidly.roy.mediation.R;
import com.avidly.roy.mediation.constant.ConstantValue;
import com.avidly.roy.mediation.utils.LogHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdCallback;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class TestActivity extends AppCompatActivity {
    private RewardedAd mRewardedAd1, mRewardedAd2;
    private static String TAG = "roy_test";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        MobileAds.initialize(this, ConstantValue.Admob_RW_ID_1);
        loadads();
    }

    private void loadads() {
        mRewardedAd1 = new RewardedAd(this, ConstantValue.Admob_RW_ID_1);
        mRewardedAd2 = new RewardedAd(this, ConstantValue.Admob_RW_ID_2);
        mRewardedAd1.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                Log.i(TAG, "onRewardedAdLoaded: rw1");
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                Log.i(TAG, "onRewardedAdFailedToLoad: rw1 " + i);

            }
        });
        mRewardedAd2.loadAd(new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
            @Override
            public void onRewardedAdLoaded() {
                Log.i(TAG, "onRewardedAdLoaded: rw2");
            }

            @Override
            public void onRewardedAdFailedToLoad(int i) {
                Log.i(TAG, "onRewardedAdFailedToLoad: rw2 " + i);
            }
        });
    }

    public void showRw1(View view) {
        mRewardedAd1.show(this, new MyShowCallBack("pid1"));
    }

    public void showRw2(View view) {
        mRewardedAd2.show(this, new MyShowCallBack("pid2"));
    }

    class MyShowCallBack extends RewardedAdCallback {

        private String pid;

        MyShowCallBack(String pid) {
            this.pid = pid;
        }

        @Override
        public void onRewardedAdOpened() {
            Log.i(TAG, "onRewardedAdOpened: "+pid);
        }

        @Override
        public void onRewardedAdClosed() {
            Log.i(TAG, "onRewardedAdClosed: "+pid);
        }


        @Override
        public void onRewardedAdFailedToShow(int i) {
            Log.i(TAG, "onRewardedAdFailedToShow: "+pid+" msg "+i);

        }

        @Override
        public void onUserEarnedReward(@NonNull RewardItem rewardItem) {

        }
    }
}
