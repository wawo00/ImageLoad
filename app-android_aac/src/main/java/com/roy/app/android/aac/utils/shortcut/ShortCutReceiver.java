package com.roy.app.android.aac.utils.shortcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.roy.app.android.aac.BuildConfig;

public class ShortCutReceiver extends BroadcastReceiver {
    public final static String CREATE_SHORTCUT = "app.action.CREATE_SHORTCUT";
    public final static String SHORTCUT_TYPE = "shortcut_type";
    public static final String SHORTCUT_APP = "shortcut.app";

    @Override
    public void onReceive(Context context, Intent intent) {
        /*
         * Will receive callback when the shortcut is pinned successfully.
         * Or, If the user doesn't allow the shortcut to be pinned to the launcher, won't receive a callback.
         */

        String shortcut = intent.getStringExtra(SHORTCUT_TYPE);
        if (!TextUtils.isEmpty(shortcut)) {
            if (shortcut.equals(SHORTCUT_APP)) {

            }
        }
        if (BuildConfig.DEBUG) {
//            LogUtil.d(LogFilterDef.SHORT_CUT_LOG, "shortcut created : " + shortcut);
            Log.i("roy", "onReceive: shortcut created : " + shortcut);
        }
    }
}
