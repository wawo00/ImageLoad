package com.roy.app.android.aac.controller;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @ProjectName: TestApplication
 * @Package: com.roy.app.android.aac.controller
 * @ClassName: UserController
 * @Description: UserController
 * @Author: Roy
 * @CreateDate: 2020/5/15 17:57
 */

public class UserController implements LifecycleObserver {
    private Lifecycle mLifecycle;

    public UserController(Lifecycle lifecycle) {
        this.mLifecycle = lifecycle;
    }

    private void log(String msg) {
        Log.i("roy_lifecycle", msg);
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreate() {
        log("onCreate");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart() {
        log("onStart");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume() {
        log("onResume");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause() {
        log("onPause");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop() {
        log("onStop");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void onDestroy() {
        log("onDestroy");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny() {
        log("onAny:" + mLifecycle.getCurrentState());
    }
}
