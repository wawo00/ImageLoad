package com.openup.app3.reflect;

/**
 * @ProjectName: TestApplication
 * @Package: com.openup.app3.reflect
 * @ClassName: Person
 * @Description: Persiona
 * @Author: Roy
 * @CreateDate: 2020/3/25 10:42
 */

public class Person {
  String name;
  private int age;

  public Person(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public Person() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  private void privateMethod(){
    System.out.println("this is private method");

  }
}


