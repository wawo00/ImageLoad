package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;

import com.avidly.roy.mediation.adapters.RewardVideoAdapterFactory;
import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.adapters.base.BaseRewardVideoAdapter;
import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.constant.RoyNetWorks;
import com.avidly.roy.mediation.entity.AdEntity;

import java.util.Arrays;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private CopyOnWriteArrayList<AdEntity> loadings = new CopyOnWriteArrayList<AdEntity>();
    private CopyOnWriteArrayList<BaseAdAdpter> loadeds = new CopyOnWriteArrayList<BaseAdAdpter>();

    protected CopyOnWriteArrayList<AdEntity> getWillLoads() {
        return willLoads;
    }

    protected void setWillLoads(CopyOnWriteArrayList<AdEntity> willLoads) {
        this.willLoads = willLoads;
    }

    protected CopyOnWriteArrayList<AdEntity> getLoadings() {
        return loadings;
    }

    protected void setLoadings(CopyOnWriteArrayList<AdEntity> loadings) {
        this.loadings = loadings;
    }

    public abstract LoadStrategy getLoadStrategy();

    protected CopyOnWriteArrayList<BaseAdAdpter> getLoadeds() {
        return loadeds;
    }

    protected void setLoadeds(CopyOnWriteArrayList<BaseAdAdpter> loadeds) {
        this.loadeds = loadeds;
    }

    public abstract void loadAds(Activity activity);

    public abstract void initWillLoads();

    public void addInWillLoadList(AdEntity... entities) {
        willLoads.addAll(Arrays.asList(entities));
    }

    public void addInLoadingList(AdEntity... entities) {
        loadings.addAll(Arrays.asList(entities));
    }

    public void removeFromLoadingList(AdEntity adEntity) {
        if (loadings.contains(adEntity)) {
            loadings.remove(adEntity);
        }
    }

    public void addLoadedList(BaseAdAdpter baseAdAdpter) {
        loadeds.add(baseAdAdpter);
    }

    public void removeFromLoaded(BaseAdAdpter baseAdAdpter) {
        if (loadeds.contains(baseAdAdpter)) {
            loadeds.remove(baseAdAdpter);
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
        return adAdpter;
    }


}
