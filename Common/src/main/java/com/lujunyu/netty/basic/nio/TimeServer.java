package com.lujunyu.netty.basic.nio;

public class TimeServer {
  private static final int PORT = 9090;

  public static void main(String args[]) {
    new Thread(new MultiplexerTimeServer(PORT)).start();
  }
}
