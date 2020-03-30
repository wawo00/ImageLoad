package com.openup.app3.proxy;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.proxy
 * @ClassName: AaFactory
 * @Description: AaFactory
 * @Author: Roy
 * @CreateDate: 2020/3/25 16:10
 */

public class AaFactory implements ManToolsFactory {
    @Override
    public void saleTool(String size) {
        System.out.println("按需求定制了一个size为"+size+"的女model");

    }
}
