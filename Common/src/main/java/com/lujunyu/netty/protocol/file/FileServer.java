package com.lujunyu.netty.protocol.file;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class FileServer {

  public void run(int port) throws Exception {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(boss, work)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 100)
          .childHandler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                  ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                  ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                  ch.pipeline().addLast(new FileServerHandler());
                }
              });
      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } finally {
      work.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }

  public static void main(String args[]) throws Exception {
    new FileServer().run(8080);
  }
}
