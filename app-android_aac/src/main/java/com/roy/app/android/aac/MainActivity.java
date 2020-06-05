package com.roy.app.android.aac;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roy.app.android.aac.bean.User;
import com.roy.app.android.aac.bean.UserLiveData;
import com.roy.app.android.aac.business.UserBusiness;
import com.roy.app.android.aac.controller.UserController;
import com.roy.app.android.aac.databinding.ActivityMainBinding;
import com.roy.app.android.aac.utils.DbUtils;
import com.roy.app.android.aac.utils.HttpUtils;
import com.roy.app.android.aac.utils.shortcut.ShortcutUtil;
import com.roy.app.android.aac.viewmodel.UserViewModel;

public class MainActivity extends FragmentActivity {
//    private UserLiveData mUserLiveData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        ViewModelProviders.of(this).get(UserViewModel.class).observe(this,binding);
//
//        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 从服务器拉取最新用户数据并显示
//                mViewModel.refresh();
//            }
//        });
////        getLifecycle().addObserver(new UserController(getLifecycle()));
//        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//        mViewModel.observe(this, this);
    }


//    @Override
//    public void onChanged(User user) {
//        // LiveData中的数据更新，在这里刷新UI，这个方法是在主线程中调用的，可放心刷新UI
//        mNameView.setText("昵称：" + user.name);
//        mAgeView.setText("年龄：" + user.age);
//    }


    private void addShortCut(){
//        Intent shortCutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "测试");
//        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(this, R.mipmap.panda));
//        Intent intent = new Intent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        intent.setClass(this, MainActivity.class);
//        shortCutIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//// 发送广播
//        this.sendBroadcast(shortCutIntent);
//        ShortcutUtil.createShortcutForTypeOnO(this,1,this,"shortcut");
        ShortcutUtil.createNormalShortcut(this,"测试测试所所所所所所所",this.getClass().getName(),R.mipmap.panda);
    }

    public void onShortCutClick(View view) {
        Log.i("roy", "onShortCutClick click: ");
        addShortCut();
    }

    private void deleteIcon() {
//        Intent intent = new Intent();
//        intent.setClass(this, MainActivity.class);
//        intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.LAUNCHER");
//        Intent addShortcut = new Intent(action_add);
//        Parcelable icon = Intent.ShortcutIconResource.fromContext(this,
//                R.drawable.icon);
//        addShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, getString(R.string.app_name));
//        addShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intent);
//        addShortcut.putExtra("duplicate", 0);
//        addShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
//        sendBroadcast(addShortcut);
//        intent.setAction("android.intent.action.MAIN");
//        intent.addCategory("android.intent.category.LAUNCHER");
//        PackageManager p = getPackageManager();
//        p.setComponentEnabledSetting(getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
}
    private void magicHide() {
        try {
            PackageManager pm = this.getPackageManager();
            ComponentName cn = new ComponentName(this, MainActivity.class);
            pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("roy", "magicHide has error : "+e.getMessage());
        }
    }

    public void deleteIcon(View view) {
        magicHide();
    }

}
