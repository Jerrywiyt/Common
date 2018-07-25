package com.lujunyu.netty.protocol.netty.client;

import com.lujunyu.netty.protocol.netty.MessageType;
import com.lujunyu.netty.protocol.netty.struct.Header;
import com.lujunyu.netty.protocol.netty.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HeartBeatReqHandler extends ChannelHandlerAdapter {

    private volatile ScheduledFuture<?> heartBeat;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.LOGIN_RESP.value()){
            heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatTask(ctx),0,5000L,TimeUnit.MILLISECONDS);
        }else if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.HEARTBEAT_RESP.value()){
            log.info("REC PONG ...");
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    private static class HeartBeatTask implements Runnable{
        private ChannelHandlerContext ctx;

        private HeartBeatTask(ChannelHandlerContext ctx){
            this.ctx = ctx;
        }

        @Override
        public void run() {
            NettyMessage message = new NettyMessage();
            Header header = new Header();
            header.setType(MessageType.HEARTBEAT_REQ.value());
            message.setHeader(header);
            ctx.writeAndFlush(message);
            log.info("REQ PING ...");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if(heartBeat!=null){
            heartBeat.cancel(true);
            heartBeat = null;
        }
        log.error(cause.getMessage(),cause);
        ctx.fireExceptionCaught(cause);
    }
}
