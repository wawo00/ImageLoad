package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;

import com.avidly.roy.mediation.adapters.RewardVideoAdapterFactory;
import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.adapters.base.BaseRewardVideoAdapter;
import com.avidly.roy.mediation.callback.RoyAdOuterLoadCallBack;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.entity.AdEntity;
import com.avidly.roy.mediation.utils.LogHelper;
import com.avidly.roy.mediation.utils.ThreadHelper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy
 * @ClassName: BaseLoader
 * @Description: BaseLoader
 * @Author: Roy
 * @CreateDate: 2020/6/3 16:39
 */

public abstract class BaseLoader {

    //默认包含三个队列：准备加载，加载中，加载失败
    private CopyOnWriteArrayList<AdEntity> willLoads = new CopyOnWriteArrayList<AdEntity>();
    private CopyOnWriteArrayList<BaseAdAdpter> loadings = new CopyOnWriteArrayList<BaseAdAdpter>();
    private CopyOnWriteArrayList<BaseAdAdpter> loadeds = new CopyOnWriteArrayList<BaseAdAdpter>();
    private CopyOnWriteArrayList<RoyAdOuterLoadCallBack> mCpLoadCallBacks = new CopyOnWriteArrayList<>();
    private Runnable mLoadFailRunnable;
    private AtomicBoolean isLoaded = new AtomicBoolean(false);

    protected synchronized void notifyCpLoadCallback() {
        for (RoyAdOuterLoadCallBack callBack : mCpLoadCallBacks) {
            callBack.onAdLoaded();
            cancelFailLoad2cp();
        }
    }

    public void cancelFailLoad2cp() {
        if (mLoadFailRunnable != null) {
            ThreadHelper.getInstance().removeOnMainThread(mLoadFailRunnable);
        }
    }

    protected void addCpLoadCallBack(RoyAdOuterLoadCallBack cpLoadCallBack) {
        mCpLoadCallBacks.add(cpLoadCallBack);
        sendLoadFailtoCpRunnable("no ads");
    }

    protected CopyOnWriteArrayList<AdEntity> getWillLoads() {
        return willLoads;
    }


    protected List<BaseAdAdpter> getLoadings() {
        return loadings;
    }

    public abstract LoadStrategy getLoadStrategy();

    protected CopyOnWriteArrayList<BaseAdAdpter> getLoadeds() {
        return loadeds;
    }

    protected void setLoadeds(CopyOnWriteArrayList<BaseAdAdpter> loadeds) {
        this.loadeds = loadeds;
    }


    public void addInWillLoadList(Activity activity, AdEntity... entities) {
        willLoads.addAll(Arrays.asList(entities));
        for (AdEntity adEntity : entities) {
            BaseAdAdpter adAdpter = getAdAdapter(adEntity, activity);
            addInLoadingList(adAdpter);
        }
    }

    public void addInLoadingList(BaseAdAdpter adAdpter) {
        loadings.add(adAdpter);
    }

    public void removeFromLoadingList(BaseAdAdpter baseAdAdpter) {
        if (loadings.contains(baseAdAdpter)) {
            loadings.remove(baseAdAdpter);
        }
    }

    public void addinLoadedList(BaseAdAdpter baseAdAdpter) {
        if (loadeds.contains(baseAdAdpter)){
            return;
        }
        baseAdAdpter.resetRetryTimes();
        loadeds.add( loadeds.isEmpty()?0:loadeds.size(),baseAdAdpter);
    }

    public void removeFromLoaded(BaseAdAdpter baseAdAdpter) {
        if (loadeds.contains(baseAdAdpter)) {
            loadeds.remove(baseAdAdpter);
            addInLoadingList(baseAdAdpter);
        } else {
            LogHelper.logi("no this adadapter ");
        }
    }

    public BaseAdAdpter getAdAdapter(AdEntity adEntity, Activity activity) {
        BaseRewardVideoAdapter adAdpter = null;
        if (adEntity.getNetWorkName().equals(RoyNetWorks.ADMOB.getName())) {
            adAdpter = RewardVideoAdapterFactory.getAdapter(RoyNetWorks.ADMOB, activity);
        } else if (adEntity.getNetWorkName().equals(RoyNetWorks.Unity.getName())) {
            adAdpter = RewardVideoAdapterFactory.getAdapter(RoyNetWorks.Unity, activity);
        } else if (adEntity.getNetWorkName().equals(RoyNetWorks.APPLOVIN.getName())) {
            adAdpter = RewardVideoAdapterFactory.getAdapter(RoyNetWorks.APPLOVIN, activity);
        }
        adAdpter.setAdEntity(adEntity);
        return adAdpter;
    }

    public abstract void startLoad();

    public abstract void startLoad(BaseAdAdpter adAdpter);

    public void sendLoadFailtoCpRunnable(final String errorMsg) {
        mLoadFailRunnable = new LoadFailRunnable(errorMsg);
        ThreadHelper.runOnMainThreadDelay(mLoadFailRunnable, 60);
    }

    class LoadFailRunnable implements Runnable {
        String errorMsg;

        public LoadFailRunnable(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        @Override
        public void run() {
            for (RoyAdOuterLoadCallBack callBack : mCpLoadCallBacks) {
                callBack.onAdFailedToLoad(errorMsg);
            }
        }
    }
}
