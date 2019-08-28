package com.lujunyu.netty.nio;

import org.junit.Test;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class TestSelector {
    @Test
    public void test() throws IOException {
        Selector selector = Selector.open();
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.register(selector,SelectionKey.OP_CONNECT|SelectionKey.OP_ACCEPT);
    }

}
