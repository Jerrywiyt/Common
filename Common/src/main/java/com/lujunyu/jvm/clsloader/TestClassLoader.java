package com.lujunyu.jvm.clsloader;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class TestClassLoader {
	public static void main(String args[]) throws ClassNotFoundException {
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		classLoader.loadClass("com.lujunyu.task.DefaultDelayTask");

		MyClassLoader myClassLoader = new MyClassLoader();

		//证明同一个classLoader重复调用defineClass加载类时会抛异常
		System.out.println(myClassLoader.loadClass("E:\\code\\myCode\\Common\\Common\\Common\\target\\classes\\com\\lujunyu\\jvm\\clsloader\\TestA.class"));
		System.out.println(myClassLoader.loadClass("E:\\code\\myCode\\Common\\Common\\Common\\target\\classes\\com\\lujunyu\\jvm\\clsloader\\TestA.class"));
	}

	private static class MyClassLoader extends ClassLoader{
		@Override
		public Class<?> findClass(String name) throws ClassNotFoundException {
			try (InputStream inputStream = new FileInputStream(new File(name))) {
				byte[] cls = new byte[inputStream.available()];
				inputStream.read(cls);
				Class<?> clazz = defineClass("com.lujunyu.jvm.clsloader.TestA",cls,0,cls.length);
				return clazz;
			}catch (Exception e){
				e.printStackTrace();
			}
			throw new ClassNotFoundException();
		}
	}
}
