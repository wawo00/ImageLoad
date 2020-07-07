package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;

import com.avidly.roy.mediation.RoyAdsApi;
import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.callback.RoyAdOuterDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdOuterLoadCallBack;
import com.avidly.roy.mediation.constant.ConstantValue;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.entity.AdEntity;
import com.avidly.roy.mediation.utils.LogHelper;

import java.lang.ref.WeakReference;
import java.nio.file.attribute.DosFileAttributes;
import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.load
 * @ClassName: LoadManager
 * @Description: 用于管理loadStrategy
 * @Author: Roy
 * @CreateDate: 2020/6/5 10:54
 */

public class LoadManager {
    private SingletonHolder mHolder;
    private static BaseLoader mLoader;
    private static BaseAdAdpter showAdapter;

    private LoadManager() {
    }

    public static LoadManager getInstance() {
        return SingletonHolder.sInstance;
    }

    public void setLoadCallBack(RoyAdOuterLoadCallBack loadCallBack) {
        mLoader.addCpLoadCallBack(loadCallBack);
    }

    private static class SingletonHolder {
        private static final LoadManager sInstance = new LoadManager();
    }

    private BaseLoader getLoader(LoadStrategy loadStrategy) {
        if (loadStrategy == LoadStrategy.Sequence) {
            mLoader = new SequenceLoader();
        } else if (loadStrategy == LoadStrategy.Paralle) {
            mLoader = new ParallelLoadr();
        } else {
            mLoader = new RequestLoader();
        }
        return mLoader;
    }

    public void startLoad() {
        if (mLoader == null) {
            mLoader = getLoader(LoadStrategy.Request);
        }
        mLoader.startLoad();

    }

    public List<BaseAdAdpter> getLoadeds() {
        LogHelper.logi("get loadeds is " + mLoader.getLoadeds().toString());
        return mLoader.getLoadeds();
    }

    public boolean hasReadyAds() {
        LogHelper.logi("loadeds is " + mLoader.getLoadeds().toString());
        return !mLoader.getLoadeds().isEmpty();
    }

    public void removeFromLoaded(BaseAdAdpter adAdpter) {
        if (mLoader.getLoadeds().contains(adAdpter)) {
            mLoader.getLoadeds().remove(adAdpter);
        } else {
            return;
        }
    }

}
