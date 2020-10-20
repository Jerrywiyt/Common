package com.lujunyu.netty.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

public class TestSelector {
  /** */
  @Test
  public void test() throws IOException {
    Selector selector = Selector.open();
    SocketChannel channel = SocketChannel.open();
    channel.configureBlocking(false);
    SelectionKey selectionKey =
        channel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_ACCEPT);
    selectionKey.attachment();

    selector.select();

    Set<SelectionKey> selectionKeys = selector.selectedKeys();

    selector.wakeup();
  }
}
