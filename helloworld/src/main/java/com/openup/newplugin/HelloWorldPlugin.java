package com.openup.newplugin;


import org.gradle.api.DefaultTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.newplugin
 * @ClassName: HelloWorldPlugin
 * @Description: HelloWorldPlugin
 * @Author: Roy
 * @CreateDate: 2020/4/2 19:09
 */

public class HelloWorldPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        project.getTasks().create("hello", DefaultTask.class);
    }
}
