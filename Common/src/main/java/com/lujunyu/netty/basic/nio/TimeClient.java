package com.lujunyu.netty.basic.nio;

public class TimeClient {
  public static void main(String[] args) {
    new Thread(new TimeClientHandle("127.0.0.1", 9090), "TimeClient-1").start();
  }
}
