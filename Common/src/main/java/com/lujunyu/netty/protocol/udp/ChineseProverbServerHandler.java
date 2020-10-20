package com.lujunyu.netty.protocol.udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
  private static final String[] arr = new String[] {"1", "2", "3"};

  @Override
  protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
    System.out.println(msg.content().toString(CharsetUtil.UTF_8));
    ctx.writeAndFlush(
        new DatagramPacket(
            Unpooled.copiedBuffer(
                "random=" + ThreadLocalRandom.current().nextInt(), CharsetUtil.UTF_8),
            msg.sender()));
  }
}
