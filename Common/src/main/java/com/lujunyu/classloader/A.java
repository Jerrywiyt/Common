package com.lujunyu.classloader;

public class A {
    static{
        System.out.println("A 被初始化了，hash="+A.class.hashCode());
    }

    public A(){
        System.out.println("新创建了A对象");
        B b = new B();
        System.out.println(b.getClass().hashCode());
        System.out.println(b.getClass().getClassLoader().getClass().getName());
    }
}
