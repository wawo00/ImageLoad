package com.openup.imageloader.load;

import java.util.HashMap;
import java.util.Map;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.load
 * @ClassName: LoadManager
 * @Description: 其实更像是个工厂类
 * @Author: Roy
 * @CreateDate: 2020/3/27 14:17
 */

public class LoadManager {
    public final static String HTTP = "http";
    public static final String HTTPS = "https";
    public static final String FILE = "file";
    private Map<String, Loader> mLoaderMap = new HashMap<>();
    private static LoadManager sInstance;
    private Loader mLoader = new NoLoader();

    private LoadManager() {
        registeInMap(HTTP, new NetLoader());
        registeInMap(HTTPS, new NetLoader());
        registeInMap(FILE, new FileLoader());

    }

    public static LoadManager getInstance() {
        if (sInstance == null) {
            synchronized (LoadManager.class) {
                if (sInstance == null) {
                    sInstance = new LoadManager();
                }
            }
        }
        return sInstance;
    }

    public Loader getLoader(String schema) {
        if (mLoaderMap.containsKey(schema)) {
            return mLoaderMap.get(schema);
        }
        return mLoader;
    }

    public synchronized void registeInMap(String key, Loader value) {
        mLoaderMap.put(key, value);
    }

}
