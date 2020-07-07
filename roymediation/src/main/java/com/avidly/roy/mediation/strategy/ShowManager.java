package com.avidly.roy.mediation.strategy;

import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.callback.RoyAdDisplayCallBack;
import com.avidly.roy.mediation.callback.RoyAdOuterDisplayCallBack;
import com.avidly.roy.mediation.strategy.load.LoadManager;
import com.avidly.roy.mediation.utils.LogDevHelper;
import com.avidly.roy.mediation.utils.LogHelper;

import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy
 * @ClassName: ShowManager
 * @Description: ShowManager
 * @Author: Roy
 * @CreateDate: 2020/6/15 17:36
 */

public class ShowManager {
    private static ShowManager sInstance=new ShowManager();
    // 当前正在展示的adapter
    BaseAdAdpter showAdapter;
    private ShowManager(){

    }

    public static ShowManager getInstance(){
        return sInstance;
    }

    public void showAd(final String placementId, final RoyAdOuterDisplayCallBack displayCallBack) {
        if (!getLoadeds().isEmpty()) {
//            for (BaseAdAdpter adAdpter : getLoadeds()) {
//                if (adAdpter.isReady()) {
//                    showAdapter = adAdpter;
//                    adAdpter.show(new DisplayCallBack(displayCallBack, placementId));
//                    break;
//                } else {
//                    LogHelper.logw("no ready ads in " + adAdpter.mAdEntity.toString());
//                }
//            }
            LogDevHelper.logLoadI("loadeds is "+getLoadeds().toString());
            for (int i = 0; i < getLoadeds().size(); i++) {
               BaseAdAdpter adAdpter=getLoadeds().get(i);
                if (adAdpter.isReady()) {
                    showAdapter = adAdpter;
                    adAdpter.show(new DisplayCallBack(displayCallBack, placementId));
                    break;
                } else {
                    LogHelper.logw("no ready ads in " + adAdpter.mAdEntity.toString());
                }
            }
        } else {
            LogHelper.logw("no ready ads in loadmanager");
        }
    }

    private List<BaseAdAdpter> getLoadeds() {
        return LoadManager.getInstance().getLoadeds();
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
            LoadManager.getInstance().removeFromLoaded(showAdapter);
            LoadManager.getInstance().startLoad();
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
}
