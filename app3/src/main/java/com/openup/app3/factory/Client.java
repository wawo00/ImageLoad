package com.openup.app3.factory;

import java.io.File;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.factory
 * @ClassName: Client
 * @Description: Client
 * @Author: Roy
 * @CreateDate: 2020/3/20 18:13
 */

public class Client {

    public static void main(String[] args) {
        FileIOHandler fileIOHandler=IOFactory.getHandler(FileIOHandler.class);
        System.out.println("iohandler is "+fileIOHandler.get("1"));
    }

}
