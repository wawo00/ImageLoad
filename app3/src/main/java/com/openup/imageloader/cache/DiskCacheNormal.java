package com.openup.imageloader.cache;

import android.content.Context;
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

public class DiskCacheNormal {

    private String dirPath = "";
    private Context context;

    public DiskCacheNormal(Context context, String dirPath) {
        this.dirPath = dirPath;
        this.context = context;
        init(context, dirPath);
    }

    /**
     * 默认使用的是内部缓存cache路径
     *
     * @param context
     */
    public DiskCacheNormal(Context context) {
        String path = context.getCacheDir().getAbsolutePath();
        init(context, path);
    }

    private void init(Context context, String dirPath) {
        File cacheDir = new File(dirPath);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }
    }

    public void putImage(String url, Bitmap bitmap) {
        String filePath = getCachePath(url);
        File imgFile = new File(filePath);
        if (imgFile.exists()) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(filePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
        } catch (Exception e) {
            Log.e("Roy", "has error in putimg in fileCache"+e.getMessage());
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
        Bitmap bitmap = BitmapFactory.decodeFile(getCachePath(url));
        return bitmap;
    }


    private String getCachePath(String origanlName) {
        return dirPath + File.separator + origanlName.toLowerCase().replace("/", "");
    }

}
