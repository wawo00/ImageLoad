package com.avidly.roy.mediation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAdNetWorks();
    }

    private void initAdNetWorks() {
        // 检查联盟是否存在
        // 检查显示配置联盟
        // 按照配置进行联盟初始化

    }
}
