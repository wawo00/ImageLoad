package com.openup.app3.factory;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.factory
 * @ClassName: IOHandler
 * @Description: IOHandler
 * @Author: Roy
 * @CreateDate: 2020/3/20 18:10
 */

public abstract class IOHandler {
    abstract void add(String id, String name);

    abstract void delete(String id);

    abstract void edit(String id, String name);

    abstract String get(String id);

}
