package com.openup.mytinker;

import android.content.Context;
import android.util.Log;
import android.util.StateSet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.mytinker
 * @ClassName: v23
 * @Description: 用于V23版本及其以上反射调用classload 实现自己热修复(新旧dex的合并)
 * @Author: Roy
 * @CreateDate: 2020/4/2 14:31
 */

public class v23 {

    private static final String TAG = "mytinker";

    public static void install(ClassLoader loader, List<File> dexes, File optimizedDirectory) {
        try {
            // step1 先找到pathList
            Field pathListFiled = ShareReflectUtil.findField(loader, "pathList");
            Object pathList = pathListFiled.get(loader);

            //通过 makeDexElements 获得elements

            //源码中是：
//         ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
//        // save dexPath for BaseDexClassLoader
//        this.dexElements = makeDexElements(splitDexPath(dexPath), optimizedDirectory,
//                                           suppressedExceptions, definingContext, isTrusted);

            ArrayList<IOException> suppressedExceptions = new ArrayList<IOException>();
            // 这样获得了新加入的elements
            Object[] elements = makePathElements(pathList, dexes, optimizedDirectory, suppressedExceptions);

            // 将新加入的dex文件追加到老dex文件前面
            expandFileArray(elements, pathList);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void expandFileArray(Object[] ext, Object pathList) throws NoSuchFieldException, IllegalAccessException {
        // 获得classloader中原本的elements
        Field elementsField = ShareReflectUtil.findField(pathList, "dexElements");
        Object[] orignal = (Object[]) elementsField.get(pathList);

        // 进行array的拼接
        Object[] combined = (Object[]) Array.newInstance(ext.getClass().getComponentType(), ext.length + orignal.length);
        System.arraycopy(ext, 0, combined, 0, ext.length);
        System.arraycopy(orignal, 0, combined, ext.length, orignal.length);
        elementsField.set(pathList, combined);
    }


//  private static Element[] makePathElements(List<File> files,
//                                              List<IOException> suppressedExceptions,
//                                              ClassLoader loader)

    private static Object[] makePathElements(Object dexPathList, List<File> files, File optimizedDirectory,
                                             List<IOException> suppressedExceptions) throws InvocationTargetException, IllegalAccessException {
        Method makePathElements = null;
        try {
            makePathElements = ShareReflectUtil.findMethod(dexPathList, "makePathElements", List.class, File.class, List.class);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException: makePathElements(List,File,List) failure");
            try {
                makePathElements = ShareReflectUtil.findMethod(dexPathList, "makePathElements", ArrayList.class, File.class, ArrayList.class);
            } catch (NoSuchMethodException ex) {
                Log.e(TAG, "NoSuchMethodException: makeDexElements(ArrayList,File,ArrayList) failure");
            }
        }
        if (makePathElements == null) {
            throw new RuntimeException("fix error");
        }

        return (Object[]) makePathElements.invoke(dexPathList, files, optimizedDirectory, suppressedExceptions);

    }

}
