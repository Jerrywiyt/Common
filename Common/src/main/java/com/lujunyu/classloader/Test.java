package com.lujunyu.classloader;

public class Test {
    public static void main(String[] args) throws Exception {
        FileClassLoader classLoader = new FileClassLoader("D:\\Users\\lujunyu\\Desktop\\Test\\classes\\");


        Class clazz1 = classLoader.findClass("com.lujunyu.classloader.A");
//        Class clazz2 = ClassLoader.getSystemClassLoader().loadClass("com.lujunyu.classloader.A");
        System.out.println(clazz1.hashCode());
//        System.out.println(clazz2.hashCode());



//        System.out.println(new A().getClass().hashCode());

        classLoader.findClass("com.lujunyu.classloader.B");
        System.out.println(clazz1.newInstance());

    }
}
