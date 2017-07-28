package com.lujunyu.disrupter;

import com.lmax.disruptor.EventHandler;

/**
 * 
 * @author lujunyu
 *
 */
public class TradeTransactionDealHandler implements EventHandler<TradeTransaction>{

	@Override
	public void onEvent(TradeTransaction event, long sequence, boolean endOfBatch) throws Exception {
		deal(event);
	}

	private void deal(TradeTransaction event) {
		System.out.println(String.format("trade[%s] is deal", event.getId()));
	}

}
