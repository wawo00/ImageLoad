package com.openup.testapplication.application;

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
    }
}
