package com.lujunyu.netty.protocol.netty.server;

import com.lujunyu.netty.protocol.netty.NettyConstant;
import com.lujunyu.netty.protocol.netty.codec.NettyMessageDecoder;
import com.lujunyu.netty.protocol.netty.codec.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {
  public void bind() throws InterruptedException {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(boss, work)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 100)
          .handler(new LoggingHandler(LogLevel.INFO))
          .childHandler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4));
                  ch.pipeline().addLast(new NettyMessageEncoder());
                  ch.pipeline().addLast(new ReadTimeoutHandler(50));
                  ch.pipeline().addLast(new LoginAuthRespHandler());
                  ch.pipeline().addLast(new HeartBeatRespHandler());
                }
              });
      ChannelFuture future = b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
      log.info("服务启动成功。。。");
      future.channel().closeFuture().sync();
    } finally {
      work.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }

  public static void main(String args[]) throws InterruptedException {
    new NettyServer().bind();
  }
}
