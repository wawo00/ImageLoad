package com.openup.app3.factory;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.factory
 * @ClassName: IOFactory
 * @Description: IOFactory
 * @Author: Roy
 * @CreateDate: 2020/3/20 18:14
 */

public class IOFactory {

    public static <T extends IOHandler> T getHandler(Class<T> tClass) {
        IOHandler ioHandler = null;
        try {
           ioHandler= (IOHandler) Class.forName(tClass.getName()).newInstance();
        } catch (Exception e) {
            return null;
        }

        return (T) ioHandler;

    }

}
