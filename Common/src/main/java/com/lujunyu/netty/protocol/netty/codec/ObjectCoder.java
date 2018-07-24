package com.lujunyu.netty.protocol.netty.codec;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class ObjectCoder {
    public static void encode(Object obj,ByteBuf byteBuf) throws UnsupportedEncodingException {
        if(obj==null){
            byteBuf.writeInt(0);
        }else {
            byte[] b = JSON.toJSONString(obj,new SerializerFeature[]{SerializerFeature.WriteClassName}).getBytes("utf-8");
            byteBuf.writeInt(b.length);
            byteBuf.writeBytes(b);
        }
    }

    public static Object decode(ByteBuf in) throws UnsupportedEncodingException {
        int size = in.readInt();
        if(size == 0){
            return null;
        }
        byte[] bytes = new byte[size];
        in.readBytes(bytes);
        return JSON.parse(new String(bytes,"utf-8"));
    }
}
