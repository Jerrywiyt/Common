package com.lujunyu.netty.protocol.netty.client;

import com.lujunyu.netty.protocol.netty.MessageType;
import com.lujunyu.netty.protocol.netty.struct.Header;
import com.lujunyu.netty.protocol.netty.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginAuthReqHandler extends ChannelHandlerAdapter {
  private static Logger logger = LoggerFactory.getLogger(LoginAuthReqHandler.class);

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    ctx.writeAndFlush(budildLoginReq());
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    NettyMessage message = (NettyMessage) msg;
    if (message.getHeader() != null
        && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
      byte loginResult = (byte) message.getBody();
      if (loginResult != (byte) 0) {
        logger.error("握手失敗");
        ctx.close();
      } else {
        logger.info("握手成功");
        ctx.fireChannelRead(message);
      }
    } else {
      ctx.fireChannelRead(message);
    }
  }

  private Object budildLoginReq() {
    NettyMessage message = new NettyMessage();
    Header header = new Header();
    header.setType(MessageType.LOGIN_REQ.value());
    message.setHeader(header);
    return message;
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    logger.error(cause.getMessage(), cause);
    ctx.fireExceptionCaught(cause);
  }
}
