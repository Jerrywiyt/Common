package com.lujunyu.disrupter;

import com.lmax.disruptor.EventTranslator;

public class TradeTransactionEventTranslator implements EventTranslator<TradeTransaction> {
  @Override
  public void translateTo(TradeTransaction event, long sequence) {
    System.out.println(String.format("sequence[%s],trade[%s]", sequence, event.getId()));
  }
}
