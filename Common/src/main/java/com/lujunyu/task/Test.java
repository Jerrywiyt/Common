package com.lujunyu.task;

import java.util.Date;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.lujunyu.utils.DateUtil;

public class Test {
	private static Logger log = Logger.getLogger(Test.class);
	
	public static void main(String[] args) throws InterruptedException {
//		test();
		test1();
	}
	
	
	private static void test1() {
		
	}


	private static void test() throws InterruptedException {
		//定义1分钟之后触发一次的延时任务，
		DelayTask task = new DefaultDelayTask(10, 1, Executors.newCachedThreadPool());
		while(true){
			Thread.sleep(50);
			Date date = new Date();
			String curTime = DateUtil.format(date, DateUtil.YYYYMMDDHHMMSS);
			task.addTask(new Task() {
				@Override
				public void execute() {
					System.err.println(curTime);
				}
			});
		}
	}
}
