package com.avidly.roy.mediation.callback;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.callback
 * @ClassName: RoyAdDisplayCallBack
 * @Description: RoyAdDisplayCallBack
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:37
 */

public interface RoyAdDisplayCallBack {
    public void onAdShow(String pid,String adName);

    public void onAdShowError(String pid,String adName,String msg);

    public void onAdClick(String pid,String adName);

    public void onAdClose(String pid,String adName);

    public void onAdReward(String pid,String adName);

    public void onAdNOReward(String pid,String adName,String error);
}
