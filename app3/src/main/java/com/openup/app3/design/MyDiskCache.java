package com.openup.app3.design;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design
 * @ClassName: MyDiskCache
 * @Description: MyDiskCache
 * @Author: Roy
 * @CreateDate: 2020/3/23 18:10
 */

public class MyDiskCache implements ImageCache {
    private String dirPath = "sdcard/MyImageLoader/cache/";

    public MyDiskCache() {
        init();
    }

    private void init() {
        File dirFile = new File(dirPath);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        String filePath = getCachePath(url);
        Log.i("roy", "putImage: " + filePath);
        File imgFile = new File(filePath);
        if (imgFile.exists()) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            imgFile.createNewFile();
            fileOutputStream = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (Exception e) {
            Log.e("Roy", "has error in putimg in fileCache " + e.getMessage());
        } finally {
            closeCloseable(fileOutputStream);
        }
    }

    @Override
    public Bitmap get(String url) {
        File cacheImg = new File(getCachePath(url));
        if (!cacheImg.exists()) {
            Log.e("Roy", " cacheImg is not exist" );
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(getCachePath(url));
        return bitmap;
    }

    private String getCachePath(String url) {
        return dirPath + url.toLowerCase().replace("/", "");
    }

    private void closeCloseable(Closeable closeable){
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
