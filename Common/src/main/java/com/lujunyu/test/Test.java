package com.lujunyu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	private static Logger logger = LoggerFactory.getLogger(Test.class);
	static volatile long i = 0l;
	public static void main(String[] args) throws SQLException {
		
		
		
		
		
		//线程。
		Executor executors = Executors.newFixedThreadPool(1000);
		
		
		
		
		for(int i=0;i<1000;i++){
			executors.execute(new Runnable() {
				
				@Override
				public void run() {
					try{
						logger.info("开始执行");
						Thread.sleep(2000l);
						logger.info("执行结束");
					}catch(Exception e){
						
					}
				}
			});
		}
		
		System.err.println("提交结束");
	}
}
