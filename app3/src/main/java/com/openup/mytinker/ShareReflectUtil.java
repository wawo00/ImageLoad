package com.openup.mytinker;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.mytinker
 * @ClassName: ShareReflectUtil
 * @Description: ShareReflectUtil
 * @Author: Roy
 * @CreateDate: 2020/4/2 14:37
 */

public class ShareReflectUtil {

    /**
     * 通过双亲委托形式获得属性
     *
     * @param instance
     * @param name
     * @return
     */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        for (Class clz = instance.getClass(); clz != null; clz = clz.getSuperclass()) {
            try {
                Field field = clz.getDeclaredField(name);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) {
                // ignore and search next
                continue;
            }
        }
        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    /**
     * 使用双亲委托形式获得方法
     *
     * @param instance
     * @param methodName
     * @param parameterTypes
     * @return
     * @throws NoSuchFieldException
     */
    public static Method findMethod(Object instance, String methodName, Class<?>... parameterTypes) throws NoSuchMethodException {
        for (Class<?> clz = instance.getClass(); clz != null; clz = clz.getSuperclass()) {
            try {
                Method targetMethod = clz.getDeclaredMethod(methodName, parameterTypes);
                if (!targetMethod.isAccessible()) {
                    targetMethod.setAccessible(true);
                }
                return targetMethod;
            } catch (NoSuchMethodException e) {
                // ignore and continue
            }
        }
        throw new NoSuchMethodException(methodName + "with parameters"+  Arrays.asList(parameterTypes)+" is not found in " + instance.getClass());

    }

//    /**
//     * 从 instance 到其父类 找  name 方法
//     * @param instance
//     * @param name
//     * @return
//     * @throws NoSuchFieldException
//     */
//    public static Method findMethod(Object instance, String name, Class<?>... parameterTypes)
//            throws NoSuchMethodException {
//        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
//            try {
//                Method method = clazz.getDeclaredMethod(name, parameterTypes);
//
//                if (!method.isAccessible()) {
//                    method.setAccessible(true);
//                }
//
//                return method;
//            } catch (NoSuchMethodException e) {
//                // ignore and search next
//            }
//        }
//        throw new NoSuchMethodException("Method "
//                + name
//                + " with parameters "
//                + Arrays.asList(parameterTypes)
//                + " not found in " + instance.getClass());
//    }

}
