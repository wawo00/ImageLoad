package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;

import com.avidly.roy.mediation.RoyAdsApi;
import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.callback.RoyAdOuterDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdOuterLoadCallBack;
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

    public void showAd(final String placementId, final RoyAdOuterDisplayCallBack displayCallBack) {
        if (!getLoadeds().isEmpty()) {
            for (BaseAdAdpter adAdpter : getLoadeds()) {
                if (adAdpter.isReady()) {
                    showAdapter = adAdpter;
                    adAdpter.show(new DisplayCallBack(displayCallBack, placementId));
                    break;
                }else{
                    LogHelper.logw("no ready ads in "+adAdpter.mAdEntity.toString());
                }
            }
        } else {
            LogHelper.logw("no ready ads in loadmanager");
        }
    }

    private class DisplayCallBack implements RoyAdDisplayCallBack {
        private RoyAdOuterDisplayCallBack displayCallBack;
        private String placementId;

        private DisplayCallBack(RoyAdOuterDisplayCallBack displayCallBack, String placementId) {
            this.displayCallBack = displayCallBack;
            this.placementId = placementId;
        }

        @Override
        public void onAdShow(String pid, String adName) {
            displayCallBack.onAdShow(placementId);
        }

        @Override
        public void onAdShowError(String pid, String adName, String msg) {
            displayCallBack.onAdShowError(placementId, msg);
        }

        @Override
        public void onAdClick(String pid, String adName) {
            displayCallBack.onAdClick(placementId);
        }

        @Override
        public void onAdClose(String pid, String adName) {
            displayCallBack.onAdClose(placementId);
            mLoader.removeFromLoaded(showAdapter);
        }

        @Override
        public void onAdReward(String pid, String adName) {
            displayCallBack.onAdReward(pid);
        }

        @Override
        public void onAdNOReward(String pid, String adName, String error) {
            displayCallBack.onAdNOReward(pid, error);
        }
    }

    private static class SingletonHolder {
        private static final LoadManager sInstance = new LoadManager();
    }

    private void getLoader(LoadStrategy loadStrategy) {
        if (loadStrategy == LoadStrategy.Sequence) {
            mLoader = new SequenceLoader();
        } else {
            mLoader = new ParallelLoadr();
        }
    }

    public void startLoad() {
        getLoader(LoadStrategy.Sequence);
        mLoader.startLoad();
    }

    public List<BaseAdAdpter> getLoadeds() {
        LogHelper.logi("get loadeds is "+mLoader.getLoadeds().toString());
        return mLoader.getLoadeds();
    }

    public boolean hasReadyAds() {
        LogHelper.logi("loadeds is " + mLoader.getLoadeds().toString());
        return !mLoader.getLoadeds().isEmpty();
    }

    public void reload(BaseAdAdpter adAdpter){

    }

}
