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
import com.roy.app.android.aac.utils.DbUtils;
import com.roy.app.android.aac.utils.HttpUtils;

public class MainActivity extends AppCompatActivity {
    private TextView mNameView;
    private TextView mAgeView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameView = (TextView) findViewById(R.id.tv_name);
        mAgeView = (TextView) findViewById(R.id.tv_age);

        // 加载缓存的用户数据并展示
        User user = loadUser();
        if (user != null) {
            mNameView.setText("昵称：" + user.name);
            mAgeView.setText("年龄：" + user.age);
        }

        findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 从服务器拉取最新用户数据并显示
                refresh();
            }
        });
    }

    private User loadUser() {
        // 这里本应从cursor中获取数据，但为求程序尽量简单，我们直接使用模拟数据。之所以要加入query这段代码，是为了尽可能模拟真实的流程。
        Cursor cursor = DbUtils.query(null);
        if (cursor != null) {
            try {

            } catch (Exception e) {

            } finally {
                cursor.close();
            }
        }
        User user = new User();
        user.name = "小虾米";
        user.age = 19;
        return user;
    }

    private void refresh() {
        HttpUtils.request(new HttpUtils.ResponseCallback() {
            @Override
            public void onResponseSuccessed(String json) {
                try {
                    User user = new Gson().fromJson(json, User.class);

                    // 用户信息更新了，要同步更新数据库中的记录
                    String updateSql = null;
                    DbUtils.update(updateSql);
                    mNameView.setText("昵称：" + user.name);
                    mAgeView.setText("年龄：" + user.age);
                } catch (Exception e) {
                }
            }

            @Override
            public void onResponseFailed(int reason) {
                Toast.makeText(MainActivity.this, "刷新失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
