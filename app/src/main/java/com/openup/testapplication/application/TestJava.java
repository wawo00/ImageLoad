package com.openup.testapplication.application;

import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.testapplication.application
 * @ClassName: TestJava
 * @Description: TestJava
 * @Author: Roy
 * @CreateDate: 2020/6/19 11:18
 */

public class TestJava {
    public static void main(String[] args) {
        Long time = System.currentTimeMillis() % 10000L;
        System.out.printf("time is " + time);
//        testTime();
        String filePath = "D:\\Temp\\hk";
//        String content = readFileToText(filePath);
//        System.out.printf("content is " + content);
        String result=decode(readFileToByte(filePath),"5ska3upftwsi4k64bbtf2cdd");
        System.out.printf("result is "+result);
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


    public static String decode( byte[] var0, String var1) {
        return "-1".equals(var0) ? null : new String(Base64.decode(encrypt(var0, var1), 0));
    }

    public static String encrypt( byte[] var0, String var1) {
        byte[] var5 = var0;
        byte[] var6 = var1.getBytes();
        int var3 = 0;

        for (int var2 = 0; var3 < var0.length; ++var3) {
            var5[var3] ^= var6[var2];
            int var4 = var2 + 1;
            var2 = var4;
            if (var4 == var6.length) {
                var2 = 0;
            }
        }

        return new String(var5);
    }
}
