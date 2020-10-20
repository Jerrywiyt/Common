package com.lujunyu.netty.protocol.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class ChineseProverbClient {
  public void run(int port) throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap();
      b.group(group)
          .channel(NioDatagramChannel.class)
          .option(ChannelOption.SO_BROADCAST, true)
          .handler(new ChineseProverbClientHandler());
      Channel ch = b.bind(0).sync().channel();
      ch.writeAndFlush(
              new DatagramPacket(
                  Unpooled.copiedBuffer("lala", CharsetUtil.UTF_8),
                  new InetSocketAddress("255.255.255.255", port)))
          .sync();
      if (!ch.closeFuture().await(15000)) {
        System.out.println("查询超时");
      }
    } finally {
      group.shutdownGracefully();
    }
  }

  public static void main(String args[]) throws Exception {
    new ChineseProverbClient().run(8080);
  }
}
