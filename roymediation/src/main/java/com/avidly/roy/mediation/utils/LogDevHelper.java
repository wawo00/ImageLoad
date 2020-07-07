package com.avidly.roy.mediation.utils;

import android.util.Log;

import static com.avidly.roy.mediation.constant.ConstantValue.ROY_TAG;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.utils
 * @ClassName: LogHelper
 * @Description: LogHelper
 * @Author: Roy
 * @CreateDate: 2020/6/3 11:11
 */

public class LogDevHelper {

    public static void logi(String content) {
        Log.i(ROY_TAG, content);
    }

    public static void logw(String content) {
        Log.w(ROY_TAG, content);
    }

    public static void loge(String content) {
        Log.e(ROY_TAG, content);
    }
    public static void logLoadI(String content) {
        Log.i(ROY_TAG, "load---"+content);
    }
    public static void logShowI(String content) {
        Log.i(ROY_TAG, "Show---"+content);
    }
}
