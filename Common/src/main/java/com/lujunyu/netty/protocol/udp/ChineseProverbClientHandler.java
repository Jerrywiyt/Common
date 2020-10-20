package com.lujunyu.netty.protocol.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class ChineseProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {
  @Override
  protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
    System.out.println(msg.content().toString(CharsetUtil.UTF_8));
    ctx.close();
  }
}
