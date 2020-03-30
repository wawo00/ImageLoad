package com.openup.app3.proxy;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.proxy
 * @ClassName: Mark
 * @Description: Mark
 * @Author: Roy
 * @CreateDate: 2020/3/25 16:08
 */

public class Mark implements ManToolsFactory {

    /*包含真实的对象*/
    public ManToolsFactory factory;

    public Mark(ManToolsFactory factory) {
        this.factory = factory;
    }

    /*前置处理器*/
    private void doSthAfter() {
        System.out.println("精美包装，快递一条龙服务");
    }

    /*后置处理器*/
    private void doSthBefore() {
        System.out.println("根据需求，进行市场调研和产品分析");
    }

    @Override
    public void saleTool(String size) {
        doSthAfter();  // 运行前服务器
        factory.saleTool(size);
        doSthBefore(); // 运动后服务器

    }
}