package com.lujunyu.disrupter;

import java.util.concurrent.CountDownLatch;

import com.lmax.disruptor.dsl.Disruptor;

public class TradeTransactionPublisher implements Runnable{
	private Disruptor<TradeTransaction> disruptor;
	private CountDownLatch latch;
	private int loop;
	public TradeTransactionPublisher(CountDownLatch latch,Disruptor<TradeTransaction> disruptor,int loop) {
		this.disruptor = disruptor;
		this.latch = latch;
		this.loop = loop;
	}
	@Override
	public void run() {
		TradeTransactionEventTranslator eventTranslator = new TradeTransactionEventTranslator();
		for(int i=0;i<loop;i++){
			disruptor.publishEvent(eventTranslator);
		}
		latch.countDown();
	}

}
