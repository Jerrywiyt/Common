package com.lujunyu.classloader;


public class Test {
    public static void main(String[] args) throws Exception {
//        FileClassLoader classLoader1 = new FileClassLoader("D:\\Users\\lujunyu\\Desktop\\Test1\\classes\\");
//        FileClassLoader classLoader = new FileClassLoader("D:\\Users\\lujunyu\\Desktop\\Test\\classes\\",classLoader1);

//        Class clazz = classLoader.loadClass("com.lujunyu.classloader.A");
//        Class clazz1 = classLoader1.loadClass("com.lujunyu.classloader.A");
//        Class clazz2 = ClassLoader.getSystemClassLoader().loadClass("com.lujunyu.classloader.A");
//        System.out.println(clazz2.hashCode());

//        System.out.println(clazz.newInstance());


//        classLoader.findClass("com.lujunyu.classloader.B");
//        System.out.println(clazz1.newInstance());


/*        Test.class.getClassLoader().loadClass("java.lang.String");
        Test.class.getClassLoader().loadClass("java.lang.String");
        Test.class.getClassLoader().getParent().loadClass("java.lang.String");*/




        FileClassLoader classLoader = new FileClassLoader("E:\\code\\myCode\\Common\\Common\\Common\\target\\classes\\");


        Class clazz = classLoader.findClass("com.lujunyu.classloader.A");

        clazz.newInstance();

        classLoader.loadClass("com.lujunyu.classloader.B");

        ClassLoader.getSystemClassLoader().loadClass("com.lujunyu.classloader.B");





/*        FileClassLoader classLoader = new FileClassLoader("E:\\code\\myCode\\Common\\Common\\Common\\target\\classes\\");

        System.out.println(classLoader.loadClass("com.lujunyu.classloader.A").getClassLoader());

        classLoader.test("com.lujunyu.classloader.A");*/

    }
}
