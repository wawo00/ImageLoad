package com.avidly.roy.mediation.strategy.show;

import android.widget.BaseAdapter;

import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;
import com.avidly.roy.mediation.strategy.load.LoadManager;
import com.avidly.roy.mediation.utils.ThreadHelper;

import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.strategy.show
 * @ClassName: SequenceDisplayer
 * @Description: 按照加载顺序展示
 * @Author: Roy
 * @CreateDate: 2020/6/3 16:37
 */

public class SequenceDisplayer extends BaseDisplayer {

    @Override
    public DisplayStrategy getDisplayeStrategy() {
        return DisplayStrategy.Sequenece;
    }

    @Override
    public void display() {
        final List<BaseAdAdpter> loadAds = LoadManager.getInstance().getLoadeds();
        ThreadHelper.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                for (BaseAdAdpter adAdpter : loadAds) {
                    if (adAdpter.isReady()) {
                        adAdpter.show(null);
                        break;
                    }
                }
            }
        });
    }
}
