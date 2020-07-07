package com.avidly.roy.mediation.request;

import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.callback.RoyAdLoadCallBack;
import com.avidly.roy.mediation.constant.RoyAdType;
import com.vungle.warren.model.Advertisement;

import java.util.UUID;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.request
 * @ClassName: AdsRequest
 * @Description: AdsRequest
 * @Author: Roy
 * @CreateDate: 2020/6/15 11:09
 */

public class AdsRequest implements Comparable {
    private BaseAdAdpter mBaseAdAdpter;
    private String requestId;
    private RoyAdType mType;
    private boolean isCancel;
    private RoyAdLoadCallBack mRoyAdLoadCallBack;

    public AdsRequest(BaseAdAdpter baseAdAdpter, RoyAdType type, RoyAdLoadCallBack loadCallBack) {
        mBaseAdAdpter = baseAdAdpter;
        this.requestId = UUID.randomUUID().toString();
        mType = type;
        mRoyAdLoadCallBack = loadCallBack;
    }

    public BaseAdAdpter getBaseAdAdpter() {
        return mBaseAdAdpter;
    }

    public void setBaseAdAdpter(BaseAdAdpter baseAdAdpter) {
        mBaseAdAdpter = baseAdAdpter;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public RoyAdType getType() {
        return mType;
    }

    public void setType(RoyAdType type) {
        mType = type;
    }

    public boolean isCancel() {
        return isCancel;
    }

    public void setCancel(boolean cancel) {
        isCancel = cancel;
    }

    public RoyAdLoadCallBack getRoyAdLoadCallBack() {
        return mRoyAdLoadCallBack;
    }

    public void setRoyAdLoadCallBack(RoyAdLoadCallBack royAdLoadCallBack) {
        mRoyAdLoadCallBack = royAdLoadCallBack;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof AdsRequest) {
            AdsRequest other = (AdsRequest) o;
            if (this.requestId.equals(other.getRequestId())) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
}

