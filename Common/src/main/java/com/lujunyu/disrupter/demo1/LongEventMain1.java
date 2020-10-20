package com.lujunyu.disrupter.demo1;

import java.nio.ByteBuffer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;

public class LongEventMain1 {
  public static void main(String[] args) {
    Executor executor = Executors.newCachedThreadPool();
    int bufferSize = 1024;
    Disruptor<LongEvent> disruptor = new Disruptor<>(LongEvent::new, bufferSize, executor);
    disruptor.handleEventsWith(
        (event, sequence, endOfBatch) ->
            System.out.println("event=" + event.get() + " sequence=" + sequence));
    disruptor.start();
    RingBuffer<LongEvent> ringBuffer = disruptor.getRingBuffer();
    ByteBuffer bb = ByteBuffer.allocate(8);
    for (long l = 0; true; l++) {
      bb.putLong(0, l);
      ringBuffer.publishEvent((event, sequence, buffer) -> event.set(buffer.getLong(0)), bb);
    }
  }
}
