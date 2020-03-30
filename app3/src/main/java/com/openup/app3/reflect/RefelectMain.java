package com.openup.app3.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.reflect
 * @ClassName: RefelectMain
 * @Description: RefelectMain
 * @Author: Roy
 * @CreateDate: 2020/3/25 10:46
 */

public class RefelectMain {
    static String name = "com.openup.app3.reflect.Person";

    public static void main(String[] args) {
        try {
            Class<Person> personClz = (Class<Person>) Class.forName(name);
            getConstructor(personClz);
            getMethod(personClz);


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private static void getMethod(Class<Person> personClz) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Method[] methods=personClz.getMethods();
        System.out.println("获取clazz对应类中的所有方法，" +
                "不能获取private方法,且获取从父类继承来的所有方法");
        for (Method method : methods) {
            System.out.println("get all methods is "+method);
        }
        System.out.println("");
        System.out.println("---------------------------");

        System.out.println("获取所有方法，包括私有方法，" +
                "所有声明的方法，都可以获取到，且只获取当前类的方法");
        methods = personClz.getDeclaredMethods();
        for(Method method:methods){
            System.out.print(" "+method.getName()+"()");

        }
        System.out.println("");
        System.out.println("---------------------------");

        System.out.println("获取指定的方法，" +
                "需要参数名称和参数列表，无参则不需要写");
        Method methodGetName=personClz.getDeclaredMethod("setName",String.class);
        System.out.println(methodGetName);
        System.out.println("---");


        // 如何执行方法
        System.out.println("执行方法，第一个参数表示执行哪个对象的方法" +
                "，剩下的参数是执行方法时需要传入的参数");
        Object object = personClz.newInstance();
        methodGetName.invoke(object,"roy");
        System.out.println("");
        System.out.println("---------------------------");

        System.out.println("执行私有方法");
        // 获得和执行私有方法
        Class[] parameterTypes;
        Method methodPrvate=personClz.getDeclaredMethod("privateMethod");
        methodPrvate.setAccessible(true);
        methodPrvate.invoke(object);
        System.out.println("");
        System.out.println("---------------------------");
    }

    private static void getConstructor(Class<Person> personClz) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Constructor<Person>[] constructors = (Constructor<Person>[]) personClz.getConstructors();
        System.out.println("获得所有的构造方法");
        for (Constructor<Person> constructor : constructors) {
            System.out.println("all constructor is " + constructor);
        }

        // 获得一个特定的构造方法
        Constructor<Person> persionConstructor = personClz.getDeclaredConstructor(String.class, int.class);
        System.out.println("get a declaredconstructor is " + persionConstructor);

        // 通过newInstance获得一个对象
        Person reflectPerson = persionConstructor.newInstance("mark", 18);
        System.out.println("reflect entity is " + reflectPerson.getName());
    }

}
