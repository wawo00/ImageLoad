package com.roy.app.android.workmanager;

import android.content.Context;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: TestApplication
 * @Package: com.roy.app.android.workmanager
 * @ClassName: MyWorker
 * @Description: MyWorker
 * @Author: Roy
 * @CreateDate: 2020/5/26 17:17
 */

public class MyWorker extends Worker {
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("HH:mm:ss:SSS");
    private Date mDate = new Date();

    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int n = getInputData().getInt(MainActivity.KEY, 1);

        System.out.println(n + "," + getId() + "开始任务doWork()");

//        SystemClock.sleep(3000);

        mDate.setTime(System.currentTimeMillis());
        Data outputData = new Data.Builder()
                .putString(MainActivity.KEY, n + "@" + mSimpleDateFormat.format(mDate) + "#" + getId())
                .build();

        return Result.success(outputData);
    }

    @Override
    public void onStopped() {
        super.onStopped();

        System.out.println("任务onStop()");
    }

}
