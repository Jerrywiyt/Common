package com.lujunyu.netty.protocol.netty.client;

import com.lujunyu.netty.protocol.netty.NettyConstant;
import com.lujunyu.netty.protocol.netty.codec.NettyMessageDecoder;
import com.lujunyu.netty.protocol.netty.codec.NettyMessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NettyClient {
    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private static EventLoopGroup group = new NioEventLoopGroup();
    public void connect(String host,int port) throws Exception {
        try{
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
                            ch.pipeline().addLast("messageEncoder",new NettyMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
                            ch.pipeline().addLast("loginAuthHandler",new LoginAuthReqHandler());
                            ch.pipeline().addLast("heartBeatHandler",new HeartBeatReqHandler());
                        }
                    });

            //发起异步连接
            ChannelFuture future = b.connect(new InetSocketAddress(host,port),new InetSocketAddress(NettyConstant.LOCALIP,NettyConstant.LOCAL_PORT)).sync();
            future.channel().closeFuture().sync();
        }finally {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try{
                        TimeUnit.SECONDS.sleep(5);
                        try{
                            System.out.println("发起重连");
                            /*
                             * 如果是认证失败的话，发起重连会遇到端口占用的情况。这种是由于认证失败，客户端主动关闭连接，此时连接处于time_wait状态，重连还是使用之前的端口号，
                             * 所以一直报端口占用异常。
                             */
                            connect(NettyConstant.REMOTEIP,NettyConstant.PORT);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    public static void main(String args[]) throws Exception {
        new NettyClient().connect(NettyConstant.REMOTEIP,NettyConstant.PORT);
    }
}
