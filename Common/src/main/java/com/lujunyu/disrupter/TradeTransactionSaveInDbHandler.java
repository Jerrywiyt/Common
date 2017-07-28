package com.lujunyu.disrupter;

import com.lmax.disruptor.EventHandler;

public class TradeTransactionSaveInDbHandler implements EventHandler<TradeTransaction>{

	@Override
	public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
		onEvent(event);
	}

	private void onEvent(TradeTransaction event) {
		System.out.println(String.format("trade[] save id db", event.getId()));
	}

}
