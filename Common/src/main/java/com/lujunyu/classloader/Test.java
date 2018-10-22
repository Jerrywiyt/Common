package com.lujunyu.classloader;

import java.io.IOException;
import java.net.MalformedURLException;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, IOException {
//        Class<?> clazz = classLoader.loadClass("com.bj58.wuxian.incentive.server.util.id.IdGeneratorFactory");
//        System.out.println(Class.forName("com.bj58.wuxian.incentive.server.util.id.IdGenerator").getName());



   /*     FileUrlClassLoader classLoader1 = FileUrlClassLoader.getInstance(new String[]{"D:\\Users\\lujunyu\\Desktop\\Test\\community-2.0.19.jar"});
        FileUrlClassLoader classLoader2 = FileUrlClassLoader.getInstance(new String[]{"D:\\Users\\lujunyu\\Desktop\\Test\\community-2.0.19.jar"});

        Class<?> clazz1 = classLoader1.loadClass("com.bj58.wuxian.community.entity.Community");
        Class<?> clazz2 = classLoader2.loadClass("com.bj58.wuxian.community.entity.Community");

        System.out.println(clazz1.hashCode());
        System.out.println(clazz2.hashCode());
        System.out.println(clazz1.newInstance().getClass().hashCode());
        System.out.println(clazz2.newInstance().getClass().hashCode());*/

        FileClassLoader classLoader = new FileClassLoader("D:\\Users\\lujunyu\\Desktop\\Test\\classes\\");

        Class<?> clazz = classLoader.findClass("com.lujunyu.utils.Test");
        System.out.println(clazz.getClassLoader().getClass().getName());
        System.out.println(classLoader.loadClass("com.lujunyu.utils.Test").getClassLoader().getClass().getName());

        Test test = new Test();
        System.out.println(test.getClass().getClassLoader().getClass().getName());
    }
}
