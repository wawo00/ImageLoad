package com.openup.imageloader.cache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.design
 * @ClassName: DiskCache
 * @Description: DiskCache
 * @Author: Roy
 * @CreateDate: 2020/3/23 10:57
 */

public class DiskCache {

    private String dirPath="sdcard/royImageLoader/";

    public DiskCache( ) {
        init();
    }

    private void init() {
        File cacheDir = new File(dirPath);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public void putImage(String url, Bitmap bitmap) {
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
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Bitmap getImage(String url) {
        File cacheImg = new File(dirPath);
        if (cacheImg == null) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(dirPath + url);
        return bitmap;
    }

    private String getCachePath(String url) {
        return dirPath + url.toLowerCase().replace("/", "");
    }

}
