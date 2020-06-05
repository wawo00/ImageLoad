package com.avidly.roy.mediation.adapters.base;

import com.avidly.roy.mediation.entity.AdEntity;

import java.util.UUID;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.adapters
 * @ClassName: RewardVideoAdapter
 * @Description: RewardVideoAdapter
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:29
 */

public abstract class BaseAdAdpter implements IAdAdapter {

    public AdEntity mAdEntity;

    protected AdEntity getAdEntity() {
        return mAdEntity;
    }

    protected void setAdEntity(AdEntity adEntity) {
        mAdEntity = adEntity;
    }

    /**
     * 获得uuid
     *
     * @return
     */
    protected String getRequestId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }


}
