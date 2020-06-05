package com.avidly.roy.mediation.callback;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.callback
 * @ClassName: RoyAdDisplayCallBack
 * @Description: RoyAdDisplayCallBack
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:37
 */

public interface RoyAdLoadCallBack {
    public void onAdLoaded(String pid, String adName);

    public void onAdFailedToLoad(String pid, String adName, String errorCode);
}
