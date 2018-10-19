package com.lujunyu.classloader;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        FileClassLoader classLoader = new FileClassLoader("E:\\code\\MyGit\\Common\\Common\\Common\\target\\classes\\");
        System.out.println(classLoader.loadClass("com.lujunyu.utils.Test").hashCode());
        System.out.println(classLoader.findClass("com.lujunyu.utils.Test").hashCode());

        FileClassLoader classLoader1 = new FileClassLoader("E:\\code\\MyGit\\Common\\Common\\Common\\target\\classes\\");
        System.out.println(classLoader1.loadClass("com.lujunyu.utils.Test").hashCode());
        System.out.println(classLoader1.findClass("com.lujunyu.utils.Test").hashCode());
    }
}
