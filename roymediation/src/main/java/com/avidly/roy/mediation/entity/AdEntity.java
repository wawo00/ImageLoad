package com.avidly.roy.mediation.entity;

import androidx.annotation.Nullable;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.entity
 * @ClassName: AdEntity
 * @Description: AdEntity
 * @Author: Roy
 * @CreateDate: 2020/6/3 10:51
 */

public class AdEntity {
    private String netWorkName;
    private String netWorkKey;
    private String netWorkPId; // placementId
    private boolean isReady;

    public AdEntity(String netWorkName, String netWorkKey, String netWorkPId) {
        this.netWorkName = netWorkName;
        this.netWorkKey = netWorkKey;
        this.netWorkPId = netWorkPId;
    }

    public String getNetWorkName() {
        return netWorkName;
    }

    public void setNetWorkName(String netWorkName) {
        this.netWorkName = netWorkName;
    }

    public String getNetWorkKey() {
        return netWorkKey;
    }

    public void setNetWorkKey(String netWorkKey) {
        this.netWorkKey = netWorkKey;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

    public String getNetWorkPId() {
        return netWorkPId;
    }

    public void setNetWorkPId(String netWorkPId) {
        this.netWorkPId = netWorkPId;
    }

    @Override
    public String toString() {
        return "AdEntity{" +
                "netWorkName='" + netWorkName + '\'' +
                ", netWorkKey='" + netWorkKey + '\'' +
                ", netWorkPId='" + netWorkPId + '\'' +
                ", isReady=" + isReady +
                '}';
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof AdEntity)) {
            return false;
        }
        AdEntity otherObj = (AdEntity) obj;
        if (otherObj.getNetWorkKey().equals(netWorkKey) && otherObj.getNetWorkName().equals(netWorkName) && otherObj.getNetWorkPId().equals(netWorkPId)) {
            return true;
        }
        return false;
    }
}
