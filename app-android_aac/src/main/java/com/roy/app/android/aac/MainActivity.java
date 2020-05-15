package com.roy.app.android.aac;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.roy.app.android.aac.bean.User;
import com.roy.app.android.aac.business.UserBusiness;
import com.roy.app.android.aac.utils.DbUtils;
import com.roy.app.android.aac.utils.HttpUtils;

public class MainActivity extends AppCompatActivity implements UserBusiness.UserListener {
    private TextView mNameView;
    private TextView mAgeView;
    private UserBusiness mUserBusiness = UserBusiness.get();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNameView = (TextView) findViewById(R.id.tv_name);
        mAgeView = (TextView) findViewById(R.id.tv_age);
        User user = mUserBusiness.LoadUser();
        if (user != null) {
            mNameView.setText("昵称：" + user.name);
            mAgeView.setText("年龄：" + user.age);
        }
        mUserBusiness.addListener(this);

        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从服务器拉取最新用户数据并显示
                mUserBusiness.requestUser();
            }
        });

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
}
