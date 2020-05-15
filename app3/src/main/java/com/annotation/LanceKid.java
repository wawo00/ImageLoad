package com.annotation;

/**
 * @ProjectName: TestApplication
 * @Package: com.annotation
 * @ClassName: LanceKid
 * @Description: LanceKid
 * @Author: Roy
 * @CreateDate: 2020/4/7 17:47
 */

public class LanceKid extends Lance {

    public LanceKid(String lance) {
        super(lance);
    }

    public static void main(String[] args) {
        Class<LanceKid> lanceKidClass = LanceKid.class;
        if (lanceKidClass.isAnnotationPresent(Tuhao.class)) {
            Tuhao tuhao = lanceKidClass.getAnnotation(Tuhao.class);
            String value = tuhao.value();
            System.out.printf("tuhao" + value);
        }

    }
}
