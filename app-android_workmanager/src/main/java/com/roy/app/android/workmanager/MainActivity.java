package com.roy.app.android.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Operation;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    static final String KEY = "Work_Key";
    private String TAG = "tag";
    private TextView text;
    private String string = "";
    private static int VALUE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.text);

        Data myData = new Data.Builder()
                .putInt(KEY, VALUE++)
                .build();

        //约束条件。
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
                .build();

        PeriodicWorkRequest.Builder argsWorkBuilder = new PeriodicWorkRequest.Builder(MyWorker.class,1, TimeUnit.MINUTES)
//                .setConstraints(constraints)
                .setInputData(myData);

        PeriodicWorkRequest request = argsWorkBuilder
                .addTag(TAG)
                .build();

        System.out.println(request.getId() + "进入onCreate()");

        LiveData<List<WorkInfo>> liveData = WorkManager.getInstance().getWorkInfosByTagLiveData(TAG);

        liveData.observe(this, new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                for (WorkInfo workInfo : workInfos) {
//                    if (workInfo != null && workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        display((workInfo.getOutputData().getString(KEY))+workInfo.getState().toString());
//                    }
                }
            }
        });

//        Operation operation = WorkManager.getInstance()
//                .enqueue(request);
        WorkManager.getInstance()
                .enqueueUniquePeriodicWork("jobTag", ExistingPeriodicWorkPolicy.KEEP, request);

        //取消任务。
        //WorkManager.getInstance().cancelWorkById(request.getId());
    }

    private void display(String s) {
//        string = string + s + "\n";
//        text.setText(string);
        Log.i(TAG, "display:"+s);

    }

}
