package com.openup.imageloader.utils;

import java.io.Closeable;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.imageloader.utils
 * @ClassName: IOutils
 * @Description: IOutils
 * @Author: Roy
 * @CreateDate: 2020/3/27 14:41
 */

public class IOutils {
    public static void closeCloseable(Closeable clz) {
        if (clz == null) {
            return;
        }

        try {
            clz.close();
        } catch (RuntimeException runException) {
            throw runException;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
