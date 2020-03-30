package com.openup.app3.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.proxy
 * @ClassName: DynamicProxy
 * @Description: DynamicProxy
 * @Author: Roy
 * @CreateDate: 2020/3/25 15:20
 */

public class DynamicProxy  implements InvocationHandler {

    /*持有的真实对象*/
    private Object factory;


    public Object getFactory() {
        return factory;
    }


    public void setFactory(Object factory) {
        this.factory = factory;
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(factory.getClass().getClassLoader(),factory.getClass().getInterfaces(),this);
//        return Proxy.newProxyInstance(factory.getClass().getClassLoader(),
//                factory.getClass().getInterfaces(),this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        doSthBefore();

        Object res = method.invoke(factory, args);
        doSthAfter();

        return res;
    }


    /*前置处理器*/
    private void doSthAfter() {
        System.out.println("精美包装，快递一条龙服务");
    }
    /*后置处理器*/


    private void doSthBefore() {
        System.out.println("根据需求，进行市场调研和产品分析");
    }
}
