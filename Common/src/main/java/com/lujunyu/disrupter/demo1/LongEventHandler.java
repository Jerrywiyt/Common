package com.lujunyu.disrupter.demo1;


import com.lmax.disruptor.EventHandler;

public class LongEventHandler implements EventHandler<LongEvent>{

	@Override
	public void onEvent(LongEvent event, long sequence, boolean endOfBatch) throws Exception {
		System.out.println("Event:"+event.get()+" sequence="+sequence+" endOfBatch="+endOfBatch);
	}
}
