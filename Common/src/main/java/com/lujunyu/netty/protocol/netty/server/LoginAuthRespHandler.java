package com.lujunyu.netty.protocol.netty.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.lujunyu.netty.protocol.netty.MessageType;
import com.lujunyu.netty.protocol.netty.struct.Header;
import com.lujunyu.netty.protocol.netty.struct.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class LoginAuthRespHandler extends ChannelHandlerAdapter {
    private static Map<String,Boolean> onCheck = new ConcurrentHashMap<>();
    private static Set<String> ipList = Sets.newHashSet("127.0.0.1");
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyMessage message = (NettyMessage) msg;
        if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.LOGIN_REQ.value()){
            NettyMessage resp = null;
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            String ip = address.getAddress().getHostAddress();
            if(onCheck.containsKey(ip)){
               resp = buildResp((byte)-1);
            }else if(!ipList.contains(ip)){
               resp = buildResp((byte)-1);
            }else {
                onCheck.put(ip,true);
                resp = buildResp((byte)0);
            }
            ctx.writeAndFlush(resp);
        }else {
            ctx.fireChannelRead(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        onCheck.clear();
        ctx.close();
        ctx.fireExceptionCaught(cause);
    }

    private NettyMessage buildResp(byte i) {
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setType(MessageType.LOGIN_RESP.value());
        message.setHeader(header);
        message.setBody(i);
        return message;
    }
}
