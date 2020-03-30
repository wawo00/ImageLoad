package com.openup.app3.genericity;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.genericity
 * @ClassName: GenerictyMain
 * @Description: GenerictyMain
 * @Author: Roy
 * @CreateDate: 2020/3/25 11:41
 */

public class GenerictyMain {


    public static void main(String[] args) {
        List<Integer> arrSrc = new ArrayList<>();
        List<Integer> arrInt = new ArrayList<>();

        GenerictyMain generictyMain= new GenerictyMain();
        generictyMain.testOther(arrInt,arrSrc);
        generictyMain.test(arrInt,arrSrc);
    }

    public <T extends Number> void test(List<T> src, List<T> dest) {
        System.out.println("src is " + src);
        System.out.println("dest" + dest);
    }

    public  void testOther(List<? extends Number> src, List<? extends Number> dest) {
        System.out.println("src is " + src);
        System.out.println("dest" + dest);
    }


}
