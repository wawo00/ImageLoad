package com.openup.application;

import android.app.Application;

import com.openup.mytinker.MyThinker;
import com.openup.mytinker.v23;

import java.io.File;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.application
 * @ClassName: MyApplication
 * @Description: MyApplication
 * @Author: Roy
 * @CreateDate: 2020/4/2 16:38
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        MyThinker.install(this.getClassLoader(),new File("/sdcard/fix.dex"),this.getCacheDir());
    }
}
