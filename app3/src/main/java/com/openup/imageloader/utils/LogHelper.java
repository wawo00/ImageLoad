package com.openup.imageloader.utils;

import android.util.Log;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.utils
 * @ClassName: LogHelper
 * @Description: LogHelper
 * @Author: Roy
 * @CreateDate: 2020/3/27 15:32
 */

public class LogHelper {
    public static final String TAG="roy";
    public static void logi(String contect){
        Log.i(TAG, contect);
    }
    public static void loge(String contect){
        Log.e(TAG, contect);
    }
}
