package com.lambda.entity;

import com.lambda.PersonCompareInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ProjectName: TestApplication
 * @Package: com.lambda.entity
 * @ClassName: Person
 * @Description: Person
 * @Author: Roy
 * @CreateDate: 2020/4/7 17:01
 */

public class Person {
    private String name;
    public Integer age;
    private Integer score;
    public Person() {
    }
    public Person(String name, Integer age, Integer score) {
        this.name = name;
        this.age = age;
        this.score = score;
    }

    public static void main(String[] args) {

        // 比较age的大小
        PersonCompareInterface<Person> personCompareInterface = (p1, p2) -> {
            return p1.age.compareTo(p2.age);
        };

        // 比较两个score
        PersonCompareInterface<Person> scoreCompare= (p1,p2)->{
            return p1.score-p2.score;
        };
        Person p1 = new Person("Av",18,90);
        Person p2 = new Person("King",20,0);
        Person p3 = new Person("Lance",17,100);
        List<Person> list = new ArrayList<>();
        list.add(p1);
        list.add(p2);
        list.add(p3); //这里我们需要比较list里面的person,按照年龄排序
        //那么我们最常见的做法是
        //sort(List<T> list, Comparator<? super T> c)
        //1. 因为我们的sort方法的第二个参数是一个接口，所以我们需要实现一个匿名内部类
        Collections.sort(list, new Comparator<Person>() {
            @Override
            public int compare(Person person1, Person person2) {
                return person1.age.compareTo(person2.age);
            }
        });
        //2. 因为第二个参数是一个@FunctionalInterface的函数式接口，所以我们可以用lambda写法
        Collections.sort(list, (person1,person2) -> person1.age.compareTo(person2.age));
    }
}
