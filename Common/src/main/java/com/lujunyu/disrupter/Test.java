package com.lujunyu.disrupter;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;

public class Test {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    int bufferSize = 1024;
    Disruptor<TradeTransaction> disruptor =
        new Disruptor<>(
            new EventFactory<TradeTransaction>() {

              @Override
              public TradeTransaction newInstance() {
                TradeTransaction trade = new TradeTransaction(10l, 1000l);
                return trade;
              }
            },
            bufferSize,
            new ThreadFactory() {
              @Override
              public Thread newThread(Runnable r) {
                return new Thread(r);
              }
            },
            ProducerType.SINGLE,
            new BusySpinWaitStrategy());

    EventHandlerGroup<TradeTransaction> handlerGroup =
        disruptor.handleEventsWith(
            new TradeTransactionDealHandler(), new TradeTransactionSaveInDbHandler());

    TradeTransactionJMSNotifyHandler jmsConsumer = new TradeTransactionJMSNotifyHandler();

    handlerGroup.then(jmsConsumer);

    disruptor.start();
    CountDownLatch latch = new CountDownLatch(1);
    new Thread(new TradeTransactionPublisher(latch, disruptor, 1000)).start();
    disruptor.shutdown();
    System.out.println("总耗时：" + (System.currentTimeMillis() - start));
  }
}
