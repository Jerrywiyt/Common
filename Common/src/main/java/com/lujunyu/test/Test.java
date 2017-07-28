package com.lujunyu.test;


public class Test {
	static volatile long i = 0l;
	public static void main(String[] args) {
		long t1 = System.currentTimeMillis();
		Object lock = new Object();
		synchronized (lock) {
			while (i < 1000000000l) {
				i++;
			}
		}
		long t2 = System.currentTimeMillis();
		System.out.println(t2 - t1);
	}
}
