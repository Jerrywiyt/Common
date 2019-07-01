package com.lujunyu.test;


public class Test {
	public static void main(String args[]) throws ClassNotFoundException {
		Thread.currentThread().getContextClassLoader().loadClass("java.lang.Test");
	}
}
