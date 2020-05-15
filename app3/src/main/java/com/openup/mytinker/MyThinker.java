package com.openup.mytinker;

import android.os.Build;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.mytinker
 * @ClassName: MyThinker
 * @Description: MyThinker
 * @Author: Roy
 * @CreateDate: 2020/4/2 16:39
 */

public class MyThinker {
    public static void install(ClassLoader loader, File dex, File optimizedDirectory) {
        List<File> files = new ArrayList<>();
        files.add(dex);
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                v23.install(loader, files, optimizedDirectory);
            } else if (Build.VERSION.SDK_INT >= 19) {

            } else { //   if (Build.VERSION.SDK_INT >= 14)

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
