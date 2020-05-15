package com.roy.app.android.aac;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roy.app.android.aac.bean.User;
import com.roy.app.android.aac.bean.UserLiveData;
import com.roy.app.android.aac.business.UserBusiness;
import com.roy.app.android.aac.controller.UserController;
import com.roy.app.android.aac.utils.DbUtils;
import com.roy.app.android.aac.utils.HttpUtils;
import com.roy.app.android.aac.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity implements  Observer<User> {
    private TextView mNameView;
    private TextView mAgeView;
    private UserViewModel mViewModel;
//    private UserLiveData mUserLiveData;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameView = (TextView) findViewById(R.id.tv_name);
        mAgeView = (TextView) findViewById(R.id.tv_age);

        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从服务器拉取最新用户数据并显示
                mViewModel.refresh();
            }
        });
//        getLifecycle().addObserver(new UserController(getLifecycle()));
        mViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mViewModel.observe(this, this);
    }


    @Override
    public void onChanged(User user) {
        // LiveData中的数据更新，在这里刷新UI，这个方法是在主线程中调用的，可放心刷新UI
        mNameView.setText("昵称：" + user.name);
        mAgeView.setText("年龄：" + user.age);
    }
}
