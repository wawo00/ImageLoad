package com.avidly.roy.mediation.adapters.base;

import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.RoyAdType;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.adapters.base
 * @ClassName: IAdAdapter
 * @Description: IAdAdapter
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:34
 */

public interface IAdAdapter {
    public void load(RoyAdLoadCallBack loadCallBack);

    public RoyAdType getType();

    public String getNetWorksName();

    public void show(RoyAdDisplayCallBack displayCallBack);

    public void release();
    public boolean isReady();
}
