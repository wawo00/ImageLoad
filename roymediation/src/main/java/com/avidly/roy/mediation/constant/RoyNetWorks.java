package com.avidly.roy.mediation.constant;

/**
 * @ProjectName: TestApplication
 * @Package: com.avidly.roy.mediation.constant
 * @ClassName: RoyNetWorks
 * @Description: RoyNetWorks
 * @Author: Roy
 * @CreateDate: 2020/6/2 18:51
 */

public enum RoyNetWorks {
    ADMOB("admob"),
    APPLOVIN("applovin"),
    Unity("unity");
    private String name;
    RoyNetWorks(String name) {
        this.name = name;
    }

   public  String getName() {
        return name;
    }
}
