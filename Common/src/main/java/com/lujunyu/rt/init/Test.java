package com.lujunyu.rt.init;

public class Test {
    public static void main(String args[]){
        System.out.println(SuperClass.str2);
    }
}

class SuperClass{
    static{
        System.out.println("init superClass");
    }
    static int a = 123;
    static String str1 = "str1";
    final static int b = 122;
    final static String str2 = "str2";
}

class SubClass extends SuperClass{

    static {
        System.out.println("init subClass");
    }
}