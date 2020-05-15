package com.annotation.Repeatable;

import java.lang.annotation.Repeatable;

/**
 * @ProjectName: TestApplication
 * @Package: com.annotation.Repeatable
 * @ClassName: Man
 * @Description: Man
 * @Author: Roy
 * @CreateDate: 2020/4/7 18:06
 */


@interface Persons{
    Person[] value();
}

@Repeatable(Persons.class)
@interface Person{
    String role() default "humen";
}

@Person(role = "father")
@Person(role = "husband")
@Person(role = "son")
public class Man {

}
