package com.lujunyu.netty.basic.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TimeServer {
  public void bind(int port) throws InterruptedException {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup(128);
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(boss, work)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(new ChildChannelHandler());
      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } finally {
      boss.shutdownGracefully();
      work.shutdownGracefully();
    }
  }

  private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
      //            socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));
      //            socketChannel.pipeline().addLast(new
      // DelimiterBasedFrameDecoder(100,Unpooled.copiedBuffer("$".getBytes())));
      socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));
      socketChannel.pipeline().addLast(new StringDecoder());
      socketChannel.pipeline().addLast(new TimeServerHandler());
    }
  }

  public static void main(String args[]) throws InterruptedException {
    new TimeServer().bind(8080);
  }
}
