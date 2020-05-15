package com.openup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.enjoy.enjoyfix.Bug;
import com.openup.app3.R;

public class MyTinkerDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tinker_demo);
    }

    public void test(View view) {
        Bug.test();
//        Log.e("Roy", "fix error");

    }

    public void fix(View view) {
        Log.e("Roy", "fix error");
    }
}
