package com.lujunyu.netty.protocol.netty.server;

import com.lujunyu.netty.protocol.netty.MessageType;
import com.lujunyu.netty.protocol.netty.struct.Header;
import com.lujunyu.netty.protocol.netty.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HeartBeatRespHandler extends ChannelHandlerAdapter {
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    NettyMessage message = (NettyMessage) msg;
    if (message.getHeader() != null
        && message.getHeader().getType() == MessageType.HEARTBEAT_REQ.value()) {
      log.info("REC PING ...");
      sendPong(ctx);
      log.info("RESP PONG ...");
    } else {
      ctx.fireChannelRead(msg);
    }
  }

  private void sendPong(ChannelHandlerContext ctx) {
    NettyMessage message = new NettyMessage();
    Header header = new Header();
    header.setType(MessageType.HEARTBEAT_RESP.value());
    message.setHeader(header);
    ctx.writeAndFlush(message);
  }
}
