package com.lujunyu.log;


import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	private static Logger logger = Logger.getLogger(Test.class);
	private static final org.slf4j.Logger logger1 = LoggerFactory.getLogger(Test.class);




	private long delay;
	public Test(long delay){
		this.delay = delay;
	}

	@Override
	public String toString() {
		new B(delay);
		return "";
	}

	public static void main(String[] args) throws InterruptedException {
		new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info(new Test(100l));
			}
		}).start();
		new B(10l);
	}


	private static class B{
		private static Logger logb = Logger.getLogger("test");

		private B(long delay){
			System.out.println(Thread.currentThread().getName());
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			logb.info("initial B");
		}
	}
}
