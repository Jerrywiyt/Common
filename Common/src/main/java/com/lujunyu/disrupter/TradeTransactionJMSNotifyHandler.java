package com.lujunyu.disrupter;

import com.lmax.disruptor.EventHandler;

public class TradeTransactionJMSNotifyHandler implements EventHandler<TradeTransaction>{
	@Override
	public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
		send2JMS(event);
	}

	private void send2JMS(TradeTransaction event) {
		System.out.println(String.format("trade[%s] send to jms", event.getId()));
	}
}
