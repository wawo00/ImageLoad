package com.openup.app3;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;


import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * @ProjectName: AnalysisSdk
 * @Package: com.aly.analysis.utils.duration
 * @ClassName: DurationReport
 * @Description: 用于统计在线时长
 * @Author: Roy
 * @CreateDate: 2020/3/18 18:32
 */

public class DurationReport {

    private static DurationReport sInstance = new DurationReport();
    // 用于计算间隔时间
    private static long resumeTime = 0;

    // 隔多久上传一次打点 (360s /5min)
    private final static long MAX_INTERTIME_SEC = 5 * 60;

    // 隔多久检查一次上报条件是否满足(ms)
    private final static long CHECK_INTERTIME_MILLSEC = 2*1000;

    //需要上报的信息
    private static String sSeverName;
    private static String sServerZone;
    private static String sUId;
    private static String sGGId;

    // 两次onpause间隔最小值(s)
    private final static int MIN_INTERPAUSE_TIME_SEC=1;

    private static HandlerThread sHandlerThread = new HandlerThread("druation");
    private static Handler sWorkHandler;

    private DurationReport() {
    }

    public static DurationReport getInstance() {
        return sInstance;
    }

    /**
     * 初始化登录时长的打点数据
     * @param uId 游戏角色id
     */
    public static void initReport(final String uId) {
        if (TextUtils.isEmpty(uId)) {
            return;
        }
        sUId = uId;
    }

    /**
     * 初始化登录时长的打点数据
     * @param severName  角色所在服务器
     * @param serverZone 角色所在区服
     * @param uId        游戏角色id
     * @param ggId       账号id
     */
    public static void initReport(final String severName, final String serverZone, final String uId, final String ggId) {
        if (severName == null || serverZone == null || uId == null || ggId == null) {
            return;
        }
        if (TextUtils.isEmpty(uId)) {
            return;
        }
        sSeverName = severName;
        sServerZone = serverZone;
        sUId = uId;
        sGGId = ggId;
    }
    /**
     * 游戏置于前台
     */
    public static void onAppResume() {
        // 开始循环检查
        resumeTime = System.currentTimeMillis();
        startSchedule();
    }

    /**
     * 游戏置于后台
     */
    public static void onAppPause() {
        if ((System.currentTimeMillis() - resumeTime) > MIN_INTERPAUSE_TIME_SEC * 1000 ) {
            // 打点T02
            LogT02();
        }
        // 取消resume计时逻辑
        cancelSchedule();
    }

    // 获得游戏时长
    private static int getDurTime() {
        return (int) ((System.currentTimeMillis() - resumeTime) / 1000);
    }

    private static void cancelSchedule() {
        sWorkHandler.removeMessages(1);
        sWorkHandler = null;
    }

    private static void startSchedule() {
        if (!sHandlerThread.isAlive()) {
            sHandlerThread.start();
        }
        sWorkHandler = new Handler(sHandlerThread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                long interTime=getDurTime();
                if (interTime >= MAX_INTERTIME_SEC) {
                    // 打点T02
                    LogT02();
                    //重置resumeTime
                    resumeTime = System.currentTimeMillis();
                }
                sWorkHandler.sendEmptyMessageDelayed(1, CHECK_INTERTIME_MILLSEC);
                return true;
            }
        });
        sWorkHandler.sendEmptyMessage(1);
    }

    //  T02打点
    private static void LogT02() {
        HashMap<String, String> valueMap = new LinkedHashMap<>();
        if (!TextUtils.isEmpty(sSeverName)) {
            valueMap.put("H", sSeverName);
        }
        if (!TextUtils.isEmpty(sServerZone)) {
            valueMap.put("Z", sServerZone);
        }
        if (!TextUtils.isEmpty(sUId)) {
            valueMap.put("U", sUId);
        }
        if (!TextUtils.isEmpty(sGGId)) {
            valueMap.put("GGID", sGGId);
        }
        valueMap.put("T", getDurTime()+"");
//        ALYLog.i("打点T02" + valueMap.toString());
//        ALYAnalysisApi.logEvent("T02", valueMap);
    }

}
