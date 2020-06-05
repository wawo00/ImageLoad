package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.load
 * @ClassName: ParallelLoadr
 * @Description: ParallelLoadr
 * @Author: Roy
 * @CreateDate: 2020/6/3 16:37
 */

public class ParallelLoadr extends BaseLoader {
    @Override
    public LoadStrategy getLoadStrategy() {
        return null;
    }

    @Override
    public void loadAds(Activity activity) {

    }

    @Override
    public void initWillLoads() {

    }
}
