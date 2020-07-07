package com.openup.app2;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.testapplication.application
 * @ClassName: MSSKAPI
 * @Description: MSSKAPI
 * @Author: Roy
 * @CreateDate: 2020/6/28 11:08
 */

public class MSSKAPI {

    private static InitListener sInitListener;
    public interface InitListener{
         void onInited();
    }

    public static InitListener getInitListener() {
        return sInitListener;
    }

    public static void setInitListener(InitListener initListener) {
        sInitListener = initListener;
        startInit();
    }

    private static void startInit() {
//        HandlerThread handlerThread = new HandlerThread("roy");
//        handlerThread.start();
//
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                sInitListener.onInited();
            }
        },1000);
    }
}
