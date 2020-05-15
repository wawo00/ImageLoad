package com.roy.app.android.aac;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

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

public class MainActivity extends AppCompatActivity implements UserBusiness.UserListener, Observer<User> {
    private TextView mNameView;
    private TextView mAgeView;
    private UserBusiness mUserBusiness = UserBusiness.get();
    private UserLiveData mUserLiveData;
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
                mUserBusiness.requestUser();
            }
        });
//        getLifecycle().addObserver(new UserController(getLifecycle()));

        // 加载数据库缓存中的用户信息
        User user = mUserBusiness.LoadUser();

        mUserLiveData = new UserLiveData();
        // LiveData注册对Activity的监听，同时Activity注册对LiveData的监听
        mUserLiveData.observe(this, this);
        // 更新LiveData中的数据
        mUserLiveData.postValue(user);
        mUserBusiness.addListener(this);

    }

    @Override
    protected void onDestroy() {
        mUserBusiness.removeListener(this);
        super.onDestroy();
    }

    @Override
    public void onRequestUserResult(int code, User user) {
        if (code == 0) {
            mNameView.setText("昵称：" + user.name);
            mAgeView.setText("年龄：" + user.age);
        } else {
            Toast.makeText(MainActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onChanged(User user) {
        mNameView.setText("昵称：" + user.name);
        mAgeView.setText("年龄：" + user.age);
    }
}
