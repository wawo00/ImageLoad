package com.openup.app3.proxy;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.proxy
 * @ClassName: DynamicMain
 * @Description: DynamicMain
 * @Author: Roy
 * @CreateDate: 2020/3/25 16:03
 */

public class DynamicMain {
    public static void main(String[] args) {
        ManToolsFactory manToolsFactory=new AaFactory();
        DynamicProxy dynamicProxy=new DynamicProxy();
        dynamicProxy.setFactory(manToolsFactory);


        //张三来了
        ManToolsFactory employee1
                = (ManToolsFactory)dynamicProxy.getProxyInstance();
        employee1.saleTool("E");
    }


}
