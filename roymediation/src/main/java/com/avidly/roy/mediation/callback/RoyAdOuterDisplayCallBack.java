package com.avidly.roy.mediation.callback;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.callback
 * @ClassName: RoyAdDisplayCallBack
 * @Description: RoyAdDisplayCallBack
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:37
 */

public interface RoyAdOuterDisplayCallBack {
    public void onAdShow(String placementId);

    public void onAdShowError(String placementId, String msg);

    public void onAdClick(String placementId);

    public void onAdClose(String placementId);

    public void onAdReward(String placementId);

    public void onAdNOReward(String placementId, String error);
}
