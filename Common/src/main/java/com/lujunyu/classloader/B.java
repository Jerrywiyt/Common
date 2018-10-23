package com.lujunyu.classloader;

public class B {
    static {
        System.out.println("B 被初始化了 hash="+B.class.hashCode());
    }
}
