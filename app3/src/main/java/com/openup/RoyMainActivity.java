package com.openup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import com.openup.app3.R;
import com.openup.testlib.TestLocalMaven;

import java.time.Instant;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.REQUEST_INSTALL_PACKAGES;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RoyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roy_main);
        getAccess();
        TestLocalMaven.printLocalMaven("1231412");
    }

    private void getAccess() {
        if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, REQUEST_INSTALL_PACKAGES) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_PHONE_STATE, READ_CONTACTS, ACCESS_FINE_LOCATION, REQUEST_INSTALL_PACKAGES}, 001);
        }

    }

    public void imageload(View view) {
        this.startActivity(new Intent(this, ImageLoaderDemo.class));
    }

    public void mytinker(View view) {
        this.startActivity(new Intent(this, MyTinkerDemo.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
