package com.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @ProjectName: TestApplication
 * @Package: com.annotation
 * @ClassName: Tuhao
 * @Description: Tuhao
 * @Author: Roy
 * @CreateDate: 2020/4/7 17:44
 */

@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface Tuhao {
    String value() default "土豪";
}
