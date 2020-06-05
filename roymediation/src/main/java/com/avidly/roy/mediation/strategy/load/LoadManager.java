package com.avidly.roy.mediation.strategy.load;

import android.app.Activity;
import android.widget.BaseAdapter;

import com.avidly.roy.mediation.adapters.base.BaseAdAdpter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
    private WeakReference<Activity> mAcRefer;
    private LoadManager() {
    }

    public static LoadManager getInstance() {
        return SingletonHolder.sInstance;
    }

    private static class SingletonHolder {
        private static final LoadManager sInstance = new LoadManager();
    }

    private void getLoader(LoadStrategy loadStrategy) {
        if (loadStrategy == LoadStrategy.Sequence) {
            mLoader = new SequenceLoadr();
        } else {
            mLoader = new ParallelLoadr();
        }
    }

    private void loadAds(Activity activity) {
        mAcRefer = new WeakReference<Activity>(activity);
        if (mLoader != null) {
            mLoader.loadAds(activity);
        }
    }

    public static List<BaseAdAdpter> getLoadeds() {
        return (List<BaseAdAdpter>) mLoader.getLoadeds().clone();
    }
}
