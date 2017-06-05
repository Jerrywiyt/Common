package com.lujunyu.task;

import java.util.concurrent.TimeUnit;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

public class UseHashedWheelTimer {
	public static void main(String[] args) {
		
		//注意java8，没有unsafe类。
		HashedWheelTimer timer = new HashedWheelTimer(1l, TimeUnit.SECONDS);
		timer.newTimeout(new TimerTask() {
			
			@Override
			public void run(Timeout arg0) throws Exception {
				System.out.println("lala");
			}
		}, 10l, TimeUnit.SECONDS);
		timer.start();
	}
}
