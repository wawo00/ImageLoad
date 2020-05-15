package com.roy.app.android.aac.utils;


import android.os.Handler;
import android.os.Looper;

import androidx.annotation.MainThread;

import java.util.Random;

/**
 * @ProjectName: TestApplication
 * @Package: com.roy.app.android.aac.utils
 * @ClassName: HttpUtils
 * @Description: HttpUtils
 * @Author: Roy
 * @CreateDate: 2020/5/15 16:52
 */

public class HttpUtils {
    private static Handler sMainHandler = new Handler(Looper.getMainLooper());


    public interface ResponseCallback {
        void onResponseSuccessed(String json);

        void onResponseFailed(int reason);
    }

    public static  void request(final ResponseCallback callback) {

        sMainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int value = new Random().nextInt(5);
                if (callback != null) {
                    if (value == 2) {
                        callback.onResponseFailed(1);
                    } else {
                        callback.onResponseSuccessed("{\"name\": \"纯爷们\", \"age\": 20}");
                    }
                }
            }
        }, 500);

    }

}
