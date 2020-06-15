package com.avidly.roy.mediation.callback;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.callback
 * @ClassName: RoyAdDisplayCallBack
 * @Description: RoyAdDisplayCallBack
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:37
 */

public interface RoyAdOuterLoadCallBack {
    public void onAdLoaded();

    public void onAdFailedToLoad(String errorCode);
}
