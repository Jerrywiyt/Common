package com.lujunyu.netty.protocol.websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;

public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {
  private static Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

  private WebSocketServerHandshaker handshaker;

  @Override
  protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
    if (msg instanceof FullHttpRequest) {
      handle(ctx, (FullHttpRequest) msg);
    } else if (msg instanceof WebSocketFrame) {
      handle(ctx, (WebSocketFrame) msg);
    }
  }

  private void handle(ChannelHandlerContext ctx, FullHttpRequest msg) {
    if (!msg.getDecoderResult().isSuccess()
        || (!"websocket".equals(msg.headers().get("Upgrade")))) {
      sendHttpResponse(ctx, msg, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, BAD_REQUEST));
      return;
    }
    WebSocketServerHandshakerFactory ws =
        new WebSocketServerHandshakerFactory("ws://localhost:8080/websocket", null, false);
    handshaker = ws.newHandshaker(msg);
    if (handshaker == null) {
      WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
    } else {
      handshaker.handshake(ctx.channel(), msg);
    }
  }

  private void sendHttpResponse(
      ChannelHandlerContext ctx,
      FullHttpRequest msg,
      DefaultFullHttpResponse defaultFullHttpResponse) {
    if (defaultFullHttpResponse.getStatus().code() != 200) {
      ByteBuf buf =
          Unpooled.copiedBuffer(defaultFullHttpResponse.getStatus().toString(), CharsetUtil.UTF_8);
      defaultFullHttpResponse.content().writeBytes(buf);
      buf.release();
      setContentLength(defaultFullHttpResponse, defaultFullHttpResponse.content().readableBytes());
    }
    ChannelFuture f = ctx.channel().writeAndFlush(defaultFullHttpResponse);
    if (!isKeepAlive(msg) || defaultFullHttpResponse.getStatus().code() != 200) {
      f.addListener(ChannelFutureListener.CLOSE);
    }
  }

  private void handle(ChannelHandlerContext ctx, WebSocketFrame msg) {
    // 关闭
    if (msg instanceof CloseWebSocketFrame) {
      handshaker.close(ctx.channel(), (CloseWebSocketFrame) msg.retain());
      return;
    }

    // 判断是不是ping请求。
    if (msg instanceof PingWebSocketFrame) {
      ctx.channel().write(new PongWebSocketFrame(msg.content().retain()));
      return;
    }
    if (!(msg instanceof TextWebSocketFrame)) {
      throw new UnsupportedOperationException("不支持非文本信息");
    }
    String request = ((TextWebSocketFrame) msg).text();

    logger.info("REQ:" + request);
    ctx.channel()
        .writeAndFlush(
            new TextWebSocketFrame(request + "，欢迎使用websocket，now=" + new Date().toString()));
  }
}
