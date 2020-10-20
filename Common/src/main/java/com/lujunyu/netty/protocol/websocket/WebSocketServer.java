package com.lujunyu.netty.protocol.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
  public void run(int port) throws Exception {
    EventLoopGroup boss = new NioEventLoopGroup();
    EventLoopGroup work = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(boss, work)
          .channel(NioServerSocketChannel.class)
          .childHandler(
              new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                  ChannelPipeline pipeline = ch.pipeline();
                  pipeline.addLast("http-codec", new HttpServerCodec());
                  pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
                  pipeline.addLast("http-chunked", new ChunkedWriteHandler());
                  pipeline.addLast("handler", new WebSocketServerHandler());
                }
              });
      Channel ch = b.bind(port).sync().channel();
      ch.closeFuture().sync();
    } finally {
      work.shutdownGracefully();
      boss.shutdownGracefully();
    }
  }

  public static void main(String args[]) throws Exception {
    new WebSocketServer().run(8080);
  }
}
