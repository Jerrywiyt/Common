package com.lujunyu.log;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Test {
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) throws InterruptedException {
		while(true){
			logger.info("21312");
			Thread.sleep(1000);
		}
	}
}
