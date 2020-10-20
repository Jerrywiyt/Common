package com.lujunyu.netty.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class TestChannel {

  @Test
  public void test() throws IOException {
    SocketChannel socketChannel = SocketChannel.open();
    socketChannel.connect(new InetSocketAddress("172.0.0.1", 8001));

    socketChannel.close();

    ByteBuffer byteBuffer = ByteBuffer.allocate(48);
    // 返回值表示读了多少字节到bytebuff中，如果返回-1表示读到了末尾。
    int read = socketChannel.read(byteBuffer);
    // 向channel中写数据
    socketChannel.write(byteBuffer);
  }

  @Test
  public void testServerSocketChannel() throws IOException {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.socket().bind(new InetSocketAddress(9999));

    while (true) {
      SocketChannel socketChannel = serverSocketChannel.accept();
      break;
    }

    serverSocketChannel.close();
  }

  @Test
  public void testDatagramChannel() throws IOException {
    DatagramChannel datagramChannel = DatagramChannel.open();
    datagramChannel.socket().bind(new InetSocketAddress(999));
    datagramChannel.receive(ByteBuffer.allocate(10));
    datagramChannel.send(ByteBuffer.allocate(10), new InetSocketAddress("host", 80));
    datagramChannel.close();
  }
}
