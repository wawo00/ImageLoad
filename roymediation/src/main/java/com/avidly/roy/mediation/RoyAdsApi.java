package com.avidly.roy.mediation;

import android.app.Activity;

import com.avidly.roy.mediation.strategy.load.LoadManager;

import java.lang.ref.WeakReference;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation
 * @ClassName: RoyAdsApi
 * @Description: RoyAdsApi
 * @Author: Roy
 * @CreateDate: 2020/6/5 14:49
 */

public class RoyAdsApi {
    private static WeakReference<Activity> sAcRefer;

    public static void init(Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException(" no activity");
        }
        sAcRefer = new WeakReference<>(activity);
        LoadManager.getInstance().startLoad();
    }

    public static Activity getActivity() {
        return sAcRefer.get();
    }

    public static void setAcRefer(WeakReference<Activity> acRefer) {
        sAcRefer = acRefer;
    }
}
