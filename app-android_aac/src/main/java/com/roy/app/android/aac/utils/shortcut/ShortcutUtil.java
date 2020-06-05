package com.roy.app.android.aac.utils.shortcut;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.database.Cursor;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.roy.app.android.aac.R;

import java.util.List;

import static com.roy.app.android.aac.utils.shortcut.ShortCutReceiver.CREATE_SHORTCUT;

public class ShortcutUtil {
    public static final int SHORTCUT_TYPE_APP = 1;

    public static int VALUE_UNKNOW = 0;
    public static int VALUE_FALSE = 1;
    public static int VALUE_TRUE = 2;
    private static String sBufferedValue;

//    public static boolean hasShortCutCreated() {
//        return LocalStorageManager.getBoolean(SharePrefConstant.SHORT_CUT_CREATE, false);
//    }
//
//    public static void setShortCutCreated(){
//        LocalStorageManager.setBoolean(SharePrefConstant.SHORT_CUT_CREATE, true);
//    }

    @TargetApi(26)
    public static void createShortcutForTypeOnO(Context context, int type, Activity activity,String name) {
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                switch (type) {
                    case SHORTCUT_TYPE_APP:
                        pinShortcut(context, activity.getClass(), R.mipmap.panda, name, ShortCutReceiver.SHORTCUT_APP);
                        break;
                }
            } catch (Exception e) {

            }
        }
    }

//    public static void createShortcutForType(int type) {
//        if (!Build.MODEL.equals("SM-G9508")) {
//            switch (type) {
//                case SHORTCUT_TYPE_APP:
//                    createNormalShortcut(ResourceUtil.getString(R.string.app_name), ShortCutActivity.class.getName(), R.mipmap.ic_launcher, "shortcut-app");
//                    break;
//            }
//        } else {
//            switch (type) {
//                case SHORTCUT_TYPE_APP:
//                    createImplicitShortcut(ResourceUtil.getString(R.string.app_name), ConstantUtils.ACTION_SHORTCUT_APP, R.mipmap.ic_launcher, "shortcut-app");
//                    break;
//            }
//        }
//    }

    //添加快捷启动
    public static void createImplicitShortcut(Context  context,String name, String action, int bitmapRes) {
        Intent shortcutIntent = new Intent(action);
//        if (!StringUtil.isEmpty(callerType)) {
//            shortcutIntent.putExtra(BaseActivity.KEY_CALL_FROM, callerType);
//        }
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Intent createShortcutIntent = getResultIntent(context,
                shortcutIntent,
                bitmapRes,
                name);

        context.sendBroadcast(createShortcutIntent);
    }

    public static void createNormalShortcut(Context  context,String name, String className, int bitmapRes) {
        Intent shortcutIntent = new Intent(Intent.ACTION_MAIN);
        shortcutIntent.setClassName(context, className);
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

        Intent createShortcutIntent = getResultIntent(context,
                shortcutIntent,
                bitmapRes,
                name);

          context.sendBroadcast(createShortcutIntent);
    }

//    public static int isShortcutExist(int type) {
//
////        int exist = VALUE_UNKNOW;
////        try {
////            switch (type) {
////                case SHORTCUT_TYPE_APP:
////                    exist = isShortCutExist(ApplicationEx.getInstance(), ResourceUtil.getString(R.string.app_name), getShortCutIntent(ApplicationEx.getInstance(), SplashActivity.class.getName()));
////                    break;
////            }
////
////        } catch (Exception e) {
////        }
//
//        return exist;
//    }

    /**
     * 判断快捷方式是否存在
     * <p/>
     * 检查快捷方式是否存在 <br/>
     * <font color=red>注意：</font> 有些手机无法判断是否已经创建过快捷方式<br/>
     * 因此，在创建快捷方式时，请添加<br/>
     * shortcutIntent.putExtra("duplicate", false);// 不允许重复创建<br/>
     * 最好使用{@link #isShortCutExist(Context, String, Intent)}
     * 进行判断，因为可能有些应用生成的快捷方式名称是一样的的<br/>
     *
     * @param context context
     * @param title   快捷方式名
     * @return 是否存在
     */
//    private static boolean isShortCutExist(Context context, String title) throws UnrecognizedException {
//        boolean result = true;
//        try {
//            ContentResolver cr = context.getContentResolver();
//            Uri uri = getUriFromLauncher(context);
//            Cursor c = cr.query(uri, new String[]{"title"}, "title=? ", new String[]{title}, null);
//            if (c != null && c.getCount() > 0) {
//                result = true;
//            }
//            if (c != null && !c.isClosed()) {
//                c.close();
//            }
//        } catch (Exception e) {
//            throw new UnrecognizedException("Can not judge");
//        }
//        return result;
//    }

    /**
     * 判断快捷方式是否存在
     * <p/>
     * 不一定所有的手机都有效，因为国内大部分手机的桌面不是系统原生的<br/>
     * 更多请参考{@link #isShortCutExist(Context, String)}<br/>
     * 桌面有两种，系统桌面(ROM自带)与第三方桌面，一般只考虑系统自带<br/>
     * 第三方桌面如果没有实现系统响应的方法是无法判断的，比如GO桌面<br/>
     *
     * @param context context
     * @param title   快捷方式名
     * @param intent  快捷方式Intent
     * @return 是否存在
     * @throws UnrecognizedException 无法判断时将抛出该异常
     */
//    private static int isShortCutExist(Context context, String title, Intent intent) throws UnrecognizedException {
//        int result = VALUE_UNKNOW;
//        try {
//            ContentResolver cr = context.getContentResolver();
//            Uri uri = getUriFromLauncher(context);
//            Cursor c = cr.query(uri, new String[]{"title", "intent"}, "title=?  and intent=?",
//                    new String[]{title, intent.toUri(0)}, null);
//            if (c != null) {
//                if (c.getCount() > 0) {
//                    result = VALUE_TRUE;
//                } else {
//                    result = VALUE_FALSE;
//                }
//            }
//
//            if (c != null && !c.isClosed()) {
//                c.close();
//            }
//        } catch (Exception ex) {
//            throw new UnrecognizedException("Can not judge");
//        }
//        return result;
//    }

//    private static Uri getUriFromLauncher(Context context) {
//        StringBuilder uriStr = new StringBuilder();
//        String authority = getAuthorityFromPermissionDefault(context);
//        if (authority == null || authority.trim().equals("")) {
//            authority = getAuthorityFromPermission(context, FunctionUtil.getCurrentLauncherPackageName(context) + ".permission.READ_SETTINGS");
//        }
//        uriStr.append("content://");
//        if (TextUtils.isEmpty(authority)) {
//            return Uri.parse(uriStr.toString());
//        } else {
//            uriStr.append(authority);
//        }
//        uriStr.append("/favorites?notify=true");
//        return Uri.parse(uriStr.toString());
//    }

    /**
     * 获得shortcutIntent,可以用该intent判断shortcut是否存在
     *
     * @param context Context
     * @param action  与shortcut绑定的activity类名{CleanShortcutActivity.class.getName()}
     */
    public static Intent getShortCutIntent(Context context, String action) {
        Intent shortcutIntent = new Intent(action);
//        shortcutIntent.setClassName(context, className);
//        shortcutIntent.setAction(Intent.ACTION_MAIN);
        shortcutIntent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return shortcutIntent;
    }

    /**
     * 获得resultIntent用来通知手机创建shortcut
     *
     * @param context        Context
     * @param shortcutIntent shortcutIntent
     * @param iconResourceld shortcut图片资源
     * @param title          shortcut名字
     */
    public static Intent getResultIntent(Context context, Intent shortcutIntent, int iconResourceld, String title) {
        final Intent resultIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context, iconResourceld));
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        resultIntent.putExtra("duplicate", false);
        resultIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, title);
        resultIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        return resultIntent;
    }

    public static String getAuthorityFromPermissionDefault(Context context) {
        if (TextUtils.isEmpty(sBufferedValue))//we get value buffered
            sBufferedValue = getAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS");
        return sBufferedValue;
    }

    /**
     * be cautious to use this, it will cost about 500ms 此函数为费时函数，大概占用500ms左右的时间<br/>
     * android系统桌面的基本信息由一个launcher.db的Sqlite数据库管理，里面有三张表<br/>
     * 其中一张表就是favorites。这个db文件一般放在data/data/com.android.launcher(launcher2)文件的databases下<br/>
     * 但是对于不同的rom会放在不同的地方<br/>
     * 例如MIUI放在data/data/com.miui.home/databases下面<br/>
     * htc放在data/data/com.htc.launcher/databases下面<br/
     *
     * @param context
     * @param permission 读取设置的权限  READ_SETTINGS_PERMISSION
     * @return
     */
    public static String getAuthorityFromPermission(Context context, String permission) {
        if (TextUtils.isEmpty(permission)) {
            return "";
        }

        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            if (packs == null) {
                return "";
            }
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (providers != null) {
                    for (ProviderInfo provider : providers) {
                        if (permission.equals(provider.readPermission) || permission.equals(provider.writePermission)) {
                            return provider.authority;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Android 8.0 create shortCut
     * <p>只有具有前台活动或前台服务的应用才能调用此方法，否则，它会抛出{@link IllegalStateException}。
     * site cited：https://developer.android.com/guide/topics/ui/shortcuts.html
     */
    @SuppressWarnings("WrongConstant")
    @RequiresApi(api = 26)
    public static void pinShortcut(Context context, Class activity, int drawableId, String label, String shortId) {
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                ShortcutManager shortcutManager = (ShortcutManager) context.getSystemService(Context.SHORTCUT_SERVICE);
                if (shortcutManager != null && shortcutManager.isRequestPinShortcutSupported()) {
                    Intent shortcutIntent = new Intent(context, activity);
                    shortcutIntent.setAction(Intent.ACTION_VIEW);

                    ShortcutInfo shortInfo = new ShortcutInfo.Builder(context, shortId)
                            .setIcon(Icon.createWithResource(context, drawableId))
                            .setShortLabel(label)
                            .setIntent(shortcutIntent)
                            .build();

                    Intent pinnedShortcutCallbackIntent = new Intent(context, ShortCutReceiver.class);
                    pinnedShortcutCallbackIntent.putExtra(ShortCutReceiver.SHORTCUT_TYPE, shortId);
                    pinnedShortcutCallbackIntent.setAction(CREATE_SHORTCUT);
                    PendingIntent shortcutCallbackIntent = PendingIntent.getBroadcast(context, 0, pinnedShortcutCallbackIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                    shortcutManager.requestPinShortcut(shortInfo, shortcutCallbackIntent.getIntentSender());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static class UnrecognizedException extends Exception {
        UnrecognizedException(String detailMessage) {
            super(detailMessage);
        }
    }

}
