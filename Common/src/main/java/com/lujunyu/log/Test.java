package com.lujunyu.log;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Test {
	private static Logger logger = LogManager.getLogger();
	
	public static void main(String[] args) {
		while(true){
			logger.info("21312");
		}
	}
}
