package com.avidly.roy;

import android.app.Application;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.multidex.MultiDex;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
//import com.google.android.gms.ads.MobileAds;

/**
 * Created by Holaverse on 2017/3/30.
 */

public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(this);
        }

    }

    private static byte[] readFileToByte(String filePath) {
        try {
            File f = new File(filePath);
            int length = (int) f.length();
            byte[] data = new byte[length];
            new FileInputStream(f).read(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String decode(String paramString1, String paramString2) {
        return "-1".equals(paramString1) ? null : new String(Base64.decode(encrypt(paramString1, paramString2), 0));
    }


    public static String encrypt(String paramString1, String paramString2) {
        byte[] arrayOfByte2 = paramString1.getBytes();
        byte[] arrayOfByte1 = paramString2.getBytes();
        int j = 0;
        int i = 0;
        while (j < paramString1.length()) {
            arrayOfByte2[j] = (byte)(arrayOfByte2[j] ^ arrayOfByte1[i]);
            int k = i + 1;
            i = k;
            if (k == arrayOfByte1.length)
                i = 0;
            j++;
        }
        return new String(arrayOfByte2);
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        readAssetsFile();
//        File file = new File(getExternalFilesDir(null).getAbsolutePath() + "/hk");


//        String result = decode(readAssetsFiles("hk"), "5ska3upftwsi4k64bbtf2cdd");
        try {
            String hk_newnew = new String(readAssetsFiles("hk_new"), "utf-8");
            Log.i("roy_decode", "roy_decode :" + hk_newnew);
//            String str = decode(new String(readAssetsFiles("hk_newnew"), "utf-8"),"5ska3upftwsi4k64bbtf2cdd");
//            Log.i("roy_decode", "roy_decode :" + str);
//            Log.i("roy_decode", "roy_timestamp:" + getUrlTimestamp());
        }catch (Throwable e){
            e.printStackTrace();
        }

    }

//    private void readAssetsFile() {
//        try {
//            InputStream in = MyApplication.this.getClass().getClassLoader().getResourceAsStream("assets/hk");
//            File file = new File(getExternalFilesDir(null).getAbsolutePath() + "/uh");
//            FileOutputStream fos = new FileOutputStream(file);
//            int len = 0;
//            byte[] buffer = new byte[1024];
//            while ((len = in.read(buffer)) != -1) {
//                fos.write(buffer, 0, len);
//                fos.flush();
//            }
//            String result = decode(buffer, "5ska3upftwsi4k64bbtf2cdd");
//            Log.i("roy_decode", "roy_decode :" + result);
//            in.close();
//            fos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static long getUrlTimestamp() {
        return System.currentTimeMillis() % 10000L;
    }

    private byte[] readAssetsFiles(String fileName) {
        byte[] bytes = new byte[6 * 1024 * 1024]; // 6kb
        try {
            AssetManager assetManager = getAssets();
            InputStream inputStream = assetManager.open(fileName);
            inputStream.read(bytes);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bytes;

    }

    private byte[] readFile(File file) {
        try {
            File f = file;
            int length = (int) f.length();
            byte[] data = new byte[length];
            new FileInputStream(f).read(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
