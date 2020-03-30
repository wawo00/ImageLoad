package com.openup.imageloader.load;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.openup.imageloader.request.BitmapRequest;

import java.io.File;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.load
 * @ClassName: FileLoader
 * @Description: FileLoader
 * @Author: Roy
 * @CreateDate: 2020/3/27 14:43
 */

public class FileLoader extends AbsLoader {

    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        String filePath = request.getRequestUrl();
        File imgFile = new File(filePath);
        Bitmap bitmap = null;
        if (!imgFile.exists() || imgFile.length() < 3) {
            return null;
        }
        return BitmapFactory.decodeFile(filePath);
    }
}
