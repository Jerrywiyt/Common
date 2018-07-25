package com.lujunyu.netty.protocol.netty.codec;

import com.lujunyu.netty.protocol.netty.struct.Header;
import com.lujunyu.netty.protocol.netty.struct.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import java.util.HashMap;
import java.util.Map;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
    public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx,in);
        if(frame==null){
            return null;
        }
        NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());

        int size = frame.readInt();
        if(size>0){
            Map<String,Object> attachment = new HashMap<>();
            int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for(int i=0;i<size;i++){
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray,"utf-8");
                attachment.put(key,ObjectCoder.decode(frame));
            }
        }
        Object body = ObjectCoder.decode(frame);
        message.setHeader(header);
        message.setBody(body);
        return message;
    }
}
