package com.avidly.roy;

import android.app.Application;
import android.content.Context;
import android.os.Build;

import androidx.multidex.MultiDex;
//import com.google.android.gms.ads.MobileAds;

/**
 * Created by Holaverse on 2017/3/30.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
}
