package com.avidly.roy.mediation.utils;

import android.drm.DrmStore;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.utils
 * @ClassName: ThreadHelper
 * @Description: ThreadHelper
 * @Author: Roy
 * @CreateDate: 2020/6/3 16:47
 */

public class ThreadHelper {
    private static ThreadHelper sInstance = new ThreadHelper();
    // 包含,广告加载线程，主线程，网络请求线程
    private static ExecutorService mLoadExecutor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static ExecutorService mSequenceLoadExecutor = Executors.newSingleThreadExecutor();
    private static Handler uiHandler;
    private static HandlerThread workThread=new HandlerThread("roy");;
    private static Handler workHandler;

    static {
        initMainHandler();
        initWorkThread();
    }

    public static ThreadHelper getInstance() {
        return sInstance;
    }

    private ThreadHelper() {
    }

    private static void initWorkThread() {
        workThread.start();
        workHandler = new Handler(workThread.getLooper());
    }

    private static void initMainHandler() {
        if (uiHandler == null) {
            uiHandler = new Handler(Looper.getMainLooper());
        }
    }

    public  void runOnMainThread(Runnable runnable) {
        if (isUiThread()) {
            runnable.run();
            return;
        }
        uiHandler.post(runnable);
    }

    public static void runOnMainThreadDelay(Runnable runnable, int sec) {
        uiHandler.postDelayed(runnable, sec * 1000);
    }

    private  boolean isUiThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    public  void runOnLoadThread(Runnable runnable) {
        mLoadExecutor.execute(runnable);
    }

    public void runInWorkThread(Runnable runnable) {
        workHandler.post(runnable);
    }

    public void runInWorkThreadDelay(Runnable runnable, int sec) {
        workHandler.postDelayed(runnable, sec * 1000);
    }

    public void removeInWorkThread(Runnable runnable) {
        workHandler.removeCallbacks(runnable);
    }
    public void removeOnMainThread(Runnable runnable) {
        uiHandler.removeCallbacks(runnable);
    }

    public  void runOnSequenceLoadThread(Runnable runnable) {
        mSequenceLoadExecutor.execute(runnable);
    }
}

